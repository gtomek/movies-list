package uk.co.tomek.popularmovies

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.tomek.popularmovies.presentation.MainActivity

/**
 * Example UI test.
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun verifyThatProgressBarIsInitiallyDisplayed() {
        // when
        activityTestRule.launchActivity(null)

        // then
        onView(withId(R.id.progress_bar))
            .check(matches(isDisplayed()))
    }
}