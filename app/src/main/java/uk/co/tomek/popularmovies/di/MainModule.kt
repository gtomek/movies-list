package uk.co.tomek.popularmovies.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import uk.co.tomek.popularmovies.BuildConfig
import uk.co.tomek.popularmovies.data.MoviesDbRepository
import uk.co.tomek.popularmovies.data.Repository
import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.data.net.BearerAuthenticationInterceptor
import uk.co.tomek.popularmovies.data.net.MoviesDbNetworkService
import uk.co.tomek.popularmovies.domain.Interactor
import uk.co.tomek.popularmovies.domain.MainInteractor
import uk.co.tomek.popularmovies.domain.MoviesDataMapper
import uk.co.tomek.popularmovies.presentation.model.MainViewState
import uk.co.tomek.popularmovies.presentation.viewmodel.MainViewModel

/**
 * KOIN modules declarations.
 */
val applicationModule: Module = module {
    factory<Repository<Page>> {
        MoviesDbRepository(
            get()
        )
    }
    factory<Interactor<MainViewState>> {
        MainInteractor(
            get(),
            get()
        )
    }
    single { MoviesDataMapper() }
    viewModel { MainViewModel(get()) }
}

val networkModule: Module = module {
    single { createOkHttpClient() }
    single {
        creteNetService<MoviesDbNetworkService>(
            get(),
            BuildConfig.SERVER_URL
        )
    }
}

fun createOkHttpClient(): OkHttpClient {
    val logInterceptor = HttpLoggingInterceptor()
    logInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .addInterceptor(BearerAuthenticationInterceptor())
        .build()
}

inline fun <reified T> creteNetService(httpClient: OkHttpClient, baseUrl: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}