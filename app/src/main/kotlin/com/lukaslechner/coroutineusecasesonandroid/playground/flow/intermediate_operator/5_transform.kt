package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operator

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .transform {
            emit(it)
            emit(it * it)
        }
        .collect {
            println(it)
        }
}