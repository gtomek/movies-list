package uk.co.tomek.popularmovies.presentation.model

/**
 * Models representing the view item on a movies list.
 */
data class MovieModel(
    val title: String,
    val imageUrl: String,
    val votingAverage: String,
    val releaseDate: String
)