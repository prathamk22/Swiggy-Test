package com.project.androidtemplate.detail

import app.cash.turbine.test
import com.project.androidtemplate.ui.detail.DetailViewModel
import com.project.androidtemplate.ui.detail.model.MovieDetailState
import com.project.androidtemplate.utils.ResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    private val fakeDetailRepository = FakeDetailRepository(ResponseMapper.Success(fakeMovieDetail))
    private var detailViewModel = DetailViewModel(fakeDetailRepository)

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun testMovieDetailsSuccessCase() = runTest {
        detailViewModel.loadMovieDetails("1234")
        detailViewModel.uiState.test {
            assert(MovieDetailState.Loading == awaitItem())
            val finalItem = awaitItem()
            assert(finalItem is MovieDetailState.Details)
            assert((finalItem as MovieDetailState.Details).detail == fakeMovieDetail)
        }
    }

    @Test
    fun `test movie details movie Id invalid case`() = runTest {
        detailViewModel.loadMovieDetails("")
        detailViewModel.uiState.test {
            assert(MovieDetailState.Error("Movie Id is invalid") == awaitItem())
        }
    }

    @Test
    fun `test movie details invalid response error`() = runTest {
        detailViewModel = DetailViewModel(FakeDetailRepository(ResponseMapper.Error(Exception("Error is here"))))
        detailViewModel.loadMovieDetails("1234")
        detailViewModel.uiState.test {
            assert(MovieDetailState.Loading == awaitItem())
            assert(MovieDetailState.Error("Something went wrong. Please try again") == awaitItem())
        }
    }

}