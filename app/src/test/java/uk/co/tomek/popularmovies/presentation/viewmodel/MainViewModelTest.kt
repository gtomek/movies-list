package uk.co.tomek.popularmovies.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.tomek.popularmovies.CoroutinesMainTestRule
import uk.co.tomek.popularmovies.domain.Interactor
import uk.co.tomek.popularmovies.presentation.model.MainViewState

/**
 * Basic ViewModel + LiveData test, should be really improved following some ideas from e.g.
 * https://medium.com/androiddevelopers/unit-testing-livedata-and-other-common-observability-problems-bb477262eb04
 * or it could be done with observerForever as in
 * https://medium.com/exploring-android/android-architecture-components-testing-your-viewmodel-livedata-70177af89c6e
 */
@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinsRule: TestRule = CoroutinesMainTestRule()

    @Mock
    private lateinit var interactor: Interactor<MainViewState>

    private lateinit var mainViewModel: MainViewModel

    private val dispatcher: CoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(interactor, dispatcher)
    }

    @Test
    fun verifyThatErrorIsPropagatedWhenLoadingFails() {
        runBlockingTest {
            // given
            val throwable = Exception("An exception")
            val errorState = MainViewState.Error(throwable)
            given(interactor.fetchData()).willReturn(errorState)

            // when
            //mainViewModel.mainViewState

            // then
            assertEquals(errorState, mainViewModel.mainViewState.value)
        }
    }

    @Test
    fun verifyThatDataStateIsPropagated() {
        runBlockingTest {
            // given
            val dataState = MainViewState.Data(mock())
            given(interactor.fetchData()).willReturn(dataState)

            // when
            //mainViewModel.mainViewState

            // then
            assertEquals(dataState, mainViewModel.mainViewState.value)
        }
    }


}