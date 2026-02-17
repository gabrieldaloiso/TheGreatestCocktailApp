package fr.isen.daloiso.thegreatestcocktailapp.network

import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.CategoryListResponse
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.DrinkFilterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /**
     * Random : random.php
     */
    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse

    /**
     * List of categories : list.php?c=list
     */
    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryListResponse

    /**
     * List of drinks for a category : filter.php?c=YOUR_CATEGORY
     */
    @GET("filter.php")
    suspend fun getDrinksByCategory(
        @Query("c") category: String
    ): DrinkFilterResponse

    /**
     * Detail of a drink : lookup.php?i=YOUR_DRINK_IDENTIFIER
     */
    @GET("lookup.php")
    suspend fun getDrinkDetail(
        @Query("i") drinkId: String
    ): CocktailResponse
}