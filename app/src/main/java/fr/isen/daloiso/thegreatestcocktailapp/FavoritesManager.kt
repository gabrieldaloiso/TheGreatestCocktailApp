package fr.isen.daloiso.thegreatestcocktailapp

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesManager {
    private const val PREFS_NAME = "cocktail_favorites"
    private const val FAVORITES_KEY = "favorites_list"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun addFavorite(context: Context, drinkId: String, drinkName: String,  drinkImage: String) {
        val favorites = getFavorites(context).toMutableList()

        if (favorites.none { it.id == drinkId }) {
            favorites.add(FavoriteDrink(drinkId, drinkName, drinkImage))
            saveFavorites(context, favorites)
        }
    }
    fun removeFavorite(context: Context, drinkId: String) {
        val favorites = getFavorites(context).toMutableList()
        favorites.removeAll { it.id == drinkId }
        saveFavorites(context, favorites)
    }
    fun isFavorite(context: Context, drinkId: String) : Boolean {
        return getFavorites(context).any { it.id == drinkId }
    }
    fun getFavorites(context: Context): List<FavoriteDrink> {
        val prefs = getPrefs(context)
        val json = prefs.getString(FAVORITES_KEY, null) ?: return emptyList()

        return try {
            val type = object : TypeToken<List<FavoriteDrink>>() {}.type
            Gson().fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }
    private fun saveFavorites(context: Context, favorites: List<FavoriteDrink>) {
        val prefs = getPrefs(context)
        val json = Gson().toJson(favorites)
        prefs.edit().putString(FAVORITES_KEY, json).apply()
    }
    fun toggleFavorite(context: Context, drinkId: String, drinkName: String, drinkImage: String) {
        if (isFavorite(context, drinkId)) {
            removeFavorite(context, drinkId)
        } else {
            addFavorite(context, drinkId, drinkName, drinkImage)
        }
    }
}

data class FavoriteDrink(
    val id: String,
    val name: String,
    val image: String
)
