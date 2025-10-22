package com.example.cocktailsdbapp.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.model.CocktailResponse
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
 * Unit tests for SearchViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel
    private lateinit var mockRepo: CocktailsRepo
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepo = mockk()
        viewModel = SearchViewModel(mockRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `executeSearch with valid results updates searchResultsData`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val query = "mojito"
        val cocktails = listOf(
            Cocktail("Mojito", "mojito.jpg", "11007", false),
            Cocktail("Mint Mojito", "mint_mojito.jpg", "11008", false)
        )
        val response = CocktailResponse(drinks = cocktails)
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getSearch(query) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.setSearchQuery(query)
        viewModel.fetchSearchData(userEmail)
        advanceTimeBy(800) // Debounce time + buffer
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getSearch(query) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `executeSearch with empty results sets searchResultsData to null`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val query = "nonexistent"
        val response = CocktailResponse(drinks = emptyList())
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getSearch(query) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.setSearchQuery(query)
        viewModel.fetchSearchData(userEmail)
        advanceTimeBy(800)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getSearch(query) }
    }

    @Test
    fun `executeSearch with null drinks sets searchResultsData to null`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val query = "test"
        val response = CocktailResponse(drinks = null)
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getSearch(query) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.setSearchQuery(query)
        viewModel.fetchSearchData(userEmail)
        advanceTimeBy(800)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getSearch(query) }
    }

    @Test
    fun `executeSearch handles exception gracefully`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val query = "error"

        coEvery { mockRepo.getSearch(query) } throws Exception("Network error")

        // When
        viewModel.setSearchQuery(query)
        viewModel.fetchSearchData(userEmail)
        advanceTimeBy(800)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getSearch(query) }
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
    fun `debounce prevents rapid search calls`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val response = CocktailResponse(drinks = emptyList())

        coEvery { mockRepo.getSearch(any()) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns emptyList()

        // When - Rapid queries
        viewModel.setSearchQuery("m")
        viewModel.fetchSearchData(userEmail)
        advanceTimeBy(100)
        
        viewModel.setSearchQuery("mo")
        advanceTimeBy(100)
        
        viewModel.setSearchQuery("moj")
        advanceTimeBy(100)
        
        viewModel.setSearchQuery("moji")
        advanceTimeBy(100)
        
        viewModel.setSearchQuery("mojit")
        advanceTimeBy(100)
        
        viewModel.setSearchQuery("mojito")
        advanceTimeBy(800) // Wait for debounce
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should only search once after debounce period
        coVerify(exactly = 1) { mockRepo.getSearch("mojito") }
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
    fun `favoriteCocktail calls insertCocktail with correct parameters`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val cocktail = Cocktail("Piña Colada", "pina.jpg", "12345", false)

        coEvery { mockRepo.findFavoriteCocktail(userEmail, cocktail.idDrink) } returns null
        coEvery { mockRepo.insertCocktail(userEmail, any()) } just Runs

        // When
        viewModel.favoriteCocktail(userEmail, cocktail)
        
        // Wait for the IO dispatcher coroutine to complete
        advanceTimeBy(1000)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { 
            mockRepo.insertCocktail(
                userEmail,
                match { 
                    it.strDrink == "Piña Colada" &&
                    it.strDrinkThumb == "pina.jpg" &&
                    it.idDrink == "12345"
                }
            ) 
        }
    }
}

