package com.project.androidtemplate.models.details


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Rating(
    @Json(name = "Source")
    val source: String? = null,
    @Json(name = "Value")
    val value: String? = null
)