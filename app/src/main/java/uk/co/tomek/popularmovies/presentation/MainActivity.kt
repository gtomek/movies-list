package uk.co.tomek.popularmovies.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import uk.co.tomek.popularmovies.R
import uk.co.tomek.popularmovies.presentation.model.MainViewState
import uk.co.tomek.popularmovies.presentation.viewmodel.MainViewModel

/**
 * Displays main application screen.
 */
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.mainViewState.observe(this, Observer { viewState ->
            viewState?.let { renderState(it) }
        })
    }

    private fun renderState(viewState: MainViewState) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
