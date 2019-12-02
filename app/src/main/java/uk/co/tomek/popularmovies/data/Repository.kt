package uk.co.tomek.popularmovies.data

/**
 * Repository abstraction.
 * In this particular case it is redundant (because we have the code in the integrator)
 * Just keeping it for a more generic case.
 */
interface Repository<T> {
    suspend fun fetchData(pageNumber: Int = 1): T
}