package com.lfelipe.codehero.util

import java.math.BigInteger
import java.security.MessageDigest

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

fun Double.roundToNextInt(): Int {
    return if (this - this.toInt() != 0.0) {
        this.toInt() + 1
    } else {
        this.toInt()
    }
}