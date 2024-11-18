package me.zaksen.deathLabyrinth.util

import java.nio.charset.Charset

fun Array<ByteArray>.convertToStrings(): Array<String?> {
    val data = arrayOfNulls<String>(this.size)
    for (i in this.indices) {
        data[i] = String(this[i], Charset.defaultCharset())
    }
    return data
}

fun Array<String>.convertToBytes(): Array<ByteArray?> {
    val data = arrayOfNulls<ByteArray>(this.size)
    for (i in this.indices) {
        val string = this[i]
        data[i] = string.toByteArray(Charset.defaultCharset())
    }
    return data
}