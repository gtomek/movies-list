package uk.co.tomek.popularmovies.domain

import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.data.model.Result
import uk.co.tomek.popularmovies.presentation.model.MovieModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Basic mapper that converting data between the data layer and UI
 */
class MoviesDataMapper {

    private var inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.UK)
    private var outputDateFormat = SimpleDateFormat("dd MMM yyyy", Locale.UK)

    fun mapPageToViewData(page: Page): List<MovieModel> = page.results.map { result ->
        MovieModel(result.title,
            result.posterPath,
            "${(result.voteAverage * 10).toInt()}%",
            inputDateFormat.parse(result.releaseDate)?.let {
                outputDateFormat.format(it)
            } ?: "")
    }

}