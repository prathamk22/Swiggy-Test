package com.project.androidtemplate.ui.list

import com.project.androidtemplate.data.OmdbService
import com.project.androidtemplate.ui.list.models.MovieResult
import com.project.androidtemplate.utils.ResponseMapper
import kotlinx.coroutines.CancellationException
import java.lang.Exception
import javax.inject.Inject

class ListRepository @Inject constructor(
    private val omdbService: OmdbService
) : IListRepository {

    override suspend fun searchMovie(search: String, page: Int): ResponseMapper<List<MovieResult>> {
        return try {
            val response = omdbService.searchMovie(search, page)
            if (response.isSuccessful) {
                val result =
                    response.body() ?: return ResponseMapper.Error(Exception("Body is null"))
                val movieResults = result.search
                    ?.filter { !it.title.isNullOrBlank() && !it.imdbID.isNullOrBlank() }
                    ?.map { MovieResult(poster = it.poster, title = it.title!!, movieId = it.imdbID!!) } ?: emptyList()
                ResponseMapper.Success(movieResults)
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