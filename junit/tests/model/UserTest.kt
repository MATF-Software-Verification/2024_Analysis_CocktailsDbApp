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
        assertEquals(name, user.name)
        assertEquals(email, user.email)
        assertEquals(password, user.password)
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
    fun `user equality case sensitivity`() {
        // Given
        val user1 = User("John Doe", "john@example.com", "password123")
        val user2 = User("john doe", "JOHN@EXAMPLE.COM", "PASSWORD123")

        // When & Then
        assertNotEquals(user1, user2)
    }
}
