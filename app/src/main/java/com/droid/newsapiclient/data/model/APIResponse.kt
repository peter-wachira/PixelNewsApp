package com.droid.newsapiclient.data.model


import com.squareup.moshi.Json

data class APIResponse(
    @Json(name = "articles")
    val articles: List<Article>,
    @Json(name = "status")
    val status: String,
    @Json(name = "totalResults")
    val totalResults: Int
)