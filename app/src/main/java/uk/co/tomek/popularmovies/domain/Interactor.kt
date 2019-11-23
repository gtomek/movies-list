package uk.co.tomek.popularmovies.domain

/**
 * Interactor/Use case abstraction used to interact between data and presentation layers
 */
interface Interactor<T> {
    suspend fun fetchData(): T
}