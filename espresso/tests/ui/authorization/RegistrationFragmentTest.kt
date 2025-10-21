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
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso Tests for RegistrationFragment
 * Tests user registration functionality and UI interactions
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class RegistrationFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private fun navigateToRegistrationFragment() {
        // Navigate to registration fragment via login screen
        // First navigate to login, then to registration
        onView(withId(R.id.bt_register))
            .perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun testRegistrationFragmentDisplaysCorrectly() {
        // Test that registration fragment displays correctly
        navigateToRegistrationFragment()
        
        // Check if all UI elements are displayed
        onView(withId(R.id.tv_register))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.et_name_input))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.et_email_input))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.et_password_input))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.bt_register))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.bt_login))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.ll_spacer))
            .check(matches(isDisplayed()))
        
        onView(withId(R.id.tv_or))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testNameInputInteraction() {
        navigateToRegistrationFragment()
        
        // Test name field interaction
        onView(withId(R.id.et_name_input))
            .perform(click())
            .perform(typeText("John Doe"))
        
        // Verify text was entered
        onView(withId(R.id.et_name_input))
            .check(matches(withText("John Doe")))
    }

    @Test
    fun testEmailInputInteraction() {
        navigateToRegistrationFragment()
        
        // Test email field interaction
        onView(withId(R.id.et_email_input))
            .perform(click())
            .perform(typeText("john.doe@example.com"))
        
        // Verify text was entered
        onView(withId(R.id.et_email_input))
            .check(matches(withText("john.doe@example.com")))
    }

    @Test
    fun testPasswordInputInteraction() {
        navigateToRegistrationFragment()
        
        // Test password field interaction
        onView(withId(R.id.et_password_input))
            .perform(click())
            .perform(typeText("password123"))
        
        // Verify text was entered
        onView(withId(R.id.et_password_input))
            .check(matches(withText("password123")))
    }

    @Test
    fun testEmailValidation() {
        // Test email validation with short input
        navigateToRegistrationFragment()
        
        // Enter short email (less than minimum characters)
        onView(withId(R.id.et_email_input))
            .perform(click())
            .perform(typeText("ab"))
        
        // Check that error is displayed
        onView(withId(R.id.et_email_input))
            .check(matches(hasErrorText(containsString("Input must be at least 3 characters"))))
    }

    @Test
    fun testPasswordValidation() {
        // Test password validation with short input
        navigateToRegistrationFragment()
        
        // Enter short password (less than minimum characters)
        onView(withId(R.id.et_password_input))
            .perform(click())
            .perform(typeText("12"))
        
        // Check that error is displayed
        onView(withId(R.id.et_password_input))
            .check(matches(hasErrorText(containsString("Input must be at least 3 characters"))))
    }

    @Test
    fun testValidInputRemovesError() {
        // Test that valid input removes error messages
        navigateToRegistrationFragment()
        
        // First enter invalid input
        onView(withId(R.id.et_email_input))
            .perform(click())
            .perform(typeText("ab"))
        
        // Verify error is shown
        onView(withId(R.id.et_email_input))
            .check(matches(hasErrorText(containsString("Input must be at least 3 characters"))))
        
        // Now enter valid input
        onView(withId(R.id.et_email_input))
            .perform(clearText())
            .perform(typeText("valid@email.com"))
        
        // Verify error is removed
        onView(withId(R.id.et_email_input))
            .check(matches(not(hasErrorText(containsString("Input must be at least 3 characters")))))
    }

    @Test
    fun testRegisterButtonWithValidInput() {
        // Test register button with valid input
        navigateToRegistrationFragment()
        
        // Fill in all fields with valid data
        onView(withId(R.id.et_name_input))
            .perform(typeText("John Doe"))
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("john.doe@example.com"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        // Click register button
        onView(withId(R.id.bt_register))
            .perform(click())
        
        // Wait for registration process
        Thread.sleep(2000)
    }

    @Test
    fun testRegisterButtonWithInvalidInput() {
        // Test register button with invalid input
        navigateToRegistrationFragment()
        
        // Fill in fields with invalid data (too short)
        onView(withId(R.id.et_name_input))
            .perform(typeText("John"))
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("ab"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("12"))
        
        // Click register button
        onView(withId(R.id.bt_register))
            .perform(click())
        
        // Should stay on registration screen
        onView(withId(R.id.bt_register))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLoginButtonNavigation() {
        // Test navigation to login screen
        navigateToRegistrationFragment()
        
        // Click login button
        onView(withId(R.id.bt_login))
            .perform(click())
        
        // Wait for navigation
        Thread.sleep(1000)
        
        // Should be on login screen (verify by checking login-specific elements)
        onView(withId(R.id.bt_login))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testInputFieldFocus() {
        // Test input field focus behavior
        navigateToRegistrationFragment()
        
        // Test name field focus
        onView(withId(R.id.et_name_input))
            .perform(click())
            .check(matches(hasFocus()))
        
        // Test email field focus
        onView(withId(R.id.et_email_input))
            .perform(click())
            .check(matches(hasFocus()))
        
        // Test password field focus
        onView(withId(R.id.et_password_input))
            .perform(click())
            .check(matches(hasFocus()))
    }

    @Test
    fun testInputFieldClearing() {
        // Test clearing input fields
        navigateToRegistrationFragment()
        
        // Enter text in all fields
        onView(withId(R.id.et_name_input))
            .perform(typeText("John Doe"))
        
        onView(withId(R.id.et_email_input))
            .perform(typeText("john@example.com"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        // Clear all fields
        onView(withId(R.id.et_name_input))
            .perform(clearText())
        
        onView(withId(R.id.et_email_input))
            .perform(clearText())
        
        onView(withId(R.id.et_password_input))
            .perform(clearText())
        
        // Verify fields are empty
        onView(withId(R.id.et_name_input))
            .check(matches(withText("")))
        
        onView(withId(R.id.et_email_input))
            .check(matches(withText("")))
        
        onView(withId(R.id.et_password_input))
            .check(matches(withText("")))
    }

    @Test
    fun testRegistrationWithLongInputs() {
        // Test registration with long inputs
        navigateToRegistrationFragment()
        
        val longName = "A".repeat(30)
        val longEmail = "a".repeat(20) + "@example.com"
        val longPassword = "p".repeat(30)
        
        // Test long name
        onView(withId(R.id.et_name_input))
            .perform(typeText(longName))
        
        // Test long email
        onView(withId(R.id.et_email_input))
            .perform(typeText(longEmail))
        
        // Test long password
        onView(withId(R.id.et_password_input))
            .perform(typeText(longPassword))
        
        // Verify all fields accept long inputs
        onView(withId(R.id.et_name_input))
            .check(matches(withText(longName)))
        
        onView(withId(R.id.et_email_input))
            .check(matches(withText(longEmail)))
        
        onView(withId(R.id.et_password_input))
            .check(matches(withText(longPassword)))
    }

    @Test
    fun testRegistrationFormValidation() {
        // Test complete form validation
        navigateToRegistrationFragment()
        
        // Test with empty fields
        onView(withId(R.id.bt_register))
            .perform(click())
        
        // Should stay on registration screen
        onView(withId(R.id.bt_register))
            .check(matches(isDisplayed()))
        
        // Test with partial data
        onView(withId(R.id.et_name_input))
            .perform(typeText("John"))
        
        onView(withId(R.id.bt_register))
            .perform(click())
        
        // Should still stay on registration screen
        onView(withId(R.id.bt_register))
            .check(matches(isDisplayed()))
        
        // Test with valid data
        onView(withId(R.id.et_email_input))
            .perform(typeText("john@example.com"))
        
        onView(withId(R.id.et_password_input))
            .perform(typeText("password123"))
        
        onView(withId(R.id.bt_register))
            .perform(click())
        
        // Wait for registration process
        Thread.sleep(2000)
    }
}
