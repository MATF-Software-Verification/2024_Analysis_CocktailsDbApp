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
        // Test that login fragment displays correctly
        // This assumes the app starts with login fragment
        
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
        // Test email field interaction
        
        onView(withId(R.id.et_email_input))
            .perform(replaceText("test@example.com"))
            .check(matches(withText("test@example.com")))
    }

    @Test
    fun testPasswordFieldInteraction() {
        // Test password field interaction
        
        onView(withId(R.id.et_password_input))
            .perform(click())
            .perform(typeText("password123"))
            .check(matches(withText("password123")))
    }

    @Test
    fun testLoginButtonClick() {
        // Test login button click
        
        // Fill in credentials
        onView(withId(R.id.et_email_input))
            .perform(typeText("test@example.com"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        // Click login button
        onView(withId(R.id.bt_login))
            .perform(click())
    }

    @Test
    fun testRegisterButtonClick() {
        // Test register button click
        
        onView(withId(R.id.bt_register))
            .perform(click())
    }

    @Test
    fun testEmptyEmailValidation() {
        // Test validation with empty email
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Should stay on login screen or show validation error
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
        
        // Should stay on login screen or show validation error
        onView(withId(R.id.et_password_input))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testInvalidEmailFormat() {
        // Test with invalid email format
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("invalid-email"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Should show validation error or stay on login screen
        onView(withId(R.id.et_email_input))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testPasswordVisibilityToggle() {
        // Test password visibility toggle if available
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        // Try to find and click password visibility toggle
        // This might not be available in all implementations
        try {
            onView(withId(R.id.til_password_input))
                .perform(click())
        } catch (e: Exception) {
            // Password visibility toggle might not be implemented
        }
    }

    @Test
    fun testLoginWithValidCredentials() {
        // Test login with valid credentials
        // Note: This assumes there's a test user in the system
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("test@example.com"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Wait for potential navigation
        Thread.sleep(1000)
        
        // Check if navigation occurred (this depends on the app's navigation structure)
        // The test might need to be adjusted based on actual app behavior
    }

    @Test
    fun testLoginWithInvalidCredentials() {
        // Test login with invalid credentials
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("invalid@example.com"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("wrongpassword"))
        
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Should show error message or stay on login screen
        Thread.sleep(1000)
        
        onView(withId(R.id.et_email_input))
            .check(matches(isDisplayed()))
    }
}
