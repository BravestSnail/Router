package com.bravestsnail.router.processor

import com.squareup.kotlinpoet.*
import java.io.File

class RouterCodeBuilder(
    private val kaptKotlinGeneratedDir: String,
    private val fileName: String,
    private val code: CodeBlock
) {
    private val routerAnnotationInit =
        ClassName("com.bravestsnail.router.handler", "RouterAnnotationInit")
    private val routerAnnotationHandler =
        ClassName("com.bravestsnail.router.handler", "RouterAnnotationHandler")
    private val generatedPackage = "com.bravestsnail.router.handler.generated"


    fun buildFile() = FileSpec.builder(generatedPackage, fileName)
        .addInitClass()
        .build()
        .writeTo(File(kaptKotlinGeneratedDir))

    private fun FileSpec.Builder.addInitClass() = apply {
        addType(
            TypeSpec.objectBuilder(fileName)
                .addSuperinterface(routerAnnotationInit)
                .addInitMethod(code)
                .build()
        )
    }

    private fun TypeSpec.Builder.addInitMethod(code: CodeBlock) = apply {
        addFunction(FunSpec.builder("init")
            .addModifiers(KModifier.OVERRIDE)
            .addParameter("handler", routerAnnotationHandler)
            .returns(UNIT)
            .addCode(code)
            .build()
        )
    }
}