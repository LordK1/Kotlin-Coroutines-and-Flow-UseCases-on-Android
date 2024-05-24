package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockVersionFeaturesAndroid10
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3.FakeFeaturesErrorApi
import com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3.FakeSuccessApi
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Perform2SequentialNetworkRequestsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = ReplaceMainDispatcherRule()

    private val receivedUiState = mutableListOf<UiState>()

    @Test
    fun `should return Success when both network requests succeed`() {
        val fakeApi = FakeSuccessApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)
        observeViewModel(viewModel)
        viewModel.perform2SequentialNetworkRequest()

        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Success(mockVersionFeaturesAndroid10)),
            receivedUiState
        )
    }

    @Test
    fun `should return Error when first network request fails`(){
        val fakeApi = FakeVersionsErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)
        observeViewModel(viewModel)

        viewModel.perform2SequentialNetworkRequest()

        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Error("Network request error")),
            receivedUiState
        )
    }
    @Test
    fun `should return Error when second network request fails`(){
        val fakeApi = FakeFeaturesErrorApi()
        val viewModel = Perform2SequentialNetworkRequestsViewModel(fakeApi)
        observeViewModel(viewModel)

        viewModel.perform2SequentialNetworkRequest()

        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Error("Network request error")),
            receivedUiState
        )
    }

    private fun observeViewModel(viewModel: Perform2SequentialNetworkRequestsViewModel) {
        viewModel.uiState().observeForever { uiState ->
            receivedUiState.add(uiState)
        }
    }
}