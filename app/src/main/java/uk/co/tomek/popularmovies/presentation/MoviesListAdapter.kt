package uk.co.tomek.popularmovies.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import kotlinx.android.synthetic.main.item_movies_list.view.*
import timber.log.Timber
import uk.co.tomek.popularmovies.BuildConfig
import uk.co.tomek.popularmovies.R
import uk.co.tomek.popularmovies.presentation.model.MovieModel

class MoviesListAdapter(
    private val clickListener: (MovieModel) -> Unit
) : ListAdapter<MovieModel, RecyclerView.ViewHolder>(MoviesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movies_list, parent, false)
        return MovieItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MovieItemViewHolder).bind(getItem(position), clickListener)
    }

    fun updateList(newList: List<MovieModel>) {
        submitList(currentList + newList)
    }

    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(
            model: MovieModel,
            clickListener: (MovieModel) -> Unit
        ) {
            Timber.v("Bind MovieItemViewHolder $model")
            with(itemView) {
                imageview_poster.load(BuildConfig.IMAGE_SERVER_URL + model.imageUrl)
                textview_title.text = model.title
                textview_voting_average.text = model.votingAverage
                textview_release_date.text = model.releaseDate
                itemView.setOnClickListener { clickListener.invoke(model) }
            }
        }
    }
}