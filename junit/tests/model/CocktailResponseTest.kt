package com.example.cocktailsdbapp.model

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for CocktailResponse data class
 */
class CocktailResponseTest {

    @Test
    fun `test CocktailResponse data class with drinks`() {
        // Given
        val cocktails = listOf(
            Cocktail("Mojito", "mojito.jpg", "11007", true),
            Cocktail("Margarita", "margarita.jpg", "11008", false)
        )
        val response = CocktailResponse(cocktails)
        
        // Then
        assertNotNull(response.drinks)
        assertEquals(2, response.drinks?.size)
        assertEquals("Mojito", response.drinks?.get(0)?.strDrink)
        assertEquals("Margarita", response.drinks?.get(1)?.strDrink)
    }

    @Test
    fun `test CocktailResponse data class with null drinks`() {
        // Given
        val response = CocktailResponse(null)
        
        // Then
        assertNull(response.drinks)
    }

    @Test
    fun `test CocktailResponse with empty list`() {
        // Given
        val response = CocktailResponse(emptyList())
        
        // Then
        assertNotNull(response.drinks)
        assertEquals(0, response.drinks?.size)
        assertTrue(response.drinks!!.isEmpty())
    }

    @Test
    fun `test CocktailResponse equality`() {
        // Given
        val cocktails1 = listOf(Cocktail("Mojito", "mojito.jpg", "11007", true))
        val cocktails2 = listOf(Cocktail("Mojito", "mojito.jpg", "11007", true))
        val response1 = CocktailResponse(cocktails1)
        val response2 = CocktailResponse(cocktails2)
        
        // Then
        assertEquals(response1, response2)
    }

    @Test
    fun `test CocktailResponse with single cocktail`() {
        // Given
        val singleCocktail = listOf(Cocktail("Mojito", "mojito.jpg", "11007", false))
        val response = CocktailResponse(singleCocktail)
        
        // Then
        assertNotNull(response.drinks)
        assertEquals(1, response.drinks?.size)
        assertEquals("Mojito", response.drinks?.first()?.strDrink)
    }
}

