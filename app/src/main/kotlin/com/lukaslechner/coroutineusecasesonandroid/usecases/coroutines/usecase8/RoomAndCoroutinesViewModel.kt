package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase8

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.launch
import timber.log.Timber

class RoomAndCoroutinesViewModel(
    private val api: MockApi,
    private val database: AndroidVersionDao
) : BaseViewModel<UiState>() {

    fun loadData() {
        uiState.value = UiState.Loading.LoadFromDb
        viewModelScope.launch {
            val androidVersions = database.getAndroidVersions()
            if (androidVersions.isEmpty()) {
                uiState.value = UiState.Error(DataSource.DATABASE, "No data found in Database")
            } else {
                uiState.value = UiState.Success(
                    dataSource = DataSource.DATABASE,
                    androidVersions.mapToUiModelList()
                )
            }
            uiState.value = UiState.Loading.LoadFromNetwork
            try {
                val androidVersionsFromNetwork = api.getRecentAndroidVersions()
                androidVersionsFromNetwork.forEach {
                    database.insert(it.mapToEntity())
                }
                uiState.value = UiState.Success(DataSource.NETWORK, androidVersionsFromNetwork)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error(DataSource.NETWORK, "Something went wrong")
            }

        }

    }

    fun clearDatabase() {

    }
}

enum class DataSource(val dataSourceName: String) {
    DATABASE("Database"),
    NETWORK("Network")
}