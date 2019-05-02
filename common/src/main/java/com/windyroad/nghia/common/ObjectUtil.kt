package com.windyroad.nghia.common

import java.lang.reflect.Field

class ObjectUtil {
    companion object {
        /// <summary> Gán dữ liệu đến dữ liệu </summary>
        fun copyProperties(from: Any, to: Any) {
            val packageName = this.javaClass.name.substring(0, this.javaClass.name.lastIndexOf("."))

            val fields = from.javaClass.declaredFields
            for (field in fields) {
                try {
                    val fieldFrom = from.javaClass.getDeclaredField(field.name)
                    fieldFrom.isAccessible = true //access private field
                    val value = fieldFrom.get(from)

                    val fieldTo = to.javaClass.getDeclaredField(field.name)
                    fieldTo.isAccessible = true //access private field


                    // check type
                    if (fieldFrom == null || value == null) continue //bỏ qua null

                    if (fieldFrom.type.name.contains(packageName)
                            || !fieldFrom.type.name.contains("java"))
                        continue


                    fieldTo.set(to, value)

                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: NoSuchFieldException) {
                    e.printStackTrace()
                }

            }
        }
    }
}