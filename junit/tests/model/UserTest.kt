package com.example.cocktailsdbapp.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for User data class
 */
class UserTest {

    @Test
    fun `user creation with valid data`() {
        // Given
        val name = "John Doe"
        val email = "john.doe@example.com"
        val password = "password123"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals(name, user.name)
        assertEquals(email, user.email)
        assertEquals(password, user.password)
    }

    @Test
    fun `user creation with empty strings`() {
        // Given
        val name = ""
        val email = ""
        val password = ""

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("", user.name)
        assertEquals("", user.email)
        assertEquals("", user.password)
    }

    @Test
    fun `user creation with special characters`() {
        // Given
        val name = "José María"
        val email = "jose.maria@café.com"
        val password = "p@ssw0rd!#$%"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("José María", user.name)
        assertEquals("jose.maria@café.com", user.email)
        assertEquals("p@ssw0rd!#$%", user.password)
    }

    @Test
    fun `user creation with long strings`() {
        // Given
        val name = "A".repeat(1000)
        val email = "test@example.com"
        val password = "B".repeat(1000)

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("A".repeat(1000), user.name)
        assertEquals("test@example.com", user.email)
        assertEquals("B".repeat(1000), user.password)
    }

    @Test
    fun `user equality with same data`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("John Doe", "john@example.com", "password123")

        // When & Then
        assertEquals(user1, user2)
        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun `user equality with different data`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("Jane Doe", "jane@example.com", "password456")

        // When & Then
        assertNotEquals(user1, user2)
        assertNotEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun `user equality with different name`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("Jane Doe", "john@example.com", "password123")

        // When & Then
        assertNotEquals(user1, user2)
    }

    @Test
    fun `user equality with different email`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("John Doe", "jane@example.com", "password123")

        // When & Then
        assertNotEquals(user1, user2)
    }

    @Test
    fun `user equality with different password`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("John Doe", "john@example.com", "password456")

        // When & Then
        assertNotEquals(user1, user2)
    }

    @Test
    fun `user copy with new name`() {
        // Given
        val originalUser = User("John Doe", "john@example.com", "password123")

        // When
        val copiedUser = originalUser.copy(name = "Jane Doe")

        // Then
        assertEquals("Jane Doe", copiedUser.name)
        assertEquals("john@example.com", copiedUser.email)
        assertEquals("password123", copiedUser.password)
        assertNotEquals(originalUser, copiedUser)
    }

    @Test
    fun `user copy with new email`() {
        // Given
        val originalUser = User("John Doe", "john@example.com", "password123")

        // When
        val copiedUser = originalUser.copy(email = "jane@example.com")

        // Then
        assertEquals("John Doe", copiedUser.name)
        assertEquals("jane@example.com", copiedUser.email)
        assertEquals("password123", copiedUser.password)
        assertNotEquals(originalUser, copiedUser)
    }

    @Test
    fun `user copy with new password`() {
        // Given
        val originalUser = User("John Doe", "john@example.com", "password123")

        // When
        val copiedUser = originalUser.copy(password = "newpassword456")

        // Then
        assertEquals("John Doe", copiedUser.name)
        assertEquals("john@example.com", copiedUser.email)
        assertEquals("newpassword456", copiedUser.password)
        assertNotEquals(originalUser, copiedUser)
    }

    @Test
    fun `user copy with all new values`() {
        // Given
        val originalUser = User("John Doe", "john@example.com", "password123")

        // When
        val copiedUser = originalUser.copy(
            name = "Jane Smith",
            email = "jane.smith@example.com",
            password = "newpassword789"
        )

        // Then
        assertEquals("Jane Smith", copiedUser.name)
        assertEquals("jane.smith@example.com", copiedUser.email)
        assertEquals("newpassword789", copiedUser.password)
        assertNotEquals(originalUser, copiedUser)
    }

    @Test
    fun `user copy with no changes`() {
        // Given
        val originalUser = User("John Doe", "john@example.com", "password123")

        // When
        val copiedUser = originalUser.copy()

        // Then
        assertEquals(originalUser, copiedUser)
        assertEquals(originalUser.hashCode(), copiedUser.hashCode())
    }

    @Test
    fun `user toString contains all fields`() {
        // Given
        val user = User("John Doe", "john@example.com", "password123")

        // When
        val userString = user.toString()

        // Then
        assertTrue(userString.contains("John Doe"))
        assertTrue(userString.contains("john@example.com"))
        assertTrue(userString.contains("password123"))
    }

    @Test
    fun `user with email containing numbers`() {
        // Given
        val name = "User123"
        val email = "user123@example123.com"
        val password = "pass123"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("User123", user.name)
        assertEquals("user123@example123.com", user.email)
        assertEquals("pass123", user.password)
    }

    @Test
    fun `user with email containing underscores and dots`() {
        // Given
        val name = "Test User"
        val email = "test.user_name@sub.domain.com"
        val password = "test_password"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("Test User", user.name)
        assertEquals("test.user_name@sub.domain.com", user.email)
        assertEquals("test_password", user.password)
    }

    @Test
    fun `user with unicode characters`() {
        // Given
        val name = "测试用户"
        val email = "test@测试.com"
        val password = "пароль123"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("测试用户", user.name)
        assertEquals("test@测试.com", user.email)
        assertEquals("пароль123", user.password)
    }

    @Test
    fun `user with whitespace in name`() {
        // Given
        val name = "  John Doe  "
        val email = "john@example.com"
        val password = "password123"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("  John Doe  ", user.name)
        assertEquals("john@example.com", user.email)
        assertEquals("password123", user.password)
    }

    @Test
    fun `user with whitespace in email`() {
        // Given
        val name = "John Doe"
        val email = "  john@example.com  "
        val password = "password123"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("John Doe", user.name)
        assertEquals("  john@example.com  ", user.email)
        assertEquals("password123", user.password)
    }

    @Test
    fun `user with whitespace in password`() {
        // Given
        val name = "John Doe"
        val email = "john@example.com"
        val password = "  password123  "

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("John Doe", user.name)
        assertEquals("john@example.com", user.email)
        assertEquals("  password123  ", user.password)
    }

    @Test
    fun `user with newline characters`() {
        // Given
        val name = "John\nDoe"
        val email = "john@example.com"
        val password = "pass\nword"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("John\nDoe", user.name)
        assertEquals("john@example.com", user.email)
        assertEquals("pass\nword", user.password)
    }

    @Test
    fun `user with tab characters`() {
        // Given
        val name = "John\tDoe"
        val email = "john@example.com"
        val password = "pass\tword"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("John\tDoe", user.name)
        assertEquals("john@example.com", user.email)
        assertEquals("pass\tword", user.password)
    }

    @Test
    fun `user with case sensitive data`() {
        // Given
        val name = "John Doe"
        val email = "John.Doe@Example.COM"
        val password = "Password123"

        // When
        val user = User(name, email, password)

        // Then
        assertEquals("John Doe", user.name)
        assertEquals("John.Doe@Example.COM", user.email)
        assertEquals("Password123", user.password)
    }

    @Test
    fun `user equality case sensitivity`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("john doe", "JOHN@EXAMPLE.COM", "PASSWORD123")

        // When & Then
        assertNotEquals(user1, user2)
    }
}
