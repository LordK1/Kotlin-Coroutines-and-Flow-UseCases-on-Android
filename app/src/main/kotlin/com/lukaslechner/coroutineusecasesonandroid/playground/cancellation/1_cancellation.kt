package com.lukaslechner.coroutineusecasesonandroid.playground.cancellation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

fun main(): Unit = runBlocking {
    val job = launch {
        repeat(100) {
            println("Operation number $it")
            delay(100)
        }
    }
    delay(250)
    println("Cancelling Coroutine")
    job.cancel()
}