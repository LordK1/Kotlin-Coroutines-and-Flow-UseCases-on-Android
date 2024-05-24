package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase11

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import java.math.BigInteger
import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.measureTimeMillis

class CooperativeCancellationViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {
    lateinit var job: Job
    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        job = viewModelScope.launch {
            Timber.d("Coroutine Context: ${this.coroutineContext}")
            var calculateFactorial: BigInteger
            val measureTimeMillis = measureTimeMillis {
                calculateFactorial = calculateFactorial(factorialOf)
            }
            var resultString = ""
            val stringConversionMeasureTimeMillis1 = measureTimeMillis {
                withContext(Dispatchers.Default + CoroutineName("String Conversion")) {
                    resultString = calculateFactorial.toString()
                }
            }
            uiState.value = UiState.Success(
                resultString, measureTimeMillis, stringConversionMeasureTimeMillis1
            )
        }
        job.invokeOnCompletion {
            if (it is CancellationException) {
                Timber.d("Calculation Job was cancelled!")
            }
        }
    }

    private suspend fun calculateFactorial(factorialOf: Int): BigInteger =
        withContext(Dispatchers.Default) {

            var result: BigInteger = BigInteger.ONE
            for (i in 1..factorialOf) {
                yield()
                result = result.multiply(i.toBigInteger())
            }
            Timber.d("Calculation factorial completed!")
            result
        }

    fun cancelCalculation() {
        job.cancel()
    }

    fun uiState(): LiveData<UiState> = uiState

    private val uiState: MutableLiveData<UiState> = MutableLiveData()
}