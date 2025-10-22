package com.example.cocktailsdbapp.database

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for RoomCocktail data class
 */
class RoomCocktailTest {

    @Test
    fun `test RoomCocktail data class basic functionality`() {
        // Given
        val roomCocktail = RoomCocktail(
            strDrink = "Mojito",
            strDrinkThumb = "mojito.jpg",
            idDrink = "11007"
        )
        
        // Then
        assertEquals("Mojito", roomCocktail.strDrink)
        assertEquals("mojito.jpg", roomCocktail.strDrinkThumb)
        assertEquals("11007", roomCocktail.idDrink)
    }

    @Test
    fun `test RoomCocktail data class equality`() {
        // Given
        val roomCocktail1 = RoomCocktail("Mojito", "mojito.jpg", "11007")
        val roomCocktail2 = RoomCocktail("Mojito", "mojito.jpg", "11007")
        val roomCocktail3 = RoomCocktail("Margarita", "margarita.jpg", "11008")
        
        // Then
        assertEquals(roomCocktail1, roomCocktail2)
        assertNotEquals(roomCocktail1, roomCocktail3)
    }

    @Test
    fun `test RoomCocktail toString method`() {
        // Given
        val roomCocktail = RoomCocktail("Mojito", "mojito.jpg", "11007")
        
        // When
        val result = roomCocktail.toString()
        
        // Then
        assertTrue(result.contains("Mojito"))
        assertTrue(result.contains("mojito.jpg"))
        assertTrue(result.contains("11007"))
    }

    @Test
    fun `test RoomCocktail with empty strings`() {
        // Given
        val roomCocktail = RoomCocktail("", "", "")
        
        // Then
        assertEquals("", roomCocktail.strDrink)
        assertEquals("", roomCocktail.strDrinkThumb)
        assertEquals("", roomCocktail.idDrink)
    }

    @Test
    fun `test RoomCocktail hashCode consistency`() {
        // Given
        val roomCocktail1 = RoomCocktail("Mojito", "mojito.jpg", "11007")
        val roomCocktail2 = RoomCocktail("Mojito", "mojito.jpg", "11007")
        
        // Then
        assertEquals(roomCocktail1.hashCode(), roomCocktail2.hashCode())
    }
}

