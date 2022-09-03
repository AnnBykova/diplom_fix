package ru.netology.cookbook.data

import androidx.lifecycle.LiveData
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps

interface RecipeRepository {
    val data: LiveData<List<Recipe>>

    val dataSteps: LiveData<List<Steps>>

    fun like(recipeId: Int)

    fun showFavorites()

    fun delete (recipeId: Int)

    fun save (recipe: Recipe)

    fun update (recipe: Recipe)

    fun searchDataBase (query: String) : LiveData <List<Recipe>>

    fun saveRecipe (recipe: Recipe, steps: List<Steps>)





    companion object{
        const val NEW_COOK_ID = 0
    }
}