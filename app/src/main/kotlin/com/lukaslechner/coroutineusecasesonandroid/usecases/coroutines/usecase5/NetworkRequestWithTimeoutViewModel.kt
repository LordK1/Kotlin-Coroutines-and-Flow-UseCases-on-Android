package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading
//        usingTimeOut(timeout)
        usingTimeOutOrNull(timeout)
    }

    private fun usingTimeOut(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeout(timeout) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (e: TimeoutCancellationException) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request timed out")
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

    private fun usingTimeOutOrNull(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersions = withTimeoutOrNull(timeout) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = recentAndroidVersions?.let {
                    UiState.Success(it)
                } ?: UiState.Error("Network request timed out")
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }

}