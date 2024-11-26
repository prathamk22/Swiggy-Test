package com.project.androidtemplate.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.androidtemplate.ui.detail.model.MovieDetailState
import com.project.androidtemplate.utils.ResponseMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: IDetailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val uiState: StateFlow<MovieDetailState>
        get() = _uiState.asStateFlow()

    fun loadMovieDetails(movieId: String?) {
        if (movieId.isNullOrBlank()) {
            _uiState.value = MovieDetailState.Error("Movie Id is invalid")
            return
        }
        _uiState.value = MovieDetailState.Loading
        viewModelScope.launch {
            when (val result = detailRepository.getMovieDetails(movieId = movieId)) {
                is ResponseMapper.Error -> {
                    _uiState.emit(MovieDetailState.Error("Something went wrong. Please try again"))
                }

                is ResponseMapper.Success -> {
                    _uiState.emit(MovieDetailState.Details(result.data))
                }
            }
        }
    }

}