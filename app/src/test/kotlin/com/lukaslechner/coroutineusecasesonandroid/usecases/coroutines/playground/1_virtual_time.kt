package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.playground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class SystemUnderTest {
    suspend fun functionWithDelay(): Int {
        delay(1000)
        return 42
    }
}

fun CoroutineScope.functionThatStartsNewCoroutine() {
    launch {
        delay(1000)
        println("Coroutine completed")
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class TestClass {
    @Test
    fun `function with delay should return 42`() = runTest {
        val realTimeStart = System.currentTimeMillis()
        val virtualTimeStart = currentTime
        val systemUnderTest = SystemUnderTest()
        val actual = systemUnderTest.functionWithDelay()

        functionThatStartsNewCoroutine()
        advanceTimeBy(1000)

        Assert.assertEquals(42, actual)
        val realTimeDuration = System.currentTimeMillis() - realTimeStart
        val virtualTimeDuration = currentTime - virtualTimeStart
        println("Test took $realTimeDuration ms vs $virtualTimeDuration ms")
    }
}