package com.example.cocktailsdbapp.ui.cocktails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.model.CocktailResponse
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.utils.Constants
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
 * Unit tests for CocktailsViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CocktailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CocktailsViewModel
    private lateinit var mockRepo: CocktailsRepo
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepo = mockk()
        viewModel = CocktailsViewModel(mockRepo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `fetchData with alcohol filter calls correct repository method`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = Constants.FILTER_ALCOHOL
        val filter = "Alcoholic"
        val cocktails = listOf(
            Cocktail("Mojito", "mojito.jpg", "11007", false),
            Cocktail("Margarita", "margarita.jpg", "11008", false)
        )
        val response = CocktailResponse(drinks = cocktails)
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getCocktailsByAlcoholContent(filter) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailsByAlcoholContent(filter) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `fetchData with category filter calls correct repository method`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = Constants.FILTER_CATEGORY
        val filter = "Cocktail"
        val cocktails = listOf(Cocktail("Martini", "martini.jpg", "11009", false))
        val response = CocktailResponse(drinks = cocktails)
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getCocktailsByCategory(filter) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailsByCategory(filter) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `fetchData with glass filter calls correct repository method`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = Constants.FILTER_GLASS
        val filter = "Highball glass"
        val cocktails = listOf(Cocktail("Mojito", "mojito.jpg", "11007", false))
        val response = CocktailResponse(drinks = cocktails)
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getCocktailsByGlass(filter) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailsByGlass(filter) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `fetchData with ingredient filter calls correct repository method`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = Constants.FILTER_INGREDIENT
        val filter = "Vodka"
        val cocktails = listOf(Cocktail("Vodka Martini", "vodka_martini.jpg", "11010", false))
        val response = CocktailResponse(drinks = cocktails)
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getCocktailsByIngredient(filter) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailsByIngredient(filter) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `fetchData with letter filter calls correct repository method`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = Constants.FILTER_LETTER
        val filter = "M"
        val cocktails = listOf(
            Cocktail("Mojito", "mojito.jpg", "11007", false),
            Cocktail("Margarita", "margarita.jpg", "11008", false),
            Cocktail("Martini", "martini.jpg", "11009", false)
        )
        val response = CocktailResponse(drinks = cocktails)
        val favorites = listOf<RoomCocktail>()

        coEvery { mockRepo.getCocktailsByFirstLetter(filter) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns favorites

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailsByFirstLetter(filter) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `fetchData with unknown filter returns empty response`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = "Unknown"
        val filter = "test"

        coEvery { mockRepo.getFavorites(userEmail) } returns listOf<RoomCocktail>()

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify(exactly = 0) { mockRepo.getCocktailsByAlcoholContent(any()) }
        coVerify(exactly = 0) { mockRepo.getCocktailsByCategory(any()) }
        coVerify(exactly = 0) { mockRepo.getCocktailsByGlass(any()) }
        coVerify(exactly = 0) { mockRepo.getCocktailsByIngredient(any()) }
        coVerify(exactly = 0) { mockRepo.getCocktailsByFirstLetter(any()) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `fetchData with null favorites still processes response`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = Constants.FILTER_ALCOHOL
        val filter = "Alcoholic"
        val cocktails = listOf(Cocktail("Mojito", "mojito.jpg", "11007", false))
        val response = CocktailResponse(drinks = cocktails)

        coEvery { mockRepo.getCocktailsByAlcoholContent(filter) } returns response
        coEvery { mockRepo.getFavorites(userEmail) } returns null

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailsByAlcoholContent(filter) }
        coVerify { mockRepo.getFavorites(userEmail) }
    }

    @Test
    fun `fetchData handles exception gracefully`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val filterCategory = Constants.FILTER_ALCOHOL
        val filter = "Alcoholic"

        coEvery { mockRepo.getCocktailsByAlcoholContent(filter) } throws Exception("Network error")

        // When
        viewModel.fetchData(userEmail, filterCategory, filter)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getCocktailsByAlcoholContent(filter) }
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

        coVerify(exactly = 0) { mockRepo.removeFavorite(any(), any()) }
    }

    @Test
    fun `cocktailsData is initialized`() {
        // Then
        assertNotNull(viewModel.cocktailsData)
    }
}

