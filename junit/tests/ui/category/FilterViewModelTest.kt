package com.example.cocktailsdbapp.ui.category

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cocktailsdbapp.model.AlcoholContentResponse
import com.example.cocktailsdbapp.model.FilterResponse
import com.example.cocktailsdbapp.model.GlassesResponse
import com.example.cocktailsdbapp.model.IngredientListResponse
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
 * Unit tests for FilterViewModel
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FilterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FilterViewModel
    private lateinit var mockRepo: CocktailsRepo
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        mockRepo = mockk()
        viewModel = FilterViewModel(mockRepo)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchData with FILTER_ALCOHOL calls getAlcoholContent`() = runTest {
        // Given
        val alcoholContentResponse = AlcoholContentResponse(
            drinks = listOf(
                com.example.cocktailsdbapp.model.AlcoholContent(strAlcoholic = "Alcoholic"),
                com.example.cocktailsdbapp.model.AlcoholContent(strAlcoholic = "Non alcoholic"),
                com.example.cocktailsdbapp.model.AlcoholContent(strAlcoholic = "Optional alcohol")
            )
        )

        coEvery { mockRepo.getAlcoholContent() } returns alcoholContentResponse

        // When
        viewModel.fetchData(Constants.FILTER_ALCOHOL)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getAlcoholContent() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertEquals(3, result?.size)
        assertTrue(result?.contains("Alcoholic") == true)
        assertTrue(result?.contains("Non alcoholic") == true)
        assertTrue(result?.contains("Optional alcohol") == true)
    }

    @Test
    fun `fetchData with FILTER_CATEGORY calls getCategories`() = runTest {
        // Given
        val categoryResponse = FilterResponse(
            drinks = listOf(
                com.example.cocktailsdbapp.model.DrinkCategory(strCategory = "Cocktail"),
                com.example.cocktailsdbapp.model.DrinkCategory(strCategory = "Ordinary Drink"),
                com.example.cocktailsdbapp.model.DrinkCategory(strCategory = "Shot")
            )
        )

        coEvery { mockRepo.getCategories() } returns categoryResponse

        // When
        viewModel.fetchData(Constants.FILTER_CATEGORY)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCategories() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertEquals(3, result?.size)
        assertTrue(result?.contains("Cocktail") == true)
        assertTrue(result?.contains("Ordinary Drink") == true)
        assertTrue(result?.contains("Shot") == true)
    }

    @Test
    fun `fetchData with FILTER_GLASS calls getGlasses`() = runTest {
        // Given
        val glassResponse = GlassesResponse(
            drinks = listOf(
                com.example.cocktailsdbapp.model.Glass(strGlass = "Highball glass"),
                com.example.cocktailsdbapp.model.Glass(strGlass = "Cocktail glass"),
                com.example.cocktailsdbapp.model.Glass(strGlass = "Shot glass")
            )
        )

        coEvery { mockRepo.getGlasses() } returns glassResponse

        // When
        viewModel.fetchData(Constants.FILTER_GLASS)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getGlasses() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertEquals(3, result?.size)
        assertTrue(result?.contains("Highball glass") == true)
        assertTrue(result?.contains("Cocktail glass") == true)
        assertTrue(result?.contains("Shot glass") == true)
    }

    @Test
    fun `fetchData with FILTER_INGREDIENT calls getIngredients`() = runTest {
        // Given
        val ingredientResponse = IngredientListResponse(
            drinks = listOf(
                com.example.cocktailsdbapp.model.StrIngredient(strIngredient = "Vodka"),
                com.example.cocktailsdbapp.model.StrIngredient(strIngredient = "Gin"),
                com.example.cocktailsdbapp.model.StrIngredient(strIngredient = "Rum")
            )
        )

        coEvery { mockRepo.getIngredients() } returns ingredientResponse

        // When
        viewModel.fetchData(Constants.FILTER_INGREDIENT)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getIngredients() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertEquals(3, result?.size)
        assertTrue(result?.contains("Vodka") == true)
        assertTrue(result?.contains("Gin") == true)
        assertTrue(result?.contains("Rum") == true)
    }

    @Test
    fun `fetchData with FILTER_LETTER returns alphabet letters`() = runTest {
        // When
        viewModel.fetchData(Constants.FILTER_LETTER)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertEquals(26, result?.size) // A to Z
        assertEquals("A", result?.first())
        assertEquals("Z", result?.last())
        assertTrue(result?.contains("M") == true)
        assertTrue(result?.contains("X") == true)
    }

    @Test
    fun `fetchData with unknown filter type does nothing`() = runTest {
        // When
        viewModel.fetchData("Unknown Filter")
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { mockRepo wasNot Called }
        assertNull(viewModel.filterData.value)
    }

    @Test
    fun `getAlcoholContent handles empty response`() = runTest {
        // Given
        val emptyResponse = AlcoholContentResponse(drinks = emptyList())

        coEvery { mockRepo.getAlcoholContent() } returns emptyResponse

        // When
        viewModel.fetchData(Constants.FILTER_ALCOHOL)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getAlcoholContent() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertTrue(result?.isEmpty() == true)
    }

    @Test
    fun `getCategories handles empty response`() = runTest {
        // Given
        val emptyResponse = FilterResponse(drinks = emptyList())

        coEvery { mockRepo.getCategories() } returns emptyResponse

        // When
        viewModel.fetchData(Constants.FILTER_CATEGORY)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCategories() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertTrue(result?.isEmpty() == true)
    }

    @Test
    fun `getGlasses handles empty response`() = runTest {
        // Given
        val emptyResponse = GlassesResponse(drinks = emptyList())

        coEvery { mockRepo.getGlasses() } returns emptyResponse

        // When
        viewModel.fetchData(Constants.FILTER_GLASS)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getGlasses() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertTrue(result?.isEmpty() == true)
    }

    @Test
    fun `getIngredients handles empty response`() = runTest {
        // Given
        val emptyResponse = IngredientListResponse(drinks = emptyList())

        coEvery { mockRepo.getIngredients() } returns emptyResponse

        // When
        viewModel.fetchData(Constants.FILTER_INGREDIENT)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getIngredients() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertTrue(result?.isEmpty() == true)
    }

    @Test
    fun `getAlcoholContent handles exception gracefully`() = runTest {
        // Given
        coEvery { mockRepo.getAlcoholContent() } throws Exception("Network error")

        // When
        viewModel.fetchData(Constants.FILTER_ALCOHOL)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getAlcoholContent() }
    }

    @Test
    fun `getCategories handles exception gracefully`() = runTest {
        // Given
        coEvery { mockRepo.getCategories() } throws Exception("Network error")

        // When
        viewModel.fetchData(Constants.FILTER_CATEGORY)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getCategories() }
    }

    @Test
    fun `getGlasses handles exception gracefully`() = runTest {
        // Given
        coEvery { mockRepo.getGlasses() } throws Exception("Network error")

        // When
        viewModel.fetchData(Constants.FILTER_GLASS)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getGlasses() }
    }

    @Test
    fun `getIngredients handles exception gracefully`() = runTest {
        // Given
        coEvery { mockRepo.getIngredients() } throws Exception("Network error")

        // When
        viewModel.fetchData(Constants.FILTER_INGREDIENT)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Should not crash
        coVerify { mockRepo.getIngredients() }
    }

    @Test
    fun `filterData is initialized`() {
        // Then
        assertNotNull(viewModel.filterData)
    }

    @Test
    fun `fetchData with multiple calls updates data correctly`() = runTest {
        // Given
        val alcoholResponse = AlcoholContentResponse(
            drinks = listOf(
                com.example.cocktailsdbapp.model.AlcoholContent(strAlcoholic = "Alcoholic")
            )
        )
        val categoryResponse = FilterResponse(
            drinks = listOf(
                com.example.cocktailsdbapp.model.DrinkCategory(strCategory = "Cocktail")
            )
        )

        coEvery { mockRepo.getAlcoholContent() } returns alcoholResponse
        coEvery { mockRepo.getCategories() } returns categoryResponse

        // When - First call
        viewModel.fetchData(Constants.FILTER_ALCOHOL)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - First call
        assertEquals("Alcoholic", viewModel.filterData.value?.first())

        // When - Second call
        viewModel.fetchData(Constants.FILTER_CATEGORY)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Second call
        assertEquals("Cocktail", viewModel.filterData.value?.first())
    }





    @Test
    fun `getAlcoholContent with large dataset`() = runTest {
        // Given
        val largeResponse = AlcoholContentResponse(
            drinks = (1..100).map { 
                com.example.cocktailsdbapp.model.AlcoholContent(strAlcoholic = "Alcoholic $it")
            }
        )

        coEvery { mockRepo.getAlcoholContent() } returns largeResponse

        // When
        viewModel.fetchData(Constants.FILTER_ALCOHOL)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getAlcoholContent() }
        val result = viewModel.filterData.value
        assertNotNull(result)
        assertEquals(100, result?.size)
        assertTrue(result?.contains("Alcoholic 1") == true)
        assertTrue(result?.contains("Alcoholic 50") == true)
        assertTrue(result?.contains("Alcoholic 100") == true)
    }

    @Test
    fun `getFirstLetters returns correct alphabet range`() = runTest {
        // When
        viewModel.fetchData(Constants.FILTER_LETTER)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val result = viewModel.filterData.value
        assertNotNull(result)
        
        // Check that all letters from A to Z are present
        for (char in 'A'..'Z') {
            assertTrue("Letter $char should be present", result?.contains(char.toString()) == true)
        }
        
        // Check that no other characters are present
        assertEquals(26, result?.size)
        assertTrue(result?.all { it.length == 1 && it[0] in 'A'..'Z' } == true)
    }
}
