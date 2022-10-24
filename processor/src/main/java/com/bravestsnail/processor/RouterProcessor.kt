package com.bravestsnail.processor

import com.bravestsnail.annotation.Router
import com.squareup.kotlinpoet.CodeBlock
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class RouterProcessor : AbstractProcessor() {

    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    private val logger by lazy { ProcessorLogger(processingEnv) }

    override fun getSupportedAnnotationTypes() = mutableSetOf(Router::class.java.canonicalName)

    /**
     * 处理注解
     *
     * @param annotations 注解
     * @param roundEnv 查询包含特定注解的被注解元素
     * @return
     */
    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {
        if (annotations?.isEmpty() == true) return false
        val kaptKotlinGeneratedDir =
            processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: return false

        val codeBuilder = CodeBlock.Builder()
        val fileName = "AnnotationInit_${UUID.randomUUID().toString().replace("-", "")}"

        //获取所有被Router注解的元素
        roundEnv.getElementsAnnotatedWith(Router::class.java).forEach {
            //校验注解是否合法
            if (!validateActivity(it)) return false

            val annotation = it.getAnnotation(Router::class.java)
            val url = annotation.url
            val className = it.simpleName.toString()
            val packageName = processingEnv.elementUtils.getPackageOf(it).toString()

            val target = "$packageName.$className"
            codeBuilder.addStatement("handler.register(%S, %S)", url, target)
        }
        RouterCodeBuilder(kaptKotlinGeneratedDir, fileName, codeBuilder.build()).buildFile()
        return true
    }

    /**
     * 校验元素是否是Activity
     *
     * @param element 需要校验的元素
     * @return 元素是Activity返回true，否则返回false
     */
    private fun validateActivity(element: Element): Boolean {
       if (element is TypeElement) {
           if (Modifier.ABSTRACT in element.modifiers) {
               logger.e("Router注解不能标记接口、抽象类", element)
           }

            if (!processingEnv.typeUtils.isSubtype(
                    element.asType(),
                    processingEnv.elementUtils.getTypeElement("android.app.Activity").asType()
                )
            ) {
                logger.e("Router注解不能标记${element.simpleName}", element)
                return false
            }

            return true
        } else {
            logger.e("Router注解标记元素必须为类")
            return false
        }
    }
}