package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.EmptyCoroutineContext

fun main() {
    val flow = flow {
        delay(100)
        println("Emitting first value")
        emit("1")
        delay(100)
        println("Emitting second value")
        emit("2")
    }

    val scope = CoroutineScope(EmptyCoroutineContext)
    flow
        .onEach { println("Collecting $it") }
        .launchIn(scope)
}