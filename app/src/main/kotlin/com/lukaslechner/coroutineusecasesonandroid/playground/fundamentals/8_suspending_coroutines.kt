package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")
    joinAll(
        async { suspendingCoroutine(1, 500) },
        async { suspendingCoroutine(2, 300) },
        async {
            repeat(5) {
                println("Other task is working on ${Thread.currentThread().name}")
                delay(500)
            }
        }
    )
}

suspend fun suspendingCoroutine(number: Int, delay: Long) {
    println("Coroutine $number starts working on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}