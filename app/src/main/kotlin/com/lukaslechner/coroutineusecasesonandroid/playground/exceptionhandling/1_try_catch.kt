package com.lukaslechner.coroutineusecasesonandroid.playground.exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(Job())
    scope.launch {
        try {
            launch {
                functionThstThrows()
            }
        } catch (e: Exception) {
            println("Caught exception: $e")
        }
    }
    Thread.sleep(1000)
}

private fun functionThstThrows() {
    throw RuntimeException()
}