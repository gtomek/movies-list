package uk.co.tomek.popularmovies.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uk.co.tomek.popularmovies.domain.Interactor
import uk.co.tomek.popularmovies.presentation.model.MainViewState

/**
 * ViewModel for the main app screen.
 */
class MainViewModel(private val mainInteractor: Interactor<MainViewState>) : ViewModel() {

    val mainLiveData = MutableLiveData<MainViewState>()
}