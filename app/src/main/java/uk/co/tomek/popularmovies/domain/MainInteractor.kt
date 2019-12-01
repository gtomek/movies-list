package uk.co.tomek.popularmovies.domain

import uk.co.tomek.popularmovies.data.MoviesDbRepository
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
        return try {
            val page = repository.fetchData()
            MainViewState.Data(mapper.mapPageToViewData(page))
        } catch (exception: Exception) {
            MainViewState.Error(exception)
        }
    }
}