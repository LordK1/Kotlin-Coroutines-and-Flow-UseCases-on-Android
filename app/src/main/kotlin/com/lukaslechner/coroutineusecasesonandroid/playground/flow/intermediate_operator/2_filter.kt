package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operator

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

suspend fun main() {
    flowOf(1, 2, 3, 4, 5)
        .filter { it > 3 }
        .collect {
        println(it)
    }
}