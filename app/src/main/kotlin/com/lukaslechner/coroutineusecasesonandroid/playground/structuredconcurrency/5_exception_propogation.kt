package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Exception handled: $exception")
    }
    val scope = CoroutineScope(SupervisorJob() + exceptionHandler)
    scope.launch {
        println("Coroutine 1 Starts")
        delay(50)
        println("Coroutine 1 fails")
        throw RuntimeException()
    }

    scope.launch {
        println("Coroutine 2 Starts")
        delay(500)
        println("Coroutine 2 Ends")
    }.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine 2 got canceled")
        }
    }

    Thread.sleep(1000)

}