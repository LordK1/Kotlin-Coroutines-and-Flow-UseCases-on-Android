package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    try {
        extracted()
    } catch (e: Exception) {
        println("Caught $e")
    }
}

suspend fun extracted() {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }
    coroutineScope {
        launch(exceptionHandler) {
            throw RuntimeException()
        }
    }
}
