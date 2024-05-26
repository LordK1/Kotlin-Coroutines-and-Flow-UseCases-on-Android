package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

suspend fun main(): Unit = coroutineScope {
    launch {
        val stocksFlow = stocksFlow()

        stocksFlow
            .onCompletion {
                if (it == null) {
                    println("Flow completed successfully")
                } else {
                    println("Flow completed with exception $it")
                }
            }
            .catch {
                println("Caught exception $it")
                emit("Default Stock")
            }
            .collect {
                println("Collected : $it")
            }

    }
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("Google")
    emit("Facebook")
    throw Exception("Something went wrong")
}
