package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.utils.printWithTimePassed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigInteger

fun main() : Unit = runBlocking {
    val startTime = System.currentTimeMillis()
    launch {

        calculateFactorialOfList(5).collect {
            printWithTimePassed(it, startTime)
        }
    }

}

private fun calculateFactorialOfList(number: Int): Flow<BigInteger> = flow {
    var result = BigInteger.ONE
    for (i in 1..number) {
        delay(10)
        result = result.multiply(BigInteger.valueOf(i.toLong()))
        emit(result)
    }

}.flowOn(Dispatchers.Default)
