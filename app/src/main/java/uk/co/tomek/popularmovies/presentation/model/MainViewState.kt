package uk.co.tomek.popularmovies.presentation.model

/**
 * Representation of the main screen view state.
 */
sealed class MainViewState {
    object Loading : MainViewState()
    data class Error(val throwable: Throwable? = null) : MainViewState()
    data class Data(val itemsResponse: Any) : MainViewState()
}