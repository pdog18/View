package kt.pdog18.com.core.tool

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.ContextWrapper
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Bundle
import android.os.Looper
import android.os.MessageQueue
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object IMMLeaks {

    internal class ReferenceCleaner(private val inputMethodManager: InputMethodManager, private val mHField: Field, private val mServedViewField: Field,
                                    private val finishInputLockedMethod: Method) : MessageQueue.IdleHandler, View.OnAttachStateChangeListener, ViewTreeObserver.OnGlobalFocusChangeListener {

        override fun onGlobalFocusChanged(oldFocus: View?, newFocus: View?) {
            if (newFocus == null) {
                return
            }
            oldFocus?.removeOnAttachStateChangeListener(this)
            Looper.myQueue().removeIdleHandler(this)
            newFocus.addOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View) {}

        override fun onViewDetachedFromWindow(v: View) {
            v.removeOnAttachStateChangeListener(this)
            Looper.myQueue().removeIdleHandler(this)
            Looper.myQueue().addIdleHandler(this)
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        override fun queueIdle(): Boolean {
            clearInputMethodManagerLeak()
            return false
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private fun clearInputMethodManagerLeak() {
            try {
                val lock = mHField.get(inputMethodManager)
                // This is highly dependent on the InputMethodManager implementation.
                synchronized(lock) {
                    val servedView = mServedViewField.get(inputMethodManager) as View?
                    if (servedView != null) {

                        val servedViewAttached = servedView.windowVisibility != View.GONE

                        if (servedViewAttached) {
                            // The view held by the IMM was replaced without a global focus change. Let's make
                            // sure we get notified when that view detaches.

                            // Avoid double registration.
                            servedView.removeOnAttachStateChangeListener(this)
                            servedView.addOnAttachStateChangeListener(this)
                        } else {
                            // servedView is not attached. InputMethodManager is being stupid!
                            val activity = extractActivity(servedView.context)
                            if (activity == null || activity.window == null) {
                                // Unlikely case. Let's finish the input anyways.
                                finishInputLockedMethod.invoke(inputMethodManager)
                            } else {
                                val decorView = activity.window.peekDecorView()
                                val windowAttached = decorView.windowVisibility != View.GONE
                                if (!windowAttached) {
                                    finishInputLockedMethod.invoke(inputMethodManager)
                                } else {
                                    decorView.requestFocusFromTouch()
                                }
                            }
                        }
                    }
                }
            } catch (unexpected: IllegalAccessException) {
                Log.e("IMMLeaks", "Unexpected reflection exception", unexpected)
            } catch (unexpected: InvocationTargetException) {
                Log.e("IMMLeaks", "Unexpected reflection exception", unexpected)
            }

        }

        private fun extractActivity(ctx: Context): Activity? {
            var context = ctx
            while (true) {
                when (context) {
                    is Application -> return null
                    is Activity -> return context
                    is ContextWrapper -> {
                        val baseContext = context.baseContext
                        // Prevent Stack Overflow.
                        if (baseContext === context) {
                            return null
                        }
                        context = baseContext
                    }
                    else -> return null
                }
            }
        }
    }

    /**
     * Fix for https://code.google.com/p/android/issues/detail?id=171190 .
     *
     * When a view that has focus gets detached, we wait for the main thread to be idle and then
     * check if the InputMethodManager is leaking a view. If yes, we tell it that the decor view got
     * focus, which is what happens if you press home and come back from recent apps. This replaces
     * the reference to the detached view with a reference to the decor view.
     *
     * Should be called from [Activity.onCreate] )}.
     */
    fun fixFocusedViewLeak(application: Application) {

        // Don't know about other versions yet.
        if (SDK_INT < KITKAT || SDK_INT > 22) {
            return
        }

        val inputMethodManager = application.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        val mServedViewField: Field
        val mHField: Field
        val finishInputLockedMethod: Method
        val focusInMethod: Method
        try {
            mServedViewField = InputMethodManager::class.java.getDeclaredField("mServedView")
            mServedViewField.isAccessible = true
            mHField = InputMethodManager::class.java.getDeclaredField("mServedView")
            mHField.isAccessible = true
            finishInputLockedMethod = InputMethodManager::class.java.getDeclaredMethod("finishInputLocked")
            finishInputLockedMethod.isAccessible = true
            focusInMethod = InputMethodManager::class.java.getDeclaredMethod("focusIn", View::class.java)
            focusInMethod.isAccessible = true
        } catch (unexpected: NoSuchMethodException) {
            Log.e("IMMLeaks", "Unexpected reflection exception", unexpected)
            return
        } catch (unexpected: NoSuchFieldException) {
            Log.e("IMMLeaks", "Unexpected reflection exception", unexpected)
            return
        }

        application.registerActivityLifecycleCallbacks(object : LifecycleCallbacksAdapter() {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                val cleaner = ReferenceCleaner(inputMethodManager, mHField, mServedViewField,
                        finishInputLockedMethod)
                val rootView = activity.window.decorView.rootView
                val viewTreeObserver = rootView.viewTreeObserver
                viewTreeObserver.addOnGlobalFocusChangeListener(cleaner)
            }
        })
    }
}