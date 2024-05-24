package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

fun main(): Unit {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception in CoroutineExceptionHandler")
    }
    val scope = CoroutineScope(Job() + exceptionHandler)
    scope.launch {
        try {
            supervisorScope {
                launch {
                    throw RuntimeException()
                }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }
}