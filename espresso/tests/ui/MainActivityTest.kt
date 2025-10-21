package com.example.cocktailsdbapp.ui

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.cocktailsdbapp.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso Tests for MainActivity
 * Tests basic activity functionality
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testMainActivityLaunches() {
        // Test that MainActivity launches successfully
        activityRule.scenario.onActivity { activity ->
            assert(activity != null)
            assert(activity.isFinishing.not())
        }
    }

    @Test
    fun testActivityNotFinishing() {
        // Test that activity is not finishing after launch
        activityRule.scenario.onActivity { activity ->
            assert(!activity.isFinishing)
        }
    }

    @Test
    fun testActivityHasWindowFocus() {
        // Test that activity has window focus
        activityRule.scenario.onActivity { activity ->
            assert(activity.hasWindowFocus())
        }
    }

    @Test
    fun testActivityIsNotDestroyed() {
        // Test that activity is not destroyed
        activityRule.scenario.onActivity { activity ->
            assert(!activity.isDestroyed)
        }
    }
}
