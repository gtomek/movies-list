package uk.co.tomek.popularmovies.data

/**
 * Repository abstraction.
 */
interface Repository<T> {
    suspend fun fetchData() : T
}