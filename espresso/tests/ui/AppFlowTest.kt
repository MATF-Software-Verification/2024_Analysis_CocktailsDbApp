package com.example.cocktailsdbapp.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.ui.cocktails.CocktailsFragmentTest
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Comprehensive Espresso Tests for App Flow
 * Register -> Display Cocktails -> Select Category ->
 * Select Filter -> Favorite Item -> Display Favorites ->
 * Unfavorite Item -> Navigate to Profile -> Display Empty Favorites
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class AppFlowTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val testEmail = "cocktailstest@example.com"
    private val testName = "Cocktails Test User"
    private val testPassword = "password123"

    @Before
    fun setUp() {
        // Register and login for each test
        registerAndLoginUser()
    }

    private fun registerAndLoginUser() {
        // Step 1: Navigate to Registration
        try {
            onView(withId(R.id.bt_register))
                .perform(click())
            Thread.sleep(1000)
        } catch (e: Exception) {
            // Might already be on registration screen
        }

        // Step 2: Register a new user
        onView(withId(R.id.et_name_input))
            .perform(clearText(), typeText(testName))

        onView(withId(R.id.et_email_input))
            .perform(clearText(), typeText(testEmail))

        onView(withId(R.id.et_password_input))
            .perform(clearText(), typeText(testPassword))

        // Click register button
        onView(withId(R.id.bt_register))
            .perform(click())

        // Wait for registration to complete
        Thread.sleep(1000)

        // Step 3: Handle the success dialog that appears after registration
        try {
            // Check if success dialog is displayed
            onView(withText("Registration Successful!"))
                .check(matches(isDisplayed()))

            // Click OK button to dismiss the dialog
            onView(withText("OK"))
                .perform(click())

            // Wait for navigation to cocktails fragment
            Thread.sleep(1000)

        } catch (e: Exception) {
            // If no success dialog appears, registration might have failed
            // Try to login manually
            onView(withId(R.id.bt_login))
                .perform(click())
            Thread.sleep(1000)

            // Fill login form
            onView(withId(R.id.et_email_input))
                .perform(clearText(), typeText(testEmail))

            onView(withId(R.id.et_password_input))
                .perform(clearText(), typeText(testPassword))

            // Click login button
            onView(withId(R.id.bt_login))
                .perform(click())

            // Wait for login to complete
            Thread.sleep(1000)
        }
    }

    object MyViewAction {
        fun clickChildViewWithId(id: Int) = object : ViewAction {
            override fun getConstraints(): Matcher<View> = isAssignableFrom(View::class.java)
            override fun getDescription(): String = "Click on a child view with specified id."
            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id)
                v.performClick()
            }
        }
    }

    @Test
    fun testAppFlow() {

        // Click on category
        onView(withId(R.id.action_filter))
            .perform(click())

        // Wait to show category list
        Thread.sleep(1000)

        // Verify categories are listed
        onView(withId(R.id.rv_filter))
            .check(matches(isDisplayed()))

        // Click on second category
        onView(withId(R.id.rv_filter))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1, // position
                    MyViewAction.clickChildViewWithId(R.id.cl_filter_view)
                )
            )

        // Wait to show filter list
        Thread.sleep(1000)

        // Verify filters are listed
        onView(withId(R.id.rv_filter))
            .check(matches(isDisplayed()))

        // Click on first filter
        onView(withId(R.id.rv_filter))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, // position
                    MyViewAction.clickChildViewWithId(R.id.cl_filter_view)
                )
            )

        // Wait to navigate to cocktails screen
        Thread.sleep(1000)

        // Verify cocktails are loaded
        onView(withId(R.id.rv_cocktails))
            .check(matches(isDisplayed()))

        // Favorite first item, so the favorites screen is not empty
        onView(withId(R.id.rv_cocktails))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, // position
                    MyViewAction.clickChildViewWithId(R.id.iv_favorite)
                )
            )

        Thread.sleep(1000)

        // Click on favorites tab
        onView(withId(R.id.navigation_favorites))
            .perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.rv_cocktails))
            .check(matches(isDisplayed()))

        // Unfavorite the item, so the favorites screen is empty
        onView(withId(R.id.rv_cocktails))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, // position
                    MyViewAction.clickChildViewWithId(R.id.iv_favorite)
                )
            )

        Thread.sleep(1000)

        // Click on profile tab
        onView(withId(R.id.navigation_profile))
            .perform(click())

        // Verify profile component is displayed
        onView(withId(R.id.til_name_input))
            .check(matches(isDisplayed()))

        // Click on fav tab
        onView(withId(R.id.navigation_favorites))
            .perform(click())

        Thread.sleep(1000)

        onView(withId(R.id.rv_cocktails))
            .check(matches(not(isDisplayed())))
    }
}
