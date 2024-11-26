package com.project.androidtemplate.ui.detail

import com.project.androidtemplate.data.OmdbService
import com.project.androidtemplate.ui.detail.model.MovieUiDetail
import com.project.androidtemplate.utils.ResponseMapper
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val omdbService: OmdbService
) : IDetailRepository {

    override suspend fun getMovieDetails(movieId: String): ResponseMapper<MovieUiDetail> {
        return try {
            val response = withContext(Dispatchers.IO) { omdbService.getMovieDetails(movieId = movieId) }
            if (response.isSuccessful) {
                val result =
                    response.body() ?: return ResponseMapper.Error(Exception("Body is null"))
                if (result.title.isNullOrBlank()) {
                    ResponseMapper.Error(Exception("Response is not valid"))
                } else {
                    ResponseMapper.Success(
                        MovieUiDetail(
                            movieTitle = result.title,
                            director = result.director,
                            released = result.released,
                            year = result.year,
                            writer = result.writer,
                            genre = result.genre,
                            poster = result.poster,
                        )
                    )
                }
            } else {
                ResponseMapper.Error(Exception("Response is not valid"))
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            return ResponseMapper.Error(e)
        }
    }

}