package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase4

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class VariableAmountOfNetworkRequestsViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                val versionFeaturesList = recentAndroidVersions.map {
                    mockApi.getAndroidVersionFeatures(it.apiLevel)
                }
                uiState.value = UiState.Success(versionFeaturesList)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }

        }
    }

    fun performNetworkRequestsConcurrently() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val deferredList = mockApi.getRecentAndroidVersions().map {
                    async {
                        mockApi.getAndroidVersionFeatures(it.apiLevel)
                    }
                }
                uiState.value = UiState.Success(deferredList.awaitAll())
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed")
            }

        }

    }
}