package com.example.cocktailsdbapp.ui.cocktails

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
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Comprehensive Espresso Tests for CocktailsFragment
 * Tests: Registration -> Login -> Cocktails Fragment functionality
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class CocktailsFragmentTest {

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
    fun testCocktailsFragmentDisplaysCorrectly() {
        // Verify we're on the cocktails fragment
        onView(withId(R.id.rv_cocktails))
            .check(matches(isDisplayed()))
        
        // Verify filter label is displayed
        onView(withId(R.id.ll_label))
            .check(matches(isDisplayed()))
        
        // Verify toolbar elements
        onView(withId(R.id.action_search))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.action_filter))
            .check(matches(isDisplayed()))
        
        // Verify bottom navigation
        onView(withId(R.id.bottomNavigationMenu))
            .check(matches(isDisplayed()))
        
        // Verify navigation tabs
        onView(withId(R.id.navigation_cocktails))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.navigation_favorites))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.navigation_profile))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCocktailsRecyclerViewHasItems() {
        // Wait for data to load
        Thread.sleep(1000)

        // Verify RecyclerView is displayed and has items
        onView(withId(R.id.rv_cocktails))
            .check(matches(isDisplayed()))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testCocktailsRecyclerViewIsScrollable() {
        // Wait for data to load
        Thread.sleep(1000)

        // Verify RecyclerView is scrollable
        onView(withId(R.id.rv_cocktails))
            .check(matches(isDisplayed()))
            .perform(swipeUp())
            .perform(swipeDown())
    }

    @Test
    fun testCocktailsItemClick() {
        // Wait for data to load
        Thread.sleep(1000)

        onView(withId(R.id.rv_cocktails))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, // position
                    MyViewAction.clickChildViewWithId(R.id.iv_drink)
                )
            )

    }

    @Test
    fun testCocktailsFavoriteButtonClick() {
        // Wait for data to load
        Thread.sleep(1000)

        onView(withId(R.id.rv_cocktails))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0, // position
                    MyViewAction.clickChildViewWithId(R.id.iv_favorite)
                )
            )

        // Wait for favorite action to complete
        Thread.sleep(1000)
    }

    @Test
    fun testCocktailsSearchAction() {
        // Click on search action in toolbar
        onView(withId(R.id.action_search))
            .perform(click())
        
        // Wait for search to open
        Thread.sleep(1000)

        // This should expand search input field
        onView(withId(R.id.action_search_input))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testCocktailsFilterAction() {
        // Click on filter action in toolbar
        onView(withId(R.id.action_filter))
            .perform(click())
        
        // Wait for filter to open
        Thread.sleep(1000)


        // This should open a filter list
        onView(withId(R.id.rv_filter))
                .check(matches(isDisplayed()))

    }
}
