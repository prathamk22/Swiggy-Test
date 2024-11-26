package com.project.androidtemplate.detail

import com.project.androidtemplate.ui.detail.IDetailRepository
import com.project.androidtemplate.ui.detail.model.MovieUiDetail
import com.project.androidtemplate.utils.ResponseMapper

class FakeDetailRepository(
    private val responseMapper: ResponseMapper<MovieUiDetail>
) : IDetailRepository {
    override suspend fun getMovieDetails(movieId: String): ResponseMapper<MovieUiDetail> {
        return responseMapper
    }
}

val fakeMovieDetail = MovieUiDetail(
    movieTitle = "movieTitle",
    director = "director",
    released = "released",
    year = "year",
    writer = "writer",
    genre = "genre",
    poster = "poster",
)