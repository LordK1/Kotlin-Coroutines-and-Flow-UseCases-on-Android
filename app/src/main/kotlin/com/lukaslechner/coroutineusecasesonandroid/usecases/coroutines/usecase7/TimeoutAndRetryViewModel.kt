package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber
import java.sql.Time

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        val oreoVersionDeferred = viewModelScope.async {
            retryWithTime(numberOfRetries, timeout) { api.getAndroidVersionFeatures(27) }
        }

        val pieVersionDeferred = viewModelScope.async {
            retryWithTime(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val oreoVersion = oreoVersionDeferred.await()
                val pieVersion = pieVersionDeferred.await()
                uiState.value = UiState.Success(listOf(oreoVersion, pieVersion))
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    private suspend fun <T> retryWithTime(
        numberOfRetries: Int,
        timeOutMillis: Long,
        block: suspend () -> T
    ): T {
        return retry(numberOfRetries) {
            withTimeout(timeOutMillis) {
                block()
            }
        }
    }

    private suspend fun <T> retry(
        numberOfRetries: Int,
        delayBetweenRetries: Long = 100,
        block: suspend () -> T
    ): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (e: Exception) {
                Timber.e(e)
            }
            delay(delayBetweenRetries)
        }
        return block()
    }
}