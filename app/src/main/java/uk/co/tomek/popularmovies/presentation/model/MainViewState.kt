package uk.co.tomek.popularmovies.presentation.model

/**
 * Representation of the main screen view state.
 */
sealed class MainViewState {
    object Loading : MainViewState()
    data class Error(val throwable: Throwable? = null) : MainViewState()
    data class Data(
        val itemsResponse: List<MovieModel>,
        val totalPages: Int = 0,
        val totalResults: Int = 0,
        val lastPage: Int
    ) : MainViewState()
}