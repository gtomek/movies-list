package uk.co.tomek.popularmovies.data

import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.data.net.MoviesDbNetworkService

class MoviesDbRepository(private val networkSource: MoviesDbNetworkService) : Repository<Page> {
    override suspend fun fetchData(): Page
            = networkSource.getPopularMovies()
}