package com.example.cocktailsdbapp.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Android Instrumentation Tests for Room Database
 * Tests database operations on actual Android environment
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class DatabaseTests {

    private lateinit var database: AppDatabase
    private lateinit var cocktailDao: CocktailDao

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        cocktailDao = database.cocktailDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testInsertAndGetCocktail() = runBlocking {
        // Given
        val cocktail = RoomCocktail("Mojito", "mojito.jpg", "11007")
        val userEmail = "test@example.com"

        // When
        cocktailDao.insertCocktail(cocktail)
        cocktailDao.insertFavorite(RoomFavorite(userEmail, cocktail.idDrink))
        val retrieved = cocktailDao.findFavoriteCocktail(userEmail, "11007")

        // Then
        assertNotNull(retrieved)
        assertEquals("Mojito", retrieved?.strDrink)
        assertEquals("mojito.jpg", retrieved?.strDrinkThumb)
        assertEquals("11007", retrieved?.idDrink)
    }

    @Test
    fun testDeleteCocktail() = runBlocking {
        // Given
        val cocktail = RoomCocktail("Margarita", "margarita.jpg", "11008")
        val userEmail = "test@example.com"
        cocktailDao.insertCocktail(cocktail)
        cocktailDao.insertFavorite(RoomFavorite(userEmail, cocktail.idDrink))

        // When
        cocktailDao.removeFavorite(userEmail, "11008")
        val retrieved = cocktailDao.findFavoriteCocktail(userEmail, "11008")

        // Then
        assertNull(retrieved)
    }

    @Test
    fun testGetAllFavorites() = runBlocking {
        // Given
        val userEmail = "test@example.com"
        val cocktails = listOf(
            RoomCocktail("Mojito", "mojito.jpg", "11007"),
            RoomCocktail("Margarita", "margarita.jpg", "11008"),
            RoomCocktail("Martini", "martini.jpg", "11009")
        )

        // When
        cocktails.forEach { cocktail ->
            cocktailDao.insertCocktail(cocktail)
            cocktailDao.insertFavorite(RoomFavorite(userEmail, cocktail.idDrink))
        }
        val favorites = cocktailDao.getFavoriteCocktails(userEmail)

        // Then
        assertNotNull(favorites)
        assertEquals(3, favorites?.size)
        assertEquals("Mojito", favorites?.get(0)?.strDrink)
        assertEquals("Margarita", favorites?.get(1)?.strDrink)
        assertEquals("Martini", favorites?.get(2)?.strDrink)
    }

    @Test
    fun testUserIsolation() = runBlocking {
        // Given
        val user1 = "user1@example.com"
        val user2 = "user2@example.com"
        val cocktail1 = RoomCocktail("Mojito", "mojito.jpg", "11007")
        val cocktail2 = RoomCocktail("Margarita", "margarita.jpg", "11008")

        // When
        cocktailDao.insertCocktail( cocktail1)
        cocktailDao.insertFavorite(RoomFavorite(user1, cocktail1.idDrink))
        cocktailDao.insertCocktail( cocktail2)
        cocktailDao.insertFavorite(RoomFavorite(user2, cocktail2.idDrink))

        val user1Favorites = cocktailDao.getFavoriteCocktails(user1)
        val user2Favorites = cocktailDao.getFavoriteCocktails(user2)

        // Then
        assertEquals(1, user1Favorites?.size)
        assertEquals(1, user2Favorites?.size)
        assertEquals("Mojito", user1Favorites?.get(0)?.strDrink)
        assertEquals("Margarita", user2Favorites?.get(0)?.strDrink)
    }
}
