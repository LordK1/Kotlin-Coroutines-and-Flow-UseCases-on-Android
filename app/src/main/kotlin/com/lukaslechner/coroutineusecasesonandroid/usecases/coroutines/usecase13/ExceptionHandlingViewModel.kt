package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase13

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                api.getAndroidVersionFeatures(27)
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network request failed: ${e.message}")
            }
        }
    }

    fun handleWithCoroutineExceptionHandler() {
        uiState.value = UiState.Loading
        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            uiState.value = UiState.Error("Network request failed: ${throwable.message}")
        }
        viewModelScope.launch(exceptionHandler) {
            api.getAndroidVersionFeatures(27)
        }
    }

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            supervisorScope {
                val oreoDeferred = async {
                    api.getAndroidVersionFeatures(27)
                }
                val pieDeferred = async {
                    api.getAndroidVersionFeatures(28)
                }
                val android10Deferred = async {
                    api.getAndroidVersionFeatures(29)
                }

                val features = listOf(oreoDeferred, pieDeferred, android10Deferred)
                    .mapNotNull {
                        try {
                            it.await()
                        } catch (e: Exception) {
                            if (e is CancellationException) {
                                throw e
                            }
                            Timber.e("Error Loading feature data!")
                            null
                        }
                    }

                uiState.value = UiState.Success(features)
            }
        }
    }
}