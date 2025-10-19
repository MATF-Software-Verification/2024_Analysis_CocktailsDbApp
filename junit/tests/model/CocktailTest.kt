package com.example.cocktailsdbapp.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for Cocktail data class
 */
class CocktailTest {

    @Test
    fun `test Cocktail data class basic functionality`() {
        // Given
        val cocktail = Cocktail(
            strDrink = "Mojito",
            strDrinkThumb = "mojito.jpg",
            idDrink = "11007",
            isFavorite = true
        )
        
        // Then
        assertEquals("Mojito", cocktail.strDrink)
        assertEquals("mojito.jpg", cocktail.strDrinkThumb)
        assertEquals("11007", cocktail.idDrink)
        assertTrue(cocktail.isFavorite)
    }

    @Test
    fun `test Cocktail data class with default isFavorite`() {
        // Given
        val cocktail = Cocktail(
            strDrink = "Margarita",
            strDrinkThumb = "margarita.jpg",
            idDrink = "11008"
        )
        
        // Then
        assertEquals("Margarita", cocktail.strDrink)
        assertEquals("margarita.jpg", cocktail.strDrinkThumb)
        assertEquals("11008", cocktail.idDrink)
        assertFalse(cocktail.isFavorite)
    }

    @Test
    fun `test Cocktail data class equality`() {
        // Given
        val cocktail1 = Cocktail("Mojito", "mojito.jpg", "11007", true)
        val cocktail2 = Cocktail("Mojito", "mojito.jpg", "11007", true)
        val cocktail3 = Cocktail("Margarita", "margarita.jpg", "11008", false)
        
        // Then
        assertEquals(cocktail1, cocktail2)
        assertNotEquals(cocktail1, cocktail3)
    }

    @Test
    fun `test Cocktail data class copy`() {
        // Given
        val original = Cocktail("Mojito", "mojito.jpg", "11007", false)
        
        // When
        val copied = original.copy(isFavorite = true)
        
        // Then
        assertEquals("Mojito", copied.strDrink)
        assertEquals("mojito.jpg", copied.strDrinkThumb)
        assertEquals("11007", copied.idDrink)
        assertTrue(copied.isFavorite)
        assertFalse(original.isFavorite)
    }

    @Test
    fun `test Cocktail toString method`() {
        // Given
        val cocktail = Cocktail("Mojito", "mojito.jpg", "11007", true)
        
        // When
        val result = cocktail.toString()
        
        // Then
        assertTrue(result.contains("Mojito"))
        assertTrue(result.contains("mojito.jpg"))
        assertTrue(result.contains("11007"))
        assertTrue(result.contains("true"))
    }

    @Test
    fun `test Cocktail with empty strings`() {
        // Given
        val cocktail = Cocktail("", "", "", false)
        
        // Then
        assertEquals("", cocktail.strDrink)
        assertEquals("", cocktail.strDrinkThumb)
        assertEquals("", cocktail.idDrink)
        assertFalse(cocktail.isFavorite)
    }

    @Test
    fun `test Cocktail list operations`() {
        // Given
        val cocktails = listOf(
            Cocktail("Mojito", "mojito.jpg", "11007", true),
            Cocktail("Margarita", "margarita.jpg", "11008", false),
            Cocktail("Martini", "martini.jpg", "11009", false)
        )
        
        // When
        val favorites = cocktails.filter { it.isFavorite }
        val nonFavorites = cocktails.filter { !it.isFavorite }
        
        // Then
        assertEquals(1, favorites.size)
        assertEquals(2, nonFavorites.size)
        assertEquals("Mojito", favorites[0].strDrink)
    }
}

