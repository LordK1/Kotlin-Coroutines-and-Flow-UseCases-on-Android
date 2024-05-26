package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun main() {
    val flow = flow {
        delay(100)
        println("Emitting first value")
        emit("1")
        delay(100)
        println("Emitting second value")
        emit("2")
    }

    val list = buildList {
        add(1)
        println("Adding first value")
        add(2)
        println("Adding second value")
    }
}