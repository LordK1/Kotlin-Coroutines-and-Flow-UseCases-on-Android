package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun main(): Unit {
    repeat(100_000) {
        thread {
            Thread.sleep(5000)
            println(".")
        }
    }
}