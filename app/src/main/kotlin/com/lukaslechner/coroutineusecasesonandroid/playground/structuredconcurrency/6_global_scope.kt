package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job

@OptIn(DelicateCoroutinesApi::class)
fun main() {

    println("Job of GlobalScope is ${GlobalScope.coroutineContext[Job]}")
}