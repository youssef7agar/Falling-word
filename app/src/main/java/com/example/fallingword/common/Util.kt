package com.example.fallingword.common

import android.content.SharedPreferences
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun SharedPreferences.int(
    defaultValue: Int = 0,
    key: (KProperty<*>) -> String = KProperty<*>::name
): ReadWriteProperty<Any, Int> =
    object : ReadWriteProperty<Any, Int> {
        override fun getValue(
            thisRef: Any,
            property: KProperty<*>
        ) = getInt(key(property), defaultValue)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: Int
        ) = edit().putInt(key(property), value).apply()
    }

fun randomBoolean(): Boolean {
    return Math.random() < 0.5
}

fun randomInt(end: Int): Int {
    return ThreadLocalRandom.current().nextInt(0, end)
}

fun randomString(): String {
    return UUID.randomUUID().toString()
}