package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        val stocksFlow = stocksFlow()
            .map {
                throw Exception("Something went wrong")
            }
        try {
            stocksFlow
                .onCompletion {
                    if (it == null) {
                        println("Flow completed successfully")
                    } else {
                        println("Flow completed with exception $it")
                    }
                }
                .collect {
                    println("Collected : $it")
                }
        } catch (e: Exception) {
            println("Caught exception $e")
        }
    }
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("Google")
    emit("Facebook")
    throw Exception("Something went wrong")
}
