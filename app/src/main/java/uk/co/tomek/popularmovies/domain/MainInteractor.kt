package uk.co.tomek.popularmovies.domain

import uk.co.tomek.popularmovies.data.Repository
import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.presentation.model.MainViewState

/**
 * Main interactor implementation.
 */
class MainInteractor(private val repository: Repository<Page>) : Interactor<MainViewState> {

    override suspend fun fetchData(): MainViewState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}