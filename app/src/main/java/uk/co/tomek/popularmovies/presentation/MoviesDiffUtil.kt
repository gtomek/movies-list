package uk.co.tomek.popularmovies.presentation

import androidx.recyclerview.widget.DiffUtil
import uk.co.tomek.popularmovies.presentation.model.MovieModel

/**
 * Simplified version of diff utils for the movies list.
 */
class MoviesDiffUtil : DiffUtil.ItemCallback<MovieModel>() {
    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem.title == newItem.title


    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
        oldItem == newItem
}