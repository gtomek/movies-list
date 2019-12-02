package uk.co.tomek.popularmovies.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_error.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uk.co.tomek.popularmovies.R
import uk.co.tomek.popularmovies.presentation.model.MainViewState
import uk.co.tomek.popularmovies.presentation.model.MovieModel
import uk.co.tomek.popularmovies.presentation.viewmodel.MainViewModel

/**
 * Displays main application screen.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var moviesListAdapter: MoviesListAdapter
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesListAdapter = MoviesListAdapter(::openDetails)

        recycler_items_list.apply {
            layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.movies_grid_span))
            adapter = moviesListAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as GridLayoutManager
                    if (dy > 0) { // scrolling down
                        val lastVisible = layoutManager.findLastCompletelyVisibleItemPosition()
                        Timber.v("Scrolled down last $lastVisible of ${layoutManager.itemCount}")
                        if (layoutManager.itemCount > 0 && lastVisible == layoutManager.itemCount - 1) {
                            mainViewModel.onBottomReached()
                        }
                    }
                }
            })
        }

        mainViewModel.mainViewState.observe(this, Observer { viewState ->
            viewState?.let { renderState(it) }
        })
    }

    private fun renderState(state: MainViewState) {
        Timber.v("Render view state $state")
        when (state) {
            is MainViewState.Loading -> {
                recycler_items_list.visibility = View.GONE
                layout_error_main.visibility = View.GONE
                progress_bar.visibility = View.VISIBLE
            }
            is MainViewState.Data -> {
                recycler_items_list.visibility = View.VISIBLE
                progress_bar.visibility = View.GONE
                layout_error_main.visibility = View.GONE
                state.itemsResponse.let {
                    moviesListAdapter.updateList(it)
                }

                if (state.itemsResponse.isEmpty()) {
                    //TODO:DisplayEmptyState
                }
            }
            is MainViewState.Error -> {
                recycler_items_list.visibility = View.GONE
                progress_bar.visibility = View.GONE
                layout_error_main.visibility = View.VISIBLE
                Timber.e(state.throwable)
            }
        }
    }

    private fun openDetails(movieModel: MovieModel) {
        Toast.makeText(this, R.string.movie_clicked, Toast.LENGTH_LONG).show()
    }
}
