package uk.co.tomek.popularmovies.presentation.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import uk.co.tomek.popularmovies.domain.Interactor
import uk.co.tomek.popularmovies.presentation.model.MainViewState

/**
 * ViewModel for the main app screen.
 */
class MainViewModel(
    private val mainInteractor: Interactor<MainViewState>,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _mainViewState = MutableLiveData<MainViewState>()
    val mainViewState: LiveData<MainViewState>
        get() {
            return _mainViewState
        }

    init {
        fetchFirstPage()
    }

    private fun fetchFirstPage() {
        _mainViewState.value = MainViewState.Loading
        fetchMovies(1)
    }

    @VisibleForTesting
    fun fetchMovies(pageNumber: Int) {
        viewModelScope.launch {
            val lastState = _mainViewState.value
            val newState = withContext(dispatcher) {
                return@withContext mainInteractor.fetchData(pageNumber)
            }

            _mainViewState.value = getNewState(newState, lastState)
        }
    }

    private fun getNewState(
        newState: MainViewState,
        lastState: MainViewState?
    ): MainViewState? {
        return if (newState is MainViewState.Data && lastState is MainViewState.Data) {
            MainViewState.Data(
                itemsResponse = lastState.itemsResponse + newState.itemsResponse,
                totalResults = newState.totalResults,
                totalPages = newState.totalPages,
                lastPage = newState.lastPage
            )
        } else {
            newState
        }
    }

    fun onBottomReached() {
        val lastState = _mainViewState.value
        if (lastState is MainViewState.Data) {
            val nextPage = lastState.lastPage + 1
            Timber.v("Getting page $nextPage of ${lastState.totalPages}")
            if (nextPage < lastState.totalPages) {
                fetchMovies(nextPage)
            }
        }
    }

    fun retryButtonClicked() {
        fetchFirstPage()
    }
}