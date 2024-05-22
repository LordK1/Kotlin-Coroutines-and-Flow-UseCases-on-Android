package com.lukaslechner.coroutineusecasesonandroid.playground.coroutinebuilders

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val startTime = System.currentTimeMillis()
    val deferred = async {
        val result = networkCall(1)
        println("Result1 received : $result after ${System.currentTimeMillis() - startTime}")
        result
    }
    val deferred1 = async {
        val result = networkCall(2)
        println("Result2 received : $result after ${System.currentTimeMillis() - startTime}")
        result
    }
    val results = listOf(deferred.await(), deferred1.await())
    println("results received : $results after ${System.currentTimeMillis() - startTime}")
}

suspend fun networkCall(number: Int): String {
    delay(500)
    return "Result $number"
}