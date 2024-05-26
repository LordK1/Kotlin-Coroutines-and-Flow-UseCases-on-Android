package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operator

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .take(3)
        .collect {
        println(it)
    }
}