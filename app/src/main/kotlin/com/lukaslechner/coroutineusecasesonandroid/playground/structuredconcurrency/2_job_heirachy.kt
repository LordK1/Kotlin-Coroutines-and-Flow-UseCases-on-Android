package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)
    val passedJob = Job()
    val coroutineJob = scope.launch(passedJob) {
        println("Starting coroutine")
        delay(1000)
    }
    Thread.sleep(1000)
    println("passedJob and coroutineJob are the same? ${passedJob === coroutineJob}")
    println("Is coroutineJob a child of scopeJob? ${scopeJob.children.contains(coroutineJob)}")
}