package com.project.androidtemplate.models.list

import com.google.gson.annotations.SerializedName

data class OmdbSearchResponse(
    @SerializedName("Response") val response: String? = null,
    @SerializedName("Search") val search: List<Search>? = null,
    @SerializedName("totalResults") val totalResults: String? = null
)