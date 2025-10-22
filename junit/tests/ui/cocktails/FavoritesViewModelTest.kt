package com.example.cocktailsdbapp.ui.cocktails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.repository.CocktailsRepo
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for FavoritesViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FavoritesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var mockRepo: CocktailsRepo
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepo = mockk()
        viewModel = FavoritesViewModel(mockRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getFavorites with valid favorites returns converted cocktails`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val roomFavorites = listOf(
            RoomCocktail("Mojito", "mojito.jpg", "11007"),
            RoomCocktail("Margarita", "margarita.jpg", "11008"),
            RoomCocktail("Martini", "martini.jpg", "11009")
        )

        coEvery { mockRepo.getFavorites(userEmail) } returns roomFavorites

        // When
        viewModel.getFavorites(userEmail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getFavorites(userEmail) }
        assertNotNull(viewModel.favoritesData.value)
        assertEquals(3, viewModel.favoritesData.value?.size)
        
        // Verify all cocktails are marked as favorites
        viewModel.favoritesData.value?.forEach { cocktail ->
            assertTrue(cocktail.isFavorite)
        }
    }

    @Test
    fun `getFavorites with empty favorites returns empty list`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val emptyFavorites = emptyList<RoomCocktail>()

        coEvery { mockRepo.getFavorites(userEmail) } returns emptyFavorites

        // When
        viewModel.getFavorites(userEmail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getFavorites(userEmail) }
        assertNotNull(viewModel.favoritesData.value)
        assertTrue(viewModel.favoritesData.value!!.isEmpty())
    }

    @Test
    fun `getFavorites with null favorites returns null`() = runTest {
        // Given
        val userEmail = "test@example.com"

        coEvery { mockRepo.getFavorites(userEmail) } returns null

        // When
        viewModel.getFavorites(userEmail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getFavorites(userEmail) }
        assertNull(viewModel.favoritesData.value)
    }

    @Test
    fun `getFavorites with single favorite returns single cocktail`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val singleFavorite = listOf(RoomCocktail("Mojito", "mojito.jpg", "11007"))

        coEvery { mockRepo.getFavorites(userEmail) } returns singleFavorite

        // When
        viewModel.getFavorites(userEmail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getFavorites(userEmail) }
        assertNotNull(viewModel.favoritesData.value)
        assertEquals(1, viewModel.favoritesData.value?.size)
        assertEquals("Mojito", viewModel.favoritesData.value?.first()?.strDrink)
        assertTrue(viewModel.favoritesData.value?.first()?.isFavorite == true)
    }

    @Test
    fun `getFavorites handles exception gracefully`() = runTest {
        // Given
        val userEmail = "test@example.com"

        coEvery { mockRepo.getFavorites(userEmail) } throws Exception("Database error")

        // When
        viewModel.getFavorites(userEmail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `favoriteCocktail adds cocktail when not already favorite`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val cocktail = Cocktail("Mojito", "mojito.jpg", "11007", false)

        coEvery { mockRepo.findFavoriteCocktail(userEmail, cocktail.idDrink) } returns null
        coEvery { mockRepo.insertCocktail(userEmail, any()) } just Runs

        // When
        viewModel.favoriteCocktail(userEmail, cocktail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.findFavoriteCocktail(userEmail, cocktail.idDrink) }
        coVerify {
            mockRepo.insertCocktail(
                userEmail,
                match {
                    it.strDrink == "Mojito" &&
                    it.strDrinkThumb == "mojito.jpg" &&
                    it.idDrink == "11007"
                }
            )
        }
        coVerify(exactly = 0) { mockRepo.removeFavorite(any(), any()) }
    }

    @Test
    fun `favoriteCocktail removes cocktail when already favorite`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val cocktail = Cocktail("Mojito", "mojito.jpg", "11007", true)
        val existingFavorite = RoomCocktail("Mojito", "mojito.jpg", "11007")

        coEvery { mockRepo.findFavoriteCocktail(userEmail, cocktail.idDrink) } returns existingFavorite
        coEvery { mockRepo.removeFavorite(userEmail, cocktail.idDrink) } just Runs

        // When
        viewModel.favoriteCocktail(userEmail, cocktail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.findFavoriteCocktail(userEmail, cocktail.idDrink) }
        coVerify { mockRepo.removeFavorite(userEmail, cocktail.idDrink) }
        coVerify(exactly = 0) { mockRepo.insertCocktail(any(), any()) }
    }

    @Test
    fun `favoriteCocktail with different users maintains separate favorites`() = runTest {
        // Given
        val user1 = "user1@example.com"
        val user2 = "user2@example.com"
        val cocktail = Cocktail("Mojito", "mojito.jpg", "11007", false)

        coEvery { mockRepo.findFavoriteCocktail(user1, cocktail.idDrink) } returns null
        coEvery { mockRepo.findFavoriteCocktail(user2, cocktail.idDrink) } returns null
        coEvery { mockRepo.insertCocktail(any(), any()) } just Runs

        // When
        viewModel.favoriteCocktail(user1, cocktail)
        viewModel.favoriteCocktail(user2, cocktail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Both users should be able to favorite
        coVerify { mockRepo.insertCocktail(user1, any()) }
        coVerify { mockRepo.insertCocktail(user2, any()) }
    }

    @Test
    fun `favoritesData is initialized`() {
        // Then
        assertNotNull(viewModel.favoritesData)
    }

    @Test
    fun `getFavorites converts RoomCocktail to Cocktail with correct properties`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val roomFavorites = listOf(
            RoomCocktail("Test Cocktail", "test.jpg", "99999")
        )

        coEvery { mockRepo.getFavorites(userEmail) } returns roomFavorites

        // When
        viewModel.getFavorites(userEmail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val convertedCocktail = viewModel.favoritesData.value?.first()
        assertNotNull(convertedCocktail)
        assertEquals("Test Cocktail", convertedCocktail?.strDrink)
        assertEquals("test.jpg", convertedCocktail?.strDrinkThumb)
        assertEquals("99999", convertedCocktail?.idDrink)
        assertTrue(convertedCocktail?.isFavorite == true)
    }

    @Test
    fun `favoriteCocktail handles existing favorite correctly`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val cocktail = Cocktail("Existing Cocktail", "existing.jpg", "22222", true)
        val existingFavorite = RoomCocktail("Existing Cocktail", "existing.jpg", "22222")

        coEvery { mockRepo.findFavoriteCocktail(userEmail, cocktail.idDrink) } returns existingFavorite
        coEvery { mockRepo.removeFavorite(userEmail, cocktail.idDrink) } just Runs

        // When
        viewModel.favoriteCocktail(userEmail, cocktail)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.removeFavorite(userEmail, cocktail.idDrink) }
        coVerify(exactly = 0) { mockRepo.insertCocktail(any(), any()) }
    }
}

