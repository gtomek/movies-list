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

    override suspend fun fetchData(pageNumber: Int): MainViewState {
        return try {
            val page = repository.fetchData(pageNumber)
            MainViewState.Data(
                mapper.mapPageToViewData(page),
                totalPages = page.totalPages,
                totalResults = page.totalResults,
                lastPage = pageNumber
            )
        } catch (exception: Exception) {
            MainViewState.Error(exception)
        }
    }

}