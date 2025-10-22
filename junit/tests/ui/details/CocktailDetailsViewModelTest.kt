package com.example.cocktailsdbapp.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cocktailsdbapp.model.CocktailDetails
import com.example.cocktailsdbapp.model.CocktailDetailsResponse
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
 * Unit tests for CocktailDetailsViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CocktailDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CocktailDetailsViewModel
    private lateinit var mockRepo: CocktailsRepo
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockRepo = mockk()
        viewModel = CocktailDetailsViewModel(mockRepo)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `getDetails with valid cocktail details returns first cocktail`() = runTest {
        // Given
        val cocktailId = "11007"
        val cocktailDetails = CocktailDetails(
            idDrink = "11007",
            strDrink = "Mojito",
            strDrinkAlternate = null,
            strTags = "IBA,ContemporaryClassic",
            strVideo = null,
            strCategory = "Cocktail",
            strIBA = "Contemporary Classics",
            strAlcoholic = "Alcoholic",
            strGlass = "Highball glass",
            strInstructions = "Muddle mint leaves with sugar and lime juice. Add a splash of soda water and fill the glass with cracked ice. Pour the rum and top with soda water. Garnish and serve with straw.",
            strInstructionsES = null,
            strInstructionsDE = null,
            strInstructionsFR = null,
            strInstructionsIT = null,
            strInstructionsZH_HANS = null,
            strInstructionsZH_HANT = null,
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/metwgh1606770327.jpg",
            strIngredient1 = "White rum",
            strIngredient2 = "Lime",
            strIngredient3 = "Sugar",
            strIngredient4 = "Mint",
            strIngredient5 = "Soda water",
            strIngredient6 = null,
            strIngredient7 = null,
            strIngredient8 = null,
            strIngredient9 = null,
            strIngredient10 = null,
            strIngredient11 = null,
            strIngredient12 = null,
            strIngredient13 = null,
            strIngredient14 = null,
            strIngredient15 = null,
            strMeasure1 = "2-3 oz",
            strMeasure2 = "Juice of 1/2",
            strMeasure3 = "2 tsp",
            strMeasure4 = "2-4",
            strMeasure5 = "Top",
            strMeasure6 = null,
            strMeasure7 = null,
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strImageSource = null,
            strImageAttribution = null,
            strCreativeCommonsConfirmed = null,
            dateModified = null,
            isFavorite = false
        )
        val response = CocktailDetailsResponse(drinks = listOf(cocktailDetails))

        coEvery { mockRepo.getCocktailDetails(cocktailId) } returns response

        // When
        viewModel.getDetails(cocktailId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailDetails(cocktailId) }
        assertNotNull(viewModel.cocktailDetailsData.value)
        assertEquals("11007", viewModel.cocktailDetailsData.value?.idDrink)
        assertEquals("Mojito", viewModel.cocktailDetailsData.value?.strDrink)
        assertEquals("Cocktail", viewModel.cocktailDetailsData.value?.strCategory)
    }

    @Test
    fun `getDetails with empty drinks list sets null`() = runTest {
        // Given
        val cocktailId = "11007"
        val response = CocktailDetailsResponse(drinks = emptyList())

        coEvery { mockRepo.getCocktailDetails(cocktailId) } returns response

        // When
        viewModel.getDetails(cocktailId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailDetails(cocktailId) }
        assertNull(viewModel.cocktailDetailsData.value)
    }

    @Test
    fun `getDetails with null drinks sets null`() = runTest {
        // Given
        val cocktailId = "11007"
        val response = CocktailDetailsResponse(drinks = null)

        coEvery { mockRepo.getCocktailDetails(cocktailId) } returns response

        // When
        viewModel.getDetails(cocktailId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailDetails(cocktailId) }
        assertNull(viewModel.cocktailDetailsData.value)
    }

    @Test
    fun `getDetails handles exception gracefully`() = runTest {
        // Given
        val cocktailId = "11007"

        coEvery { mockRepo.getCocktailDetails(cocktailId) } throws Exception("Network error")

        // When
        viewModel.getDetails(cocktailId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getCocktailDetails(cocktailId) }
    }

    @Test
    fun `cocktailDetailsData is initialized`() {
        // Then
        assertNotNull(viewModel.cocktailDetailsData)
    }
}
