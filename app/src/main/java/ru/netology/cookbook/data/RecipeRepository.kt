package ru.netology.cookbook.data

import androidx.lifecycle.LiveData
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps

interface RecipeRepository {
    val data: LiveData<List<Recipe>>

    val dataSteps: LiveData<List<Steps>>


    fun like(recipeId: Long)

    fun delete(recipeId: Long)

    fun save(recipe: Recipe)

    fun update(recipe: Recipe)

    fun searchDataBase(query: String): LiveData<List<Recipe>>

    fun saveRecipe(recipe: Recipe)

    fun insertNewStep(step: Steps)

    fun deleteStepsByRecipeId(recipeId: Long)

    fun deleteStep(stepId: Long)

    fun getStepsByRecipeId(recipeId: Long): List<Steps>

    fun getLastStepOrder(recipeId: Long): Int
}