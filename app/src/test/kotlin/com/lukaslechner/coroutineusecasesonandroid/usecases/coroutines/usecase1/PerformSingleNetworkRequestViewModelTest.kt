package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lukaslechner.coroutineusecasesonandroid.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.ReplaceMainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PerformSingleNetworkRequestViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = ReplaceMainDispatcherRule()

    private val receivedUiState = mutableListOf<UiState>()

    @Before
    fun setup() {

    }

    @Test
    fun `should return success when network request is successful`() {
        val fakeApi = FakeSuccessApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeApi)
        observeViewModel(viewModel)
        viewModel.performSingleNetworkRequest()
        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Success(mockAndroidVersions)),
            receivedUiState
        )
    }

    @Test
    fun `should return Error when network request fails`() {
        val fakeErrorApi = FakeErrorApi()
        val viewModel = PerformSingleNetworkRequestViewModel(fakeErrorApi)
        observeViewModel(viewModel)
        viewModel.performSingleNetworkRequest()
        Assert.assertEquals(
            listOf(UiState.Loading, UiState.Error("Network request failed!")),
            receivedUiState
        )
    }

    private fun observeViewModel(viewModel: PerformSingleNetworkRequestViewModel) {
        viewModel.uiState().observeForever { uiState ->
            if (uiState != null) {
                receivedUiState.add(uiState)
            }
        }
    }
}