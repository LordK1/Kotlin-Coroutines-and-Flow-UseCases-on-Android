package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase10

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.math.BigInteger
import kotlin.system.measureTimeMillis

class CalculationInBackgroundViewModel : BaseViewModel<UiState>() {

    fun performCalculation(factorialOf: Int) {
        uiState.value = UiState.Loading
        viewModelScope.launch {
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
    }

    private suspend fun calculateFactorial(factorialOf: Int): BigInteger =
        withContext(Dispatchers.Default) {

            var result: BigInteger = BigInteger.ONE
            for (i in 1..factorialOf) {
                result = result.multiply(i.toBigInteger())
            }
            result
        }
}