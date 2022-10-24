package com.bravestsnail.processor

import com.squareup.kotlinpoet.*
import java.io.File

class RouterCodeBuilder(
    private val kaptKotlinGeneratedDir: String,
    private val fileName: String,
    private val code: CodeBlock
) {
    private val routerAnnotationInit =
        ClassName("com.bravestsnail.routerhandler", "RouterAnnotationInit")
    private val routerAnnotationHandler =
        ClassName("com.bravestsnail.routerhandler", "RouterAnnotationHandler")
    private val generatedPackage = "com.bravestsnail.routerhandler.generated"

    fun buildFile() = FileSpec.builder(generatedPackage, fileName)
        .addInitClass()
        .build()
        .writeTo(File(kaptKotlinGeneratedDir))

    private fun FileSpec.Builder.addInitClass() = apply {
        addType(
            TypeSpec.classBuilder(fileName)
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