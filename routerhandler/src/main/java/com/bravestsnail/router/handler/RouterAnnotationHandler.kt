package com.bravestsnail.router.handler

interface RouterAnnotationHandler {
    object Default : RouterAnnotationHandler {
        override fun register(url: String, target: String) {
            RouterUtils.register(url, target)
        }
    }

    fun register(url: String, target: String)
}