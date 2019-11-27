package uk.co.tomek.popularmovies.domain

import uk.co.tomek.popularmovies.data.Repository
import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.presentation.model.MainViewState

/**
 * Main interactor implementation.
 */
class MainInteractor(
    private val repository: Repository<Page>,
    private val mapper: MoviesDataMapper
) : Interactor<MainViewState> {

    override suspend fun fetchData(): MainViewState {
        val page = repository.fetchData()
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}