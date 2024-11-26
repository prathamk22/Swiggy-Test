package com.project.androidtemplate.data

import com.project.androidtemplate.models.details.OmdbMovieDetailResponse
import com.project.androidtemplate.models.list.OmdbSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET("/")
    suspend fun searchMovie(@Query("s") search: String, @Query("page") page: Int): Response<OmdbSearchResponse>

    @GET("/")
    suspend fun getMovieDetails(@Query("i") movieId: String): Response<OmdbMovieDetailResponse>

}