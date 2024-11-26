package com.project.androidtemplate.list

import app.cash.turbine.test
import com.project.androidtemplate.ui.list.ListViewModel
import com.project.androidtemplate.ui.list.models.MovieListState
import com.project.androidtemplate.utils.ResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.lang.Exception

class ListViewModelTests {

    private val fakeList = listOf(
        fakeMovieResult1, fakeMovieResult2, fakeMovieResult3
    )
    private val fakeListRepository = FakeListRepository(ResponseMapper.Success(fakeList))

    @Before
    fun before() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test list viewmodel success case`() = runTest {
        val listViewModel = ListViewModel(fakeListRepository)
        listViewModel.uiState.test {
            listViewModel.updateSearchKey("Avengers")
            assert(awaitItem() == MovieListState.Idle)
            assert(awaitItem() == MovieListState.Loading)
            assert(awaitItem() == MovieListState.MovieList(fakeList, false, 1))
        }
    }

    @Test
    fun `test list viewmodel error case`() = runTest {
        val fakeListRepository = FakeListRepository(ResponseMapper.Error(Exception("Something went wrong")))
        val listViewModel = ListViewModel(fakeListRepository)
        listViewModel.uiState.test {
            listViewModel.updateSearchKey("Avengers")
            assert(awaitItem() == MovieListState.Idle)
            assert(awaitItem() == MovieListState.Loading)
            assert(awaitItem() == MovieListState.Error("Something went wrong. Please try again"))
        }
    }


}