package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Exception handled: $throwable")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)
    scope.launch(exceptionHandler) {
        throw RuntimeException()
    }
    Thread.sleep(100)
}