package com.example.funciona.ui.theme

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.tsgapp.Producto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesRepository {
    private const val PREFS_NAME = "favorites_prefs"
    private const val KEY_FAVORITES = "favorites_list"
    private val gson = Gson()

    fun saveFavorites(context: Context, favorites: List<Producto>) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(favorites)
        sharedPreferences.edit {
            putString(KEY_FAVORITES, json)
        }
    }

    fun loadFavorites(context: Context): List<Producto> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_FAVORITES, null)
        return try {
            json?.let {
                val type = object : TypeToken<List<Producto>>() {}.type
                gson.fromJson<List<Producto>>(it, type) ?: emptyList()
            } ?: emptyList()
        } catch (e: Exception) {
            Log.e("FavoritesRepo", "Error parsing favorites", e)
            emptyList()
        }
    }

    fun toggleFavorite(context: Context, producto: Producto) {
        val currentFavorites = loadFavorites(context).toMutableList()
        if (currentFavorites.contains(producto)) {
            currentFavorites.remove(producto)
        } else {
            currentFavorites.add(producto)
        }
        saveFavorites(context, currentFavorites)
    }
}