package uk.co.tomek.popularmovies.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            fetchMovies()
            return _mainViewState
        }

    private fun fetchMovies() {
        _mainViewState.value = MainViewState.Loading
        viewModelScope.launch {
            _mainViewState.value = withContext(dispatcher) {
                return@withContext mainInteractor.fetchData()
            }
        }
    }
}