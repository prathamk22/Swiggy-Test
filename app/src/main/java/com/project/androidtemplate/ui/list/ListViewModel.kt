@file:OptIn(FlowPreview::class)

package com.project.androidtemplate.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.androidtemplate.ui.list.models.MovieListState
import com.project.androidtemplate.utils.ResponseMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listRepository: IListRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _uiState = MutableStateFlow<MovieListState>(MovieListState.Idle)
    val uiState: StateFlow<MovieListState>
        get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(350L)
                .filter {
                    if (it.isBlank()) {
                        _uiState.emit(MovieListState.Idle)
                        false
                    } else {
                        true
                    }
                }
                .distinctUntilChanged()
                .collectLatest {
                    searchForMovie(it)
                }
        }
    }

    private fun searchForMovie(searchQuery: String, page: Int = 1) {
        viewModelScope.launch {
            if (_uiState.value is MovieListState.MovieList) {
                _uiState.update {
                    (it as MovieListState.MovieList).copy(isLoadingMore = true)
                }
            } else {
                _uiState.update {
                    MovieListState.Loading
                }
            }
            when (val result = listRepository.searchMovie(searchQuery, page)) {
                is ResponseMapper.Error -> {
                    _uiState.update {
                        MovieListState.Error("Something went wrong. Please try again")
                    }
                }

                is ResponseMapper.Success -> {
                    _uiState.update {
                        if (it is MovieListState.MovieList) {
                            it.copy(
                                list = it.list.toMutableList().apply {
                                    addAll(result.data)
                                },
                                currentPage = page,
                                isLoadingMore = false
                            )
                        } else {
                            if (result.data.isEmpty()) {
                                MovieListState.NoResultFound("No movies found for $searchQuery. Please update the query")
                            } else {
                                MovieListState.MovieList(
                                    result.data,
                                    currentPage = page,
                                    isLoadingMore = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateSearchKey(searchKey: String) {
        _searchQuery.value = searchKey
    }

    fun loadMore() {
        val currentPage = (_uiState.value as? MovieListState.MovieList)?.currentPage ?: 1
        searchForMovie(_searchQuery.value, currentPage + 1)
    }

}