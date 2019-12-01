package uk.co.tomek.popularmovies.data.net

import retrofit2.http.GET
import uk.co.tomek.popularmovies.BuildConfig
import uk.co.tomek.popularmovies.data.model.Page

/**
 * Retrofit network service definition for movies DB.
 */
interface MoviesDbNetworkService {

    @GET("3/movie/popular?api_key=${BuildConfig.MOVIESDB_API_V3_KEY}")
    suspend fun getPopularMovies(): Page

}