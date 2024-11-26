package com.project.androidtemplate.ui.list.models

sealed class MovieListState {

    data object Idle: MovieListState()

    data object Loading: MovieListState()

    data class NoResultFound(val message: String): MovieListState()

    data class MovieList(val list: List<MovieResult>, val isLoadingMore: Boolean = false, val currentPage: Int): MovieListState()

    data class Error(val message: String): MovieListState()

}