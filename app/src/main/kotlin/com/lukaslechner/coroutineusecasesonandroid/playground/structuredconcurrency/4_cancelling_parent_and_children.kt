package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking{
    val scope = CoroutineScope(Dispatchers.Default)
    scope.coroutineContext[Job]!!.invokeOnCompletion {
        if (it is CancellationException) {
         println("Coroutine was cancelled")
        }
    }

    val coroutine1Job = scope.launch {
        delay(1000)
        println("Coroutine 1 was cancelled")
    }
    coroutine1Job.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine 1 was cancelled")
        }
    }
    scope.launch {
        delay(1000)
        println("Coroutine 2 was cancelled")
    }.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine 2 was cancelled")
        }
    }


    coroutine1Job.cancelAndJoin()
}