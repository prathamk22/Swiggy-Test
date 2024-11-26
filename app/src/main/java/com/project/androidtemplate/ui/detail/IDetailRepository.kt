package com.project.androidtemplate.ui.detail

import com.project.androidtemplate.ui.detail.model.MovieUiDetail
import com.project.androidtemplate.utils.ResponseMapper

interface IDetailRepository {
    suspend fun getMovieDetails(movieId: String): ResponseMapper<MovieUiDetail>
}