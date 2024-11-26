package com.project.androidtemplate.models.list

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("imdbID")
    val imdbID: String? = null,
    @SerializedName("Poster")
    val poster: String? = null,
    @SerializedName("Title")
    val title: String? = null,
    @SerializedName("Type")
    val type: String? = null,
    @SerializedName("Year")
    val year: String? = null
)