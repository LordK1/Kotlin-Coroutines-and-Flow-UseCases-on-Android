package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion

suspend fun main(): Unit = coroutineScope {
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
            emitAll(fallbackFlow())
        }.catch {
            println("Caught 2 exception $it")
        }
        .launchIn(this)
}

private fun stocksFlow(): Flow<String> = flow {
    emit("Apple")
    emit("Google")
    emit("Facebook")
    throw Exception("Something went wrong")
}

private fun fallbackFlow(): Flow<String> = flow {
    emit("Default Stock")
    throw Exception("Something went wrong")
}