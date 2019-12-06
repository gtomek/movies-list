package uk.co.tomek.popularmovies.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.tomek.popularmovies.MainCoroutineScopeRule
import uk.co.tomek.popularmovies.captureValues
import uk.co.tomek.popularmovies.domain.Interactor
import uk.co.tomek.popularmovies.getValueForTest
import uk.co.tomek.popularmovies.presentation.model.MainViewState

/**
 * Basic ViewModel + LiveData test, it could be improved following some ideas from e.g.
 * https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
 * or it could be done with observerForever as in
 * https://medium.com/exploring-android/android-architecture-components-testing-your-viewmodel-livedata-70177af89c6e
 * testing with viewModelScope
 * https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
 * https://github.com/googlecodelabs/kotlin-coroutines/blob/master/coroutines-codelab/finished_code/src/test/java/com/example/android/kotlincoroutines/main/MainViewModelTest.kt
 */
@ExperimentalCoroutinesApi
class MainViewModelTest {

    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule(dispatcher)

    @Mock
    private lateinit var interactor: Interactor<MainViewState>

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(interactor, dispatcher)
    }

    @Test
    fun verifyThatErrorIsPropagatedWhenLoadingFails() {
        coroutineScope.runBlockingTest {
            // given
            val throwable = Exception("An exception")
            val errorState = MainViewState.Error(throwable)
            given(interactor.fetchData(any())).willReturn(errorState)

            // when
            mainViewModel.fetchMovies(1)

            // then
            mainViewModel.mainViewState.captureValues {
                assertEquals(errorState, mainViewModel.mainViewState.getValueForTest())
            }
        }
    }

    @Test
    fun verifyThatDataStateIsPropagated() {
        coroutineScope.runBlockingTest {
            // given
            val dataState = MainViewState.Data(mock(), lastPage = 1)
            given(interactor.fetchData(any())).willReturn(dataState)

            // when
            mainViewModel.fetchMovies(1)

            // then
            mainViewModel.mainViewState.captureValues {
                assertEquals(dataState, mainViewModel.mainViewState.getValueForTest())
            }
        }
    }


}