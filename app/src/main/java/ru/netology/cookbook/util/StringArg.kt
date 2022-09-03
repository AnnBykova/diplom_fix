package ru.netology.cookbook.util

import android.os.Bundle
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object StringArg: ReadWriteProperty<Bundle, String> {
    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: String) {
        value?.let { thisRef.putString(property.name, it) }
    }

    override fun getValue(thisRef: Bundle, property: KProperty<*>): String {
        return thisRef.getString(property.name).toString()
    }



}