package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.async
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { delayDemonstration(1, 500) },
        async { delayDemonstration(2, 300) }
    )
}

fun delayDemonstration(number: Int, delay: Long) {
    println("Coroutine $number starts working")
    Handler(Looper.getMainLooper()).postDelayed({
        println("Coroutine $number ends working")
    }, delay)
}
