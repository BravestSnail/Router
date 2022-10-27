package com.bravestsnail.router.handler

import android.app.Activity
import android.content.Context
import android.content.Intent

object RouterUtils {
    private val routers = HashMap<String, String>()

    fun register(url: String, target: String) {
        routers[url] = target
    }

    /**
     * 导航到url对应的Activity页面
     *
     * @param activity
     * @param url
     * @param intent
     */
    @JvmOverloads
    fun dispatch(activity: Activity, url: String, intent: Intent = Intent()) {
        routers[url]?.let {
            intent.setClassName(activity, it)
            activity.startActivity(intent)
        } ?: run {
            throw Exception("The url isn't registered")
        }
    }

    fun init(context: Context) {
        val classes = ClazzUtils.getAllClassByInterface(context, RouterAnnotationInit::class.java)
        classes.forEach {
            val instance = it.getField("INSTANCE").get(null)
            if (instance is RouterAnnotationInit) {
                instance.init(RouterAnnotationHandler.Default)
            }
        }
    }
}