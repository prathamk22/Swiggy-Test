package com.project.androidtemplate.ui.detail.model

data class MovieUiDetail(
    val movieTitle: String,
    val director: String? = null,
    val released: String? = null,
    val year: String? = null,
    val writer: String? = null,
    val genre: String? = null,
    val poster: String? = null,
)