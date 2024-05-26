package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        stocksFlow()
            .catch {
                println("Handle exception in catch() operator $it")
            }
            .collect {
                println(it)
            }
    }

}

private fun stocksFlow(): Flow<String> = flow {
    repeat(5) {
        delay(1000)
        if (it < 4) {
            emit("New Stock data")
        } else {
            throw Exception("Network Request Failed!")
        }
    }
}.retryWhen { cause, attempts ->
    println("Enter retry function with cause $cause in attemps $attempts")
    delay(1000 * attempts + 1)
    cause is Exception
}