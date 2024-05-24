package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Exception handled: ${exception.message}")
    }

    val scope = CoroutineScope(Job())
    val deferred = scope.async {
        delay(200)
        throw RuntimeException()
    }
    scope.launch {
        deferred.await()
    }

    Thread.sleep(1000)

}