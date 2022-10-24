package com.bravestsnail.routerhandler

interface RouterAnnotationHandler {

    fun register(url: String, target: String)
}