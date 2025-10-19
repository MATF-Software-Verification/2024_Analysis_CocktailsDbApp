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
    fun `saveUserData with different users with same email returns false`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("Jane Doe", "john@example.com", "password456")
        viewModel.saveUserData(user1) // First save

        // When
        val result = viewModel.saveUserData(user2) // Second save with same email

        // Then
        assertFalse(result)
        verify(exactly = 1) { mockEditor.apply() } // Only called once
    }

    @Test
    fun `saveUserData with different emails saves both users`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("Jane Doe", "jane@example.com", "password456")

        // When
        val result1 = viewModel.saveUserData(user1)
        val result2 = viewModel.saveUserData(user2)

        // Then
        assertTrue(result1)
        assertTrue(result2)
        verify(exactly = 2) { mockEditor.apply() }
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
    fun `getUserData with null values returns empty strings`() {
        // Given
        val email = "john@example.com"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_NAME}", "") 
        } returns null
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns null
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns null

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
    fun `isUserInfoValid with null email returns false`() {
        // Given
        val email = "john@example.com"
        val password = "password123"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns null
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns password

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
    fun `editUserName with empty name updates correctly`() {
        // Given
        val email = "john@example.com"
        val oldName = "John Doe"
        val newName = ""
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

    @Test
    fun `editPassword with empty password updates correctly`() {
        // Given
        val email = "john@example.com"
        val name = "John Doe"
        val oldPassword = "password123"
        val newPassword = ""
        
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

    @Test
    fun `editUserName with special characters updates correctly`() {
        // Given
        val email = "john@example.com"
        val oldName = "John Doe"
        val newName = "José María"
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
    fun `editPassword with special characters updates correctly`() {
        // Given
        val email = "john@example.com"
        val name = "John Doe"
        val oldPassword = "password123"
        val newPassword = "p@ssw0rd!#$%"
        
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

    @Test
    fun `saveUserData with empty strings saves correctly`() {
        // Given
        val user = User("", "", "")

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
    fun `saveUserData with long strings saves correctly`() {
        // Given
        val user = User("A".repeat(1000), "test@example.com", "B".repeat(1000))

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
    fun `isUserInfoValid with case sensitive password`() {
        // Given
        val email = "john@example.com"
        val storedPassword = "Password123"
        val inputPassword = "password123"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns email
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns storedPassword

        // When
        val result = viewModel.isUserInfoValid(email, inputPassword)

        // Then
        assertFalse(result) // Case sensitive
    }

    @Test
    fun `isUserInfoValid with exact case match returns true`() {
        // Given
        val email = "john@example.com"
        val storedPassword = "Password123"
        val inputPassword = "Password123"
        
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_EMAIL}", "") 
        } returns email
        every { 
            mockSharedPreferences.getString("${email}${Constants.SHARED_PREF_PASSWORD}", "") 
        } returns storedPassword

        // When
        val result = viewModel.isUserInfoValid(email, inputPassword)

        // Then
        assertTrue(result)
    }

    @Test
    fun `multiple saveUserData calls with different users`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("Jane Smith", "jane@example.com", "password456")
        val user3 = User("Bob Johnson", "bob@example.com", "password789")

        // When
        val result1 = viewModel.saveUserData(user1)
        val result2 = viewModel.saveUserData(user2)
        val result3 = viewModel.saveUserData(user3)

        // Then
        assertTrue(result1)
        assertTrue(result2)
        assertTrue(result3)
        verify(exactly = 3) { mockEditor.apply() }
    }

    @Test
    fun `editUserName and editPassword with same user`() {
        // Given
        val email = "john@example.com"
        val name = "John Doe"
        val oldPassword = "password123"
        val newName = "John Smith"
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
        viewModel.editUserName(email, newName)
        viewModel.editPassword(email, newPassword)

        // Then
        verify {
            mockEditor.putString("${email}${Constants.SHARED_PREF_NAME}", newName)
            mockEditor.putString("${email}${Constants.SHARED_PREF_PASSWORD}", newPassword)
        }
        verify(exactly = 2) { mockEditor.apply() }
    }
}
