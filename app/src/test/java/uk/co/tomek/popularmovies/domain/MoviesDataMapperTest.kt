package uk.co.tomek.popularmovies.domain

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import uk.co.tomek.popularmovies.data.model.Page
import uk.co.tomek.popularmovies.data.model.Result
import uk.co.tomek.popularmovies.presentation.model.MovieModel

class MoviesDataMapperTest {

    private lateinit var dataMapper: MoviesDataMapper

    @Before
    fun setUp() {
        dataMapper = MoviesDataMapper()
    }

    @Test
    fun verifyEmptyResultsSetMapping() {
        // given
        val page = Page(
            0,
            emptyList(),
            0,
            0
        )
        val expected = emptyList<MovieModel>()

        // when
        val result = dataMapper.mapPageToViewData(page)

        // then
        assertEquals(expected, result)
    }

    @Test
    fun verifyMoviesDataIsMappedProperly() {
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
            on { backdropPath } doReturn testImageUrl
            on { releaseDate } doReturn testReleaseDate
            on { voteAverage } doReturn testAverage
        }
        val movie2 = mock<Result> {
            on { title } doReturn testTitle2
            on { backdropPath } doReturn testImageUrl2
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
        val expected = listOf(expectedMovieModel, expectedMovieModel2)

        // when
        val result = dataMapper.mapPageToViewData(page)

        // then
        assertEquals(expected, result)
    }
}