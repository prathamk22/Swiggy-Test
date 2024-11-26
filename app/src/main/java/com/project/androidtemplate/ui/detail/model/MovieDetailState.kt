package com.project.androidtemplate.ui.detail.model

sealed class MovieDetailState {

    data object Loading : MovieDetailState()

    data class Details(val detail: MovieUiDetail): MovieDetailState()

    data class Error(val message: String): MovieDetailState()

}