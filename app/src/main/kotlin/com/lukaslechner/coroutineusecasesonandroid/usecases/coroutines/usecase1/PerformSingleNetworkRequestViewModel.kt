package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        Timber.d("I am the first statement in the coroutine")
        uiState.value = UiState.Loading
        val job = viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("Network request failed!")
            }
        }
        Timber.d("I am the first statement after the coroutine")
        job.invokeOnCompletion {
            if (it != CancellationException()) {
                Timber.d("Coroutine cancelled")
            }
        }
    }
}