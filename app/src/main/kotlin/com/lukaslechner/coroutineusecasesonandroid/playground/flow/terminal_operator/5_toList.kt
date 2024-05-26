package com.lukaslechner.coroutineusecasesonandroid.playground.flow.terminal_operator

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

fun main() {
    val flow = flow {
        delay(100)
        println("Emitting first value")
        emit("1")
        delay(100)
        println("Emitting second value")
        emit("2")
    }

    runBlocking {
        val single = flow.toSet()
        println("Received $single")
    }
    runBlocking {
        val single = flow.toList()
        println("Received $single")
    }
}