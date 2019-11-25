package uk.co.tomek.popularmovies.data

import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.data.net.MoviesDbService

class MoviesDbRepository(val networkSource: MoviesDbService) : Repository<Page> {
    override suspend fun fetchData(): Page {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}