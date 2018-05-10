package com.example.a18.path

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.multidex.MultiDexApplication

import com.facebook.stetho.Stetho


import timber.log.Timber

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        app = this
        Stetho.initializeWithDefaults(this)

        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                /* Create Tag like this :
                   (AppWrap.java:83)      message ....
                   help the log in the  logcat terminal can click!
                 */
                val tag = " (" + element.fileName + ":" + element.lineNumber + ") "
                return tag + super.createStackElementTag(element)!!
            }
        })


        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                Timber.d("%s  onActivityCreated", activity.javaClass.simpleName)
            }

            override fun onActivityStarted(activity: Activity) {
                Timber.d("%s  onActivityStarted", activity.javaClass.simpleName)
            }

            override fun onActivityResumed(activity: Activity) {
                Timber.d("%s  onActivityResumed", activity.javaClass.simpleName)
            }

            override fun onActivityPaused(activity: Activity) {
                Timber.d("%s  onActivityPaused", activity.javaClass.simpleName)
            }

            override fun onActivityStopped(activity: Activity) {
                Timber.d("%s  onActivityStopped", activity.javaClass.simpleName)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                Timber.d("%s  onActivitySaveInstanceState", activity.javaClass.simpleName)
            }

            override fun onActivityDestroyed(activity: Activity) {
                Timber.d("%s  onActivityDestroyed", activity.javaClass.simpleName)
            }
        })
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var app: Context
    }
}
