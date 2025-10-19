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
    fun `getDetails with multiple cocktails returns first one`() = runTest {
        // Given
        val cocktailId = "11007"
        val firstCocktail = CocktailDetails(
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
        val secondCocktail = CocktailDetails(
            idDrink = "11008",
            strDrink = "Margarita",
            strDrinkAlternate = null,
            strTags = null,
            strVideo = null,
            strCategory = "Cocktail",
            strIBA = null,
            strAlcoholic = "Alcoholic",
            strGlass = "Cocktail glass",
            strInstructions = "Mix ingredients",
            strInstructionsES = null,
            strInstructionsDE = null,
            strInstructionsFR = null,
            strInstructionsIT = null,
            strInstructionsZH_HANS = null,
            strInstructionsZH_HANT = null,
            strDrinkThumb = "margarita.jpg",
            strIngredient1 = "Tequila",
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
        val response = CocktailDetailsResponse(drinks = listOf(firstCocktail, secondCocktail))

        coEvery { mockRepo.getCocktailDetails(cocktailId) } returns response

        // When
        viewModel.getDetails(cocktailId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailDetails(cocktailId) }
        assertNotNull(viewModel.cocktailDetailsData.value)
        assertEquals("11007", viewModel.cocktailDetailsData.value?.idDrink)
        assertEquals("Mojito", viewModel.cocktailDetailsData.value?.strDrink)
        // Should return the first cocktail, not the second
        assertNotEquals("11008", viewModel.cocktailDetailsData.value?.idDrink)
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
    fun `getDetails with different cocktail IDs calls repository with correct ID`() = runTest {
        // Given
        val cocktailId1 = "11007"
        val cocktailId2 = "11008"
        val cocktailDetails1 = CocktailDetails(
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
        val cocktailDetails2 = CocktailDetails(
            idDrink = "11008",
            strDrink = "Margarita",
            strDrinkAlternate = null,
            strTags = null,
            strVideo = null,
            strCategory = "Cocktail",
            strIBA = null,
            strAlcoholic = "Alcoholic",
            strGlass = "Cocktail glass",
            strInstructions = "Mix ingredients",
            strInstructionsES = null,
            strInstructionsDE = null,
            strInstructionsFR = null,
            strInstructionsIT = null,
            strInstructionsZH_HANS = null,
            strInstructionsZH_HANT = null,
            strDrinkThumb = "margarita.jpg",
            strIngredient1 = "Tequila",
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

        coEvery { mockRepo.getCocktailDetails(cocktailId1) } returns CocktailDetailsResponse(drinks = listOf(cocktailDetails1))
        coEvery { mockRepo.getCocktailDetails(cocktailId2) } returns CocktailDetailsResponse(drinks = listOf(cocktailDetails2))

        // When
        viewModel.getDetails(cocktailId1)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.getDetails(cocktailId2)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailDetails(cocktailId1) }
        coVerify { mockRepo.getCocktailDetails(cocktailId2) }
        assertEquals("11008", viewModel.cocktailDetailsData.value?.idDrink)
        assertEquals("Margarita", viewModel.cocktailDetailsData.value?.strDrink)
    }

    @Test
    fun `cocktailDetailsData is initialized`() {
        // Then
        assertNotNull(viewModel.cocktailDetailsData)
    }

    @Test
    fun `getDetails with cocktail having all fields populated`() = runTest {
        // Given
        val cocktailId = "11007"
        val cocktailDetails = CocktailDetails(
            idDrink = "11007",
            strDrink = "Mojito",
            strDrinkAlternate = "Cuban Mojito",
            strTags = "IBA,ContemporaryClassic,Summer",
            strVideo = "https://www.youtube.com/watch?v=example",
            strCategory = "Cocktail",
            strIBA = "Contemporary Classics",
            strAlcoholic = "Alcoholic",
            strGlass = "Highball glass",
            strInstructions = "Muddle mint leaves with sugar and lime juice. Add a splash of soda water and fill the glass with cracked ice. Pour the rum and top with soda water. Garnish and serve with straw.",
            strInstructionsES = "Machacar las hojas de menta con azúcar y jugo de lima. Agregar un chorrito de agua con gas y llenar el vaso con hielo picado. Verter el ron y completar con agua con gas. Decorar y servir con pajita.",
            strInstructionsDE = "Minzblätter mit Zucker und Limettensaft zerdrücken. Einen Spritzer Sodawasser hinzufügen und das Glas mit gebrochenem Eis füllen. Den Rum eingießen und mit Sodawasser auffüllen. Garnieren und mit Strohhalm servieren.",
            strInstructionsFR = "Écraser les feuilles de menthe avec le sucre et le jus de citron vert. Ajouter un éclaboussure d'eau gazeuse et remplir le verre avec de la glace pilée. Verser le rhum et compléter avec de l'eau gazeuse. Garnir et servir avec une paille.",
            strInstructionsIT = "Schiacciare le foglie di menta con zucchero e succo di lime. Aggiungere uno spruzzo di acqua gassata e riempire il bicchiere con ghiaccio tritato. Versare il rum e completare con acqua gassata. Guarnire e servire con cannuccia.",
            strInstructionsZH_HANS = "将薄荷叶与糖和酸橙汁捣碎。加入少量苏打水，用碎冰装满杯子。倒入朗姆酒，用苏打水加满。装饰并用吸管服务。",
            strInstructionsZH_HANT = "將薄荷葉與糖和酸橙汁搗碎。加入少量蘇打水，用碎冰裝滿杯子。倒入朗姆酒，用蘇打水加滿。裝飾並用吸管服務。",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/metwgh1606770327.jpg",
            strIngredient1 = "White rum",
            strIngredient2 = "Lime",
            strIngredient3 = "Sugar",
            strIngredient4 = "Mint",
            strIngredient5 = "Soda water",
            strIngredient6 = "Ice",
            strIngredient7 = "Mint sprig",
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
            strMeasure6 = "Fill",
            strMeasure7 = "Garnish",
            strMeasure8 = null,
            strMeasure9 = null,
            strMeasure10 = null,
            strMeasure11 = null,
            strMeasure12 = null,
            strMeasure13 = null,
            strMeasure14 = null,
            strMeasure15 = null,
            strImageSource = "https://commons.wikimedia.org/wiki/File:Mojito_001.jpg",
            strImageAttribution = "TheCocktailDB.com",
            strCreativeCommonsConfirmed = "Yes",
            dateModified = "2017-09-07 21:42:09",
            isFavorite = false
        )
        val response = CocktailDetailsResponse(drinks = listOf(cocktailDetails))

        coEvery { mockRepo.getCocktailDetails(cocktailId) } returns response

        // When
        viewModel.getDetails(cocktailId)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        coVerify { mockRepo.getCocktailDetails(cocktailId) }
        val result = viewModel.cocktailDetailsData.value
        assertNotNull(result)
        assertEquals("11007", result?.idDrink)
        assertEquals("Mojito", result?.strDrink)
        assertEquals("Cuban Mojito", result?.strDrinkAlternate)
        assertEquals("IBA,ContemporaryClassic,Summer", result?.strTags)
        assertEquals("https://www.youtube.com/watch?v=example", result?.strVideo)
        assertEquals("Cocktail", result?.strCategory)
        assertEquals("Contemporary Classics", result?.strIBA)
        assertEquals("Alcoholic", result?.strAlcoholic)
        assertEquals("Highball glass", result?.strGlass)
        assertTrue(result?.strInstructions?.contains("Muddle mint leaves") == true)
        assertTrue(result?.strInstructionsES?.contains("Machacar las hojas") == true)
        assertTrue(result?.strInstructionsDE?.contains("Minzblätter mit Zucker") == true)
        assertTrue(result?.strInstructionsFR?.contains("Écraser les feuilles") == true)
        assertTrue(result?.strInstructionsIT?.contains("Schiacciare le foglie") == true)
        assertTrue(result?.strInstructionsZH_HANS?.contains("将薄荷叶") == true)
        assertTrue(result?.strInstructionsZH_HANT?.contains("將薄荷葉") == true)
        assertEquals("https://www.thecocktaildb.com/images/media/drink/metwgh1606770327.jpg", result?.strDrinkThumb)
        assertEquals("White rum", result?.strIngredient1)
        assertEquals("Lime", result?.strIngredient2)
        assertEquals("Sugar", result?.strIngredient3)
        assertEquals("Mint", result?.strIngredient4)
        assertEquals("Soda water", result?.strIngredient5)
        assertEquals("Ice", result?.strIngredient6)
        assertEquals("Mint sprig", result?.strIngredient7)
        assertEquals("2-3 oz", result?.strMeasure1)
        assertEquals("Juice of 1/2", result?.strMeasure2)
        assertEquals("2 tsp", result?.strMeasure3)
        assertEquals("2-4", result?.strMeasure4)
        assertEquals("Top", result?.strMeasure5)
        assertEquals("Fill", result?.strMeasure6)
        assertEquals("Garnish", result?.strMeasure7)
        assertEquals("https://commons.wikimedia.org/wiki/File:Mojito_001.jpg", result?.strImageSource)
        assertEquals("TheCocktailDB.com", result?.strImageAttribution)
        assertEquals("Yes", result?.strCreativeCommonsConfirmed)
        assertEquals("2017-09-07 21:42:09", result?.dateModified)
        assertFalse(result?.isFavorite == true)
    }

    @Test
    fun `getDetails with multiple calls updates data correctly`() = runTest {
        // Given
        val cocktailId1 = "11007"
        val cocktailId2 = "11008"
        val cocktailDetails1 = CocktailDetails(
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
        val cocktailDetails2 = CocktailDetails(
            idDrink = "11008",
            strDrink = "Margarita",
            strDrinkAlternate = null,
            strTags = null,
            strVideo = null,
            strCategory = "Cocktail",
            strIBA = null,
            strAlcoholic = "Alcoholic",
            strGlass = "Cocktail glass",
            strInstructions = "Mix ingredients",
            strInstructionsES = null,
            strInstructionsDE = null,
            strInstructionsFR = null,
            strInstructionsIT = null,
            strInstructionsZH_HANS = null,
            strInstructionsZH_HANT = null,
            strDrinkThumb = "margarita.jpg",
            strIngredient1 = "Tequila",
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

        coEvery { mockRepo.getCocktailDetails(cocktailId1) } returns CocktailDetailsResponse(drinks = listOf(cocktailDetails1))
        coEvery { mockRepo.getCocktailDetails(cocktailId2) } returns CocktailDetailsResponse(drinks = listOf(cocktailDetails2))

        // When - First call
        viewModel.getDetails(cocktailId1)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - First call
        assertEquals("11007", viewModel.cocktailDetailsData.value?.idDrink)
        assertEquals("Mojito", viewModel.cocktailDetailsData.value?.strDrink)

        // When - Second call
        viewModel.getDetails(cocktailId2)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then - Second call
        assertEquals("11008", viewModel.cocktailDetailsData.value?.idDrink)
        assertEquals("Margarita", viewModel.cocktailDetailsData.value?.strDrink)
    }
}
