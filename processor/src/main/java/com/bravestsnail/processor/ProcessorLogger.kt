package com.bravestsnail.processor

import javax.annotation.processing.ProcessingEnvironment
import javax.lang.model.element.Element
import javax.tools.Diagnostic

class ProcessorLogger(private val env: ProcessingEnvironment) {
    /**
    * 打印注释级别日志
    */
    fun n(message: String, element: Element? = null) {
        print(Diagnostic.Kind.NOTE, message, element)
    }

    /**
    * 打印警告级别日志
    */
    fun w(message: String, element: Element? = null) {
        print(Diagnostic.Kind.WARNING, message, element)
    }

    /**
    * 打印错误级别日志
    */
    fun e(message: String, element: Element? = null) {
        print(Diagnostic.Kind.ERROR, message, element)
    }

    private fun print(kind: Diagnostic.Kind, message: String, element: Element?) {
        print("\n")
        env.messager.printMessage(kind, message, element)  // 2

    }
}