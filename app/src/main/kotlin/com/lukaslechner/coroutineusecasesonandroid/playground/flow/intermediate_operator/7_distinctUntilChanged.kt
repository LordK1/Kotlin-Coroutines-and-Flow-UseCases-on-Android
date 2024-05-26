package com.lukaslechner.coroutineusecasesonandroid.playground.flow.intermediate_operator

import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex

suspend fun main() {
    flowOf(1,1, 2, 3, 4, 5,1)
        .distinctUntilChanged()
        .collect {
            println(it)
        }
}