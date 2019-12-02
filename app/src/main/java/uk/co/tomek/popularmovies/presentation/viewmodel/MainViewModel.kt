package uk.co.tomek.popularmovies.presentation.viewmodel

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
            _mainViewState.value = MainViewState.Loading
            fetchMovies(1)
            return _mainViewState
        }

    private fun fetchMovies(pageNumber: Int) {
        viewModelScope.launch {
            _mainViewState.value = withContext(dispatcher) {
                return@withContext mainInteractor.fetchData(pageNumber)
            }
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
}