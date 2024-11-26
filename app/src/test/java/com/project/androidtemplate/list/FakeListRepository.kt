package com.project.androidtemplate.list

import com.project.androidtemplate.ui.list.IListRepository
import com.project.androidtemplate.ui.list.models.MovieResult
import com.project.androidtemplate.utils.ResponseMapper

class FakeListRepository(
    private val response: ResponseMapper<List<MovieResult>>
) : IListRepository {
    override suspend fun searchMovie(search: String, page: Int): ResponseMapper<List<MovieResult>> {
        return response
    }
}

val fakeMovieResult1 = MovieResult(
    poster = "poster1",
    title = "title1",
    movieId = "movieId1",
)

val fakeMovieResult2 = MovieResult(
    poster = "poster2",
    title = "title2",
    movieId = "movieId2",
)

val fakeMovieResult3 = MovieResult(
    poster = "poster3",
    title = "title3",
    movieId = "movieId3",
)
