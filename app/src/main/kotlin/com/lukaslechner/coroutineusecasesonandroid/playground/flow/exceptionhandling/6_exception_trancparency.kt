package com.lukaslechner.coroutineusecasesonandroid.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.flow

suspend fun main(): Unit = coroutineScope {
    flow {
        try {
            emit(1)
        } catch (e: Exception) {
            println("catch exception in flow builder")
        }
    }.collect {
        throw Exception("Exception in collect {$it}")
    }
}