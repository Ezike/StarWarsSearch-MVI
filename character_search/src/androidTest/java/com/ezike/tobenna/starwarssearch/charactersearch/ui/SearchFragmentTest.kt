package com.ezike.tobenna.starwarssearch.charactersearch.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ezike.tobenna.starwarssearch.charactersearch.R
import com.ezike.tobenna.starwarssearch.core.di.ExecutorModule
import com.ezike.tobenna.starwarssearch.remote.di.RemoteModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@UninstallModules(
    ExecutorModule::class,
    RemoteModule::class
)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SearchFragmentTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule: ActivityScenarioRule<com.ezike.tobenna.starwarssearch.MainActivity> =
        ActivityScenarioRule(com.ezike.tobenna.starwarssearch.MainActivity::class.java)

    @Test
    fun should_show_initial_state() {
        onView(withId(R.id.search_bar)).check(matches(isDisplayed()))
        onView(withId(R.id.clear_history)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_history_prompt)).check(matches(isDisplayed())).check(
            matches(withText("Your recent searches will appear here"))
        )
    }

    @Test
    fun show_data_when_search_is_done() {
        onView(withId(R.id.search_bar)).perform(ViewActions.typeText(DummyData.query))
        onView(withId(R.id.search_result)).check(matches(isDisplayed()))
    }
}
