package uk.co.tomek.popularmovies.data.model

import com.squareup.moshi.Json

data class Page(
    val page: Int,
    val results: List<Result>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
)
