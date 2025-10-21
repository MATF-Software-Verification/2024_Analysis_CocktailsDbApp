package com.example.cocktailsdbapp.ui.authorization

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso Tests for LoginFragment
 * Tests login functionality and UI interactions
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLoginFragmentDisplaysCorrectly() {
        // Check if login button is displayed
        onView(withId(R.id.bt_login))
            .check(matches(isDisplayed()))
        
        // Check if email field is displayed
        onView(withId(R.id.et_email_input))
            .check(matches(isDisplayed()))
        
        // Check if password field is displayed
        onView(withId(R.id.et_password_input))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEmailFieldInteraction() {
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("test@example.com"))
            .check(matches(withText("test@example.com")))
    }

    @Test
    fun testPasswordFieldInteraction() {
        
        onView(withId(R.id.et_password_input))
            .perform(click())
            .perform(typeText("password123"))
            .check(matches(withText("password123")))
    }

    @Test
    fun testRegisterButtonClick() {
        
        onView(withId(R.id.bt_register))
            .perform(click())

        // Wait for navigation to finish
        Thread.sleep(1000)

        // Check if we are on registration screen
        onView(withId(R.id.tv_register))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyEmailValidation() {
        // Test validation with empty email
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Should stay on login screen
        onView(withId(R.id.et_email_input))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyPasswordValidation() {
        // Test validation with empty password
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("test@example.com"))
        
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Should stay on login screen
        onView(withId(R.id.et_password_input))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testInvalidEmailFormat() {
        onView(withId(R.id.et_password_input))
            .perform(clearText(), typeText("password123"))

        // Chars < 3
        onView(withId(R.id.et_email_input))
            .perform(clearText(), typeText("in"))

        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Should show validation error
        onView(withId(R.id.et_email_input))
            .check(matches(hasErrorText(containsString("Input must be at least 3 characters"))))
    }

    @Test
    fun testLoginWithInvalidCredentials() {
        // Test login with non existent user
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("invalid@example.com"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password"))
        
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Should stay on login screen
        Thread.sleep(1000)
        
        onView(withId(R.id.et_email_input))
            .check(matches(isDisplayed()))
    }
}
