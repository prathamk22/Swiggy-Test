package com.project.androidtemplate.ui.list

import com.project.androidtemplate.ui.list.models.MovieResult
import com.project.androidtemplate.utils.ResponseMapper

interface IListRepository {
    suspend fun searchMovie(search: String, page: Int = 1): ResponseMapper<List<MovieResult>>
}