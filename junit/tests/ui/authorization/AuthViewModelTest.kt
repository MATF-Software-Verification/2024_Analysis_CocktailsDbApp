package com.example.cocktailsdbapp.ui.authorization

import android.content.SharedPreferences
import com.example.cocktailsdbapp.model.User
import com.example.cocktailsdbapp.utils.Constants
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for AuthViewModel
 */
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private lateinit var mockSharedPreferences: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor

    @Before
    fun setup() {
        mockSharedPreferences = mockk()
        mockEditor = mockk()
        viewModel = AuthViewModel(mockSharedPreferences)
        
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockEditor.putString(any(), any()) } returns mockEditor
        every { mockEditor.apply() } just Runs
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `saveUserData with new user returns true and saves data`() {
        // Given
        val user = User("John Doe", "john@example.com", "password123")

        // When
        val result = viewModel.saveUserData(user)

        // Then
        assertTrue(result)
        verify {
            mockEditor.putString("${user.email}${Constants.SHARED_PREF_NAME}", user.name)
            mockEditor.putString("${user.email}${Constants.SHARED_PREF_EMAIL}", user.email)
            mockEditor.putString("${user.email}${Constants.SHARED_PREF_PASSWORD}", user.password)
            mockEditor.apply()
        }
    }

    @Test
    fun `saveUserData with existing user returns false`() {
        // Given
        val user = User("John Doe", "john@example.com", "password123")
        viewModel.saveUserData(user) // First save

        // When
        val result = viewModel.saveUserData(user) // Second save

        // Then
        assertFalse(result)
        verify(exactly = 1) { mockEditor.apply() } // Only called once
    }

    @Test
    fun `getUserData returns correct user data`() {
        // Given
        val email = "john@example.com"
        val name = "John Doe"
        val password = "password123"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_NAME}", "") 
        } returns name
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns email
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns password

        // When
        val result = viewModel.getUserData(email)

        // Then
        assertEquals(name, result.name)
        assertEquals(email, result.email)
        assertEquals(password, result.password)
    }

    @Test
    fun `getUserData with non-existent user returns empty strings`() {
        // Given
        val email = "nonexistent@example.com"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_NAME}", "") 
        } returns ""
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns ""
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns ""

        // When
        val result = viewModel.getUserData(email)

        // Then
        assertEquals("", result.name)
        assertEquals("", result.email)
        assertEquals("", result.password)
    }

    @Test
    fun `isUserInfoValid with correct credentials returns true`() {
        // Given
        val email = "john@example.com"
        val password = "password123"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns email
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns password

        // When
        val result = viewModel.isUserInfoValid(email, password)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isUserInfoValid with incorrect password returns false`() {
        // Given
        val email = "john@example.com"
        val correctPassword = "password123"
        val wrongPassword = "wrongpassword"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns email
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns correctPassword

        // When
        val result = viewModel.isUserInfoValid(email, wrongPassword)

        // Then
        assertFalse(result)
    }

    @Test
    fun `isUserInfoValid with non-existent email returns false`() {
        // Given
        val email = "nonexistent@example.com"
        val password = "password123"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns ""
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns ""

        // When
        val result = viewModel.isUserInfoValid(email, password)

        // Then
        assertFalse(result)
    }

    @Test
    fun `editUserName updates name correctly`() {
        // Given
        val email = "john@example.com"
        val oldName = "John Doe"
        val newName = "John Smith"
        val password = "password123"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_NAME}", "") 
        } returns oldName
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns email
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns password

        // When
        viewModel.editUserName(email, newName)

        // Then
        verify {
            mockEditor.putString("${email}${Constants.SHARED_PREF_NAME}", newName)
            mockEditor.apply()
        }
    }
    @Test
    fun `editPassword updates password correctly`() {
        // Given
        val email = "john@example.com"
        val name = "John Doe"
        val oldPassword = "password123"
        val newPassword = "newpassword456"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_NAME}", "") 
        } returns name
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns email
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns oldPassword

        // When
        viewModel.editPassword(email, newPassword)

        // Then
        verify {
            mockEditor.putString("${email}${Constants.SHARED_PREF_PASSWORD}", newPassword)
            mockEditor.apply()
        }
    }
}
