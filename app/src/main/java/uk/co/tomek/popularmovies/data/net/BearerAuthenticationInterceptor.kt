package uk.co.tomek.popularmovies.data.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import uk.co.tomek.popularmovies.BuildConfig

/**
 * OkHttp interceptor adding bearer header.
 */
class BearerAuthenticationInterceptor : Interceptor {

    private val token = BuildConfig.MOVIESDB_API_V3_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest: Request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .method(chain.request().method, chain.request().body)
            .build()
        return chain.proceed(newRequest)
    }
}