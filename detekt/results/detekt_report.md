# detekt

## Metrics

* 330 number of properties

* 334 number of functions

* 112 number of classes

* 15 number of packages

* 85 number of kt files

## Complexity Report

* 4,535 lines of code (loc)

* 3,506 source lines of code (sloc)

* 2,320 logical lines of code (lloc)

* 290 comment lines of code (cloc)

* 462 cyclomatic complexity (mcc)

* 179 cognitive complexity

* 8 number of total code smells

* 8% comment source ratio

* 199 mcc per 1,000 lloc

* 3 code smells per 1,000 lloc

## Findings (8)

### complexity, LongParameterList (1)

The more parameters a function has the more complex it is. Long parameter lists are often used to control complex algorithms and violate the Single Responsibility Principle. Prefer functions with short parameter lists.

[Documentation](https://detekt.dev/docs/rules/complexity#longparameterlist)

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/main/java/com/example/cocktailsdbapp/ui/BaseAdapter.kt:45:29
```
The constructor(oldList: List<T>, newList: List<T>, areItemsTheSame: (oldItem: T, newItem: T) -> Boolean, areContentsTheSame: (oldItem: T, newItem: T) -> Boolean) has too many parameters. The current threshold is set to 3.
```
```kotlin
42 
43     abstract fun getDiffCallback(oldList: List<T>, newList: List<T>): DiffUtil.Callback
44 
45     inner class DiffCallback(
!!                             ^ error
46         private val oldList: List<T>,
47         private val newList: List<T>,
48         private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,

```

### complexity, TooManyFunctions (7)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/main/java/com/example/cocktailsdbapp/MainActivity.kt:20:7
```
Class 'MainActivity' with '12' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
17 import dagger.hilt.android.AndroidEntryPoint
18 
19 @AndroidEntryPoint
20 class MainActivity : AppCompatActivity(), Communicator {
!!       ^ error
21 
22     private lateinit var navController: NavController
23     private var searchIcon: MenuItem? = null

```

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/main/java/com/example/cocktailsdbapp/network/CocktailApiService.kt:13:11
```
Interface 'CocktailApiService' with '11' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
10 import retrofit2.http.GET
11 import retrofit2.http.Query
12 
13 interface CocktailApiService {
!!           ^ error
14 
15     @GET(Constants.QUERY_FILTER)
16     suspend fun getCocktailsByAlcoholContent(@Query("a") type: String = Constants.QUERY_PARAM_LIST): CocktailResponse

```

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/main/java/com/example/cocktailsdbapp/repository/CocktailsRepo.kt:11:11
```
Interface 'CocktailsRepo' with '15' functions detected. Defined threshold inside interfaces is set to '11'
```
```kotlin
8  import com.example.cocktailsdbapp.model.GlassesResponse
9  import com.example.cocktailsdbapp.model.IngredientListResponse
10 
11 interface CocktailsRepo {
!!           ^ error
12 
13     suspend fun getCocktailsByAlcoholContent(alcoholContent: String): CocktailResponse
14     suspend fun getCocktailsByGlass(glass: String): CocktailResponse

```

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/main/java/com/example/cocktailsdbapp/repository/CocktailsRepoImpl.kt:15:7
```
Class 'CocktailsRepoImpl' with '15' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
12 import com.example.cocktailsdbapp.network.CocktailApiService
13 import com.example.cocktailsdbapp.utils.toCocktailsResponse
14 
15 class CocktailsRepoImpl(private val serviceApi: CocktailApiService, private val cocktailDao: CocktailDao): CocktailsRepo {
!!       ^ error
16     override suspend fun getCocktailsByAlcoholContent(alcoholContent: String): CocktailResponse {
17         return serviceApi.getCocktailsByAlcoholContent(alcoholContent)
18     }

```

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/main/java/com/example/cocktailsdbapp/ui/search/SearchFragment.kt:21:7
```
Class 'SearchFragment' with '11' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
18 import dagger.hilt.android.AndroidEntryPoint
19 
20 @AndroidEntryPoint
21 class SearchFragment: BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate), CocktailAdapter.OnFavoriteClickListener, CocktailAdapter.OnItemClickListener {
!!       ^ error
22 
23     private val searchViewModel: SearchViewModel by viewModels()
24 

```

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/test/java/com/example/cocktailsdbapp/unit_tests/BasicTest.kt:13:7
```
Class 'BasicTest' with '12' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
10  * Basic unit tests for CocktailsDbApp
11  * Tests simple functionality that doesn't require complex dependencies
12  */
13 class BasicTest {
!!       ^ error
14 
15     @Test
16     fun `test Cocktail data class basic functionality`() {

```

* /Users/stefanvucan/Desktop/matf/SoftwareVerificationProject/2024_Analysis_CocktailsDbApp/CocktailsDbApp/app/src/test/java/com/example/cocktailsdbapp/unit_tests/ComprehensiveTestSuite.kt:24:7
```
Class 'ComprehensiveTestSuite' with '27' functions detected. Defined threshold inside classes is set to '11'
```
```kotlin
21  * Tests all major components: Models, Extensions, and Basic Logic
22  */
23 @ExperimentalCoroutinesApi
24 class ComprehensiveTestSuite {
!!       ^ error
25 
26     private lateinit var testDispatcher: TestDispatcher
27 

```

generated with [detekt version 1.23.8](https://detekt.dev/) on 2025-10-19 16:08:33 UTC
