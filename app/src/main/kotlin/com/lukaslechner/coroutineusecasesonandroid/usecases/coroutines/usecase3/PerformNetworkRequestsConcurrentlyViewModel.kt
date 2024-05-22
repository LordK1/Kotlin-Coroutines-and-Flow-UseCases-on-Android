package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoFeatures = mockApi.getAndroidVersionFeatures(27)
                val peiFeatures = mockApi.getAndroidVersionFeatures(28)
                val android10Features = mockApi.getAndroidVersionFeatures(29)
                val versions = listOf(oreoFeatures, peiFeatures, android10Features)
                uiState.value = UiState.Success(versions)
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.message.toString())
            }

        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        val oreoFeaturesDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(27)
        }
        val pieFeaturesDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(28)
        }
        val android10FeaturesDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(29)
        }

        viewModelScope.launch {

            try {
                val versions = awaitAll(oreoFeaturesDeferred, pieFeaturesDeferred, android10FeaturesDeferred)
                uiState.value = UiState.Success(versions)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }
        }
    }
}