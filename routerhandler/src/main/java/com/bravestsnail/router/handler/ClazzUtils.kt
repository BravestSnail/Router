package com.bravestsnail.router.handler

import android.content.Context
import dalvik.system.DexFile
import java.io.IOException

object ClazzUtils {
    //给一个接口，返回这个接口的所有实现类
    fun getAllClassByInterface(context: Context, c: Class<*>): List<Class<*>> {
        val returnClassList: MutableList<Class<*>> = ArrayList() //返回结果
        //如果不是一个接口，则不做处理
        if (c.isInterface) {
            val packageName = c.getPackage()?.name ?: return returnClassList//获得当前的包名
            try {
                val allClass = getClasses(context, packageName) //获得当前包下以及子包下的所有类
                //判断是否是同一个接口
                for (i in allClass.indices) {
                    if (c.isAssignableFrom(allClass[i])) { //判断是不是一个接口
                        if (c != allClass[i]) { //本身不加进去
                            returnClassList.add(allClass[i])
                        }
                    }
                }
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return returnClassList
    }

    //从一个包中查找出所有的类，在jar包中不能查找
    @Throws(ClassNotFoundException::class, IOException::class)
    private fun getClasses(context: Context, packageName: String): List<Class<*>> {
        val dexFileClassNames = getDexFileClassNames(context, packageName)
        val classes = ArrayList<Class<*>>()
        for (s in dexFileClassNames) {
            try {
                val scanClass = Class.forName(s)
                classes.add(scanClass)
            } catch (e: Exception) {
                continue
            }
        }
        return classes
    }

    @Throws(IOException::class)
    fun getDexFileClassNames(context: Context, packageName: String?): List<String> {
        val df = DexFile(context.packageCodePath) //通过DexFile查找当前的APK中可执行文件
        val enumeration = df.entries() //获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
        val classes: MutableList<String> = ArrayList()
        while (enumeration.hasMoreElements()) { //遍历
            val className = enumeration.nextElement()
            if (className.startsWith(packageName!!)) {
                classes.add(className)
            }
        }
        return classes
    }
}