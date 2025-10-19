package com.example.cocktailsdbapp.repository

import com.example.cocktailsdbapp.database.CocktailDao
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.database.RoomFavorite
import com.example.cocktailsdbapp.model.*
import com.example.cocktailsdbapp.network.CocktailApiService
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests for CocktailsRepoImpl
 */
class CocktailsRepoImplTest {

    private lateinit var repository: CocktailsRepoImpl
    private lateinit var mockApiService: CocktailApiService
    private lateinit var mockCocktailDao: CocktailDao

    @Before
    fun setup() {
        mockApiService = mockk()
        mockCocktailDao = mockk()
        repository = CocktailsRepoImpl(mockApiService, mockCocktailDao)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getCocktailsByAlcoholContent returns response from API`() = runTest {
        // Given
        val alcoholContent = "Alcoholic"
        val expectedResponse = CocktailResponse(
            drinks = listOf(
                Cocktail("Mojito", "mojito.jpg", "11007")
            )
        )
        coEvery { mockApiService.getCocktailsByAlcoholContent(alcoholContent) } returns expectedResponse

        // When
        val result = repository.getCocktailsByAlcoholContent(alcoholContent)

        // Then
        assertEquals(expectedResponse, result)
        assertEquals(1, result.drinks?.size)
        assertEquals("Mojito", result.drinks?.first()?.strDrink)
        coVerify(exactly = 1) { mockApiService.getCocktailsByAlcoholContent(alcoholContent) }
    }

    @Test
    fun `getCocktailsByGlass returns response from API`() = runTest {
        // Given
        val glass = "Highball glass"
        val expectedResponse = CocktailResponse(
            drinks = listOf(
                Cocktail("Mojito", "mojito.jpg", "11007")
            )
        )
        coEvery { mockApiService.getCocktailsByGlass(glass) } returns expectedResponse

        // When
        val result = repository.getCocktailsByGlass(glass)

        // Then
        assertEquals(expectedResponse, result)
        assertEquals("Mojito", result.drinks?.first()?.strDrink)
        coVerify(exactly = 1) { mockApiService.getCocktailsByGlass(glass) }
    }

    @Test
    fun `getCocktailsByCategory returns response from API`() = runTest {
        // Given
        val category = "Cocktail"
        val expectedResponse = CocktailResponse(
            drinks = listOf(
                Cocktail("Margarita", "margarita.jpg", "11008")
            )
        )
        coEvery { mockApiService.getCocktailsByCategory(category) } returns expectedResponse

        // When
        val result = repository.getCocktailsByCategory(category)

        // Then
        assertEquals(expectedResponse, result)
        assertEquals("Margarita", result.drinks?.first()?.strDrink)
        coVerify(exactly = 1) { mockApiService.getCocktailsByCategory(category) }
    }

    @Test
    fun `getCocktailsByFirstLetter returns response from API`() = runTest {
        // Given
        val letter = "m"
        val expectedResponse = CocktailResponse(
            drinks = listOf(
                Cocktail("Mojito", "mojito.jpg", "11007"),
                Cocktail("Margarita", "margarita.jpg", "11008")
            )
        )
        coEvery { mockApiService.getCocktailsByFirstLetter(letter) } returns expectedResponse

        // When
        val result = repository.getCocktailsByFirstLetter(letter)

        // Then
        assertEquals(expectedResponse, result)
        assertEquals(2, result.drinks?.size)
        coVerify(exactly = 1) { mockApiService.getCocktailsByFirstLetter(letter) }
    }

    @Test
    fun `getCocktailsByIngredient returns response from API`() = runTest {
        // Given
        val ingredient = "Vodka"
        val expectedResponse = CocktailResponse(
            drinks = listOf(
                Cocktail("Martini", "martini.jpg", "11009")
            )
        )
        coEvery { mockApiService.getCocktailsByIngredient(ingredient) } returns expectedResponse

        // When
        val result = repository.getCocktailsByIngredient(ingredient)

        // Then
        assertEquals(expectedResponse, result)
        assertEquals("Martini", result.drinks?.first()?.strDrink)
        coVerify(exactly = 1) { mockApiService.getCocktailsByIngredient(ingredient) }
    }

    @Test
    fun `getAlcoholContent returns response from API`() = runTest {
        // Given
        val expectedResponse = AlcoholContentResponse(
            drinks = listOf(
                AlcoholContent("Alcoholic"),
                AlcoholContent("Non alcoholic")
            )
        )
        coEvery { mockApiService.getAlcoholContent() } returns expectedResponse

        // When
        val result = repository.getAlcoholContent()

        // Then
        assertEquals(expectedResponse, result)
        assertEquals(2, result.drinks.size)
        coVerify(exactly = 1) { mockApiService.getAlcoholContent() }
    }

    @Test
    fun `getCategories returns response from API`() = runTest {
        // Given
        val expectedResponse = FilterResponse(
            drinks = listOf(
                DrinkCategory("Cocktail"),
                DrinkCategory("Shot")
            )
        )
        coEvery { mockApiService.getCategories() } returns expectedResponse

        // When
        val result = repository.getCategories()

        // Then
        assertEquals(expectedResponse, result)
        assertEquals(2, result.drinks.size)
        coVerify(exactly = 1) { mockApiService.getCategories() }
    }

    @Test
    fun `getGlasses returns response from API`() = runTest {
        // Given
        val expectedResponse = GlassesResponse(
            drinks = listOf(
                Glass("Highball glass"),
                Glass("Cocktail glass")
            )
        )
        coEvery { mockApiService.getGlasses() } returns expectedResponse

        // When
        val result = repository.getGlasses()

        // Then
        assertEquals(expectedResponse, result)
        assertEquals(2, result.drinks.size)
        coVerify(exactly = 1) { mockApiService.getGlasses() }
    }

    @Test
    fun `getIngredients returns response from API`() = runTest {
        // Given
        val expectedResponse = IngredientListResponse(
            drinks = listOf(
                StrIngredient("Vodka"),
                StrIngredient("Gin")
            )
        )
        coEvery { mockApiService.getIngredients() } returns expectedResponse

        // When
        val result = repository.getIngredients()

        // Then
        assertEquals(expectedResponse, result)
        assertEquals(2, result.drinks.size)
        coVerify(exactly = 1) { mockApiService.getIngredients() }
    }

    @Test
    fun `getCocktailDetails returns response from API`() = runTest {
        // Given
        val cocktailId = "11007"
        val cocktailDetails = CocktailDetails(
            idDrink = "11007",
            strDrink = "Mojito",
            strDrinkAlternate = null,
            strTags = null,
            strVideo = null,
            strCategory = "Cocktail",
            strIBA = null,
            strAlcoholic = "Alcoholic",
            strGlass = "Highball glass",
            strInstructions = "Mix ingredients",
            strInstructionsES = null,
            strInstructionsDE = null,
            strInstructionsFR = null,
            strInstructionsIT = null,
            strInstructionsZH_HANS = null,
            strInstructionsZH_HANT = null,
            strDrinkThumb = "mojito.jpg",
            strIngredient1 = "Rum",
            strIngredient2 = "Lime",
            strIngredient3 = null,
            strIngredient4 = null,
            strIngredient5 = null,
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
            strMeasure1 = "2 oz",
            strMeasure2 = "1 oz",
            strMeasure3 = null,
            strMeasure4 = null,
            strMeasure5 = null,
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
        val expectedResponse = CocktailDetailsResponse(drinks = listOf(cocktailDetails))
        coEvery { mockApiService.getCocktailDetails(cocktailId) } returns expectedResponse

        // When
        val result = repository.getCocktailDetails(cocktailId)

        // Then
        assertEquals(expectedResponse, result)
        assertEquals("Mojito", result.drinks?.first()?.strDrink)
        coVerify(exactly = 1) { mockApiService.getCocktailDetails(cocktailId) }
    }

    @Test
    fun `getSearch returns transformed response from API`() = runTest {
        // Given
        val searchParam = "mojito"
        val cocktailDetails = CocktailDetails(
            idDrink = "11007",
            strDrink = "Mojito",
            strDrinkAlternate = null,
            strTags = null,
            strVideo = null,
            strCategory = null,
            strIBA = null,
            strAlcoholic = null,
            strGlass = null,
            strInstructions = null,
            strInstructionsES = null,
            strInstructionsDE = null,
            strInstructionsFR = null,
            strInstructionsIT = null,
            strInstructionsZH_HANS = null,
            strInstructionsZH_HANT = null,
            strDrinkThumb = "mojito.jpg",
            strIngredient1 = null,
            strIngredient2 = null,
            strIngredient3 = null,
            strIngredient4 = null,
            strIngredient5 = null,
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
            strMeasure1 = null,
            strMeasure2 = null,
            strMeasure3 = null,
            strMeasure4 = null,
            strMeasure5 = null,
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
        val apiResponse = CocktailDetailsResponse(drinks = listOf(cocktailDetails))
        coEvery { mockApiService.getSearch(searchParam) } returns apiResponse

        // When
        val result = repository.getSearch(searchParam)

        // Then
        assertNotNull(result)
        assertEquals(1, result.drinks?.size)
        coVerify(exactly = 1) { mockApiService.getSearch(searchParam) }
    }

    @Test
    fun `getFavorites returns favorites from database`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val expectedFavorites = listOf(
            RoomCocktail("Mojito", "mojito.jpg", "11007"),
            RoomCocktail("Margarita", "margarita.jpg", "11008")
        )
        coEvery { mockCocktailDao.getFavoriteCocktails(userEmail) } returns expectedFavorites

        // When
        val result = repository.getFavorites(userEmail)

        // Then
        assertEquals(expectedFavorites, result)
        assertEquals(2, result?.size)
        assertEquals("Mojito", result?.first()?.strDrink)
        coVerify(exactly = 1) { mockCocktailDao.getFavoriteCocktails(userEmail) }
    }

    @Test
    fun `getFavorites returns null when no favorites`() = runTest {
        // Given
        val userEmail = "test@example.com"
        coEvery { mockCocktailDao.getFavoriteCocktails(userEmail) } returns null

        // When
        val result = repository.getFavorites(userEmail)

        // Then
        assertNull(result)
        coVerify(exactly = 1) { mockCocktailDao.getFavoriteCocktails(userEmail) }
    }

    @Test
    fun `findFavoriteCocktail returns cocktail from database`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val cocktailId = "11007"
        val expectedCocktail = RoomCocktail("Mojito", "mojito.jpg", "11007")
        coEvery { mockCocktailDao.findFavoriteCocktail(userEmail, cocktailId) } returns expectedCocktail

        // When
        val result = repository.findFavoriteCocktail(userEmail, cocktailId)

        // Then
        assertEquals(expectedCocktail, result)
        assertEquals("Mojito", result?.strDrink)
        coVerify(exactly = 1) { mockCocktailDao.findFavoriteCocktail(userEmail, cocktailId) }
    }

    @Test
    fun `findFavoriteCocktail returns null when not found`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val cocktailId = "11007"
        coEvery { mockCocktailDao.findFavoriteCocktail(userEmail, cocktailId) } returns null

        // When
        val result = repository.findFavoriteCocktail(userEmail, cocktailId)

        // Then
        assertNull(result)
        coVerify(exactly = 1) { mockCocktailDao.findFavoriteCocktail(userEmail, cocktailId) }
    }

    @Test
    fun `removeFavorite calls dao removeFavorite`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val cocktailId = "11007"
        coEvery { mockCocktailDao.removeFavorite(userEmail, cocktailId) } just Runs

        // When
        repository.removeFavorite(userEmail, cocktailId)

        // Then
        coVerify(exactly = 1) { mockCocktailDao.removeFavorite(userEmail, cocktailId) }
    }

    @Test
    fun `insertCocktail inserts cocktail and favorite to database`() = runTest {
        // Given
        val userEmail = "test@example.com"
        val roomCocktail = RoomCocktail("Mojito", "mojito.jpg", "11007")
        coEvery { mockCocktailDao.insertCocktail(roomCocktail) } just Runs
        coEvery { mockCocktailDao.insertFavorite(any()) } just Runs

        // When
        repository.insertCocktail(userEmail, roomCocktail)

        // Then
        coVerify(exactly = 1) { mockCocktailDao.insertCocktail(roomCocktail) }
        coVerify(exactly = 1) { 
            mockCocktailDao.insertFavorite(
                match { it.userEmail == userEmail && it.idDrink == "11007" }
            ) 
        }
    }

    @Test
    fun `insertCocktail creates correct RoomFavorite`() = runTest {
        // Given
        val userEmail = "user@test.com"
        val roomCocktail = RoomCocktail("Martini", "martini.jpg", "11009")
        val capturedFavorite = slot<RoomFavorite>()
        
        coEvery { mockCocktailDao.insertCocktail(roomCocktail) } just Runs
        coEvery { mockCocktailDao.insertFavorite(capture(capturedFavorite)) } just Runs

        // When
        repository.insertCocktail(userEmail, roomCocktail)

        // Then
        assertEquals(userEmail, capturedFavorite.captured.userEmail)
        assertEquals("11009", capturedFavorite.captured.idDrink)
    }

    @Test
    fun `getCocktailsByAlcoholContent handles empty response`() = runTest {
        // Given
        val alcoholContent = "Alcoholic"
        val expectedResponse = CocktailResponse(drinks = emptyList())
        coEvery { mockApiService.getCocktailsByAlcoholContent(alcoholContent) } returns expectedResponse

        // When
        val result = repository.getCocktailsByAlcoholContent(alcoholContent)

        // Then
        assertEquals(expectedResponse, result)
        assertTrue(result.drinks?.isEmpty() == true)
    }

    @Test
    fun `getCocktailsByAlcoholContent handles null response`() = runTest {
        // Given
        val alcoholContent = "Alcoholic"
        val expectedResponse = CocktailResponse(drinks = null)
        coEvery { mockApiService.getCocktailsByAlcoholContent(alcoholContent) } returns expectedResponse

        // When
        val result = repository.getCocktailsByAlcoholContent(alcoholContent)

        // Then
        assertEquals(expectedResponse, result)
        assertNull(result.drinks)
    }
}

