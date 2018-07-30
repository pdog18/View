package pdog18.com.core

import android.app.Application
import com.facebook.stetho.Stetho
import timber.log.Timber

fun debugInstall(app: Application) {
    if (!BuildConfig.DEBUG) {
        return
    }
    initStetho(app)
    initLogcat()
}

fun initLogcat() {
    Timber.plant(object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String? {
            /* Create Tag like this :
               (AppWrap.java:83) MethodName      message ....
               help the log in the  logcat terminal can click!
             */
            return String.format(" (%s:%s)",
                    element.fileName,
                    element.lineNumber)
            //                String tag = " (" + element.getFileName() + ":" + element.getLineNumber() + ") ";
            //                return tag ;
        }
    })
}

fun initStetho(app: Application) {
    Stetho.initializeWithDefaults(app)
}