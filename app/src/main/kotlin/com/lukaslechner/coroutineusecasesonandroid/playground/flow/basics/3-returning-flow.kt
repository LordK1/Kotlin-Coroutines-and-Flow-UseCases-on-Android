package com.lukaslechner.coroutineusecasesonandroid.playground.flow.basics

import com.lukaslechner.coroutineusecasesonandroid.playground.utils.printWithTimePassed
import java.math.BigInteger

fun main() {
    val startTime = System.currentTimeMillis()
    val result = calculateFactorialOfList(5).forEach {
        printWithTimePassed(it, startTime)
    }
    println("Result : $result")
}

private fun calculateFactorialOfList(number: Int): Sequence<BigInteger> = sequence {
    var result = BigInteger.ONE
    for (i in 1..number) {
        Thread.sleep(10)
        result = result.multiply(BigInteger.valueOf(i.toLong()))
        yield(result)
    }

}
