package uk.co.tomek.popularmovies.domain

import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.tomek.popularmovies.data.Repository
import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.data.model.Result
import uk.co.tomek.popularmovies.presentation.model.MainViewState
import uk.co.tomek.popularmovies.presentation.model.MovieModel

class MainInteractorTest {

    @Mock
    private lateinit var repository: Repository<Page>

    private lateinit var interactor: MainInteractor

    private val mapper = MoviesDataMapper()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        interactor = MainInteractor(repository, mapper)
    }

    @Test
    fun verifyAnErrorIsPropagatedWhenConnectionFails() {
        // given
        val expection = RuntimeException("an expection")
        val expected = MainViewState.Error(expection)

        // when
        runBlocking { whenever(repository.fetchData(any())) }.thenThrow(expection)
        val result = runBlocking { interactor.fetchData(1) }

        // then
        assertEquals(expected, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun verifyThatDataStateIsReturnedInCaseOfNormalDataFetch() {

        runBlockingTest {
            // given
            val testTitle = "TestTitle"
            val testTitle2 = "TestTitle2"
            val testImageUrl = "testImageUrl"
            val testImageUrl2 = "testImageUrl2"
            val testReleaseDate = "2017-12-25"
            val testReleaseDate2 = "2019-12-17"
            val testAverage = 6.6
            val testAverage2 = 0.0
            val movie1 = mock<Result> {
                on { title } doReturn testTitle
                on { posterPath } doReturn testImageUrl
                on { releaseDate } doReturn testReleaseDate
                on { voteAverage } doReturn testAverage
            }
            val movie2 = mock<Result> {
                on { title } doReturn testTitle2
                on { posterPath } doReturn testImageUrl2
                on { releaseDate } doReturn testReleaseDate2
                on { voteAverage } doReturn testAverage2
            }
            val page = Page(
                1,
                listOf(movie1, movie2),
                1,
                2
            )
            val expectedMovieModel = MovieModel(
                testTitle,
                testImageUrl,
                "66%",
                "25 Dec 2017"
            )
            val expectedMovieModel2 = MovieModel(
                testTitle2,
                testImageUrl2,
                "0%",
                "17 Dec 2019"
            )
            val expectedMovies = listOf(expectedMovieModel, expectedMovieModel2)
            given(repository.fetchData(any())).willReturn(page)
            val expectedState = MainViewState.Data(expectedMovies,
                totalPages = 1,
                totalResults = 2,
                lastPage = 1)

            // when
            val result = interactor.fetchData(1)

            // then
            assertEquals(expectedState, result)
        }

    }
}