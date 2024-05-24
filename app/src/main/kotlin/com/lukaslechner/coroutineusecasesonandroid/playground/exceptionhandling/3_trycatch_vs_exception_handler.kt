package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught exception $throwable")
    }
    val scope = CoroutineScope(Job())

    scope.launch(exceptionHandler) {
        launch {
            println("Starting coroutine 1")
            delay(100)
            throw RuntimeException()

        }
        launch {
            println("Starting coroutine 2")
            delay(3000)
            println("Ending coroutine 2")
        }
    }
    Thread.sleep(5000)

}