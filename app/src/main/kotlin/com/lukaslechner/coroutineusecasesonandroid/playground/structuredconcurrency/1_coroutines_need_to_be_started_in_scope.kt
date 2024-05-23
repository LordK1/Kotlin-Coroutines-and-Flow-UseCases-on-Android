package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException


val scope = CoroutineScope(Dispatchers.Default)
fun main() = runBlocking {
    val job = scope.launch {
        delay(100)
        println("Coroutine Completed")
    }
    job.invokeOnCompletion {
        if (it is CancellationException) {
            println("Coroutine Completed with $it")
        }
    }
    delay(50)
    onDestroy()
}

fun onDestroy() {
    println("life-time of scope ends")
    scope.cancel()
}