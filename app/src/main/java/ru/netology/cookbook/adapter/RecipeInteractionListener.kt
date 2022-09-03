package ru.netology.cookbook.adapter

import ru.netology.cookbook.Recipe

interface RecipeInteractionListener {
    fun onLikeClicked (recipe: Recipe)
    fun onRecipeClicked (recipe: Recipe)
    fun onRemoveClicked (recipeId: Int)
    fun onEditClicked (recipeId: Int)
    fun onSaveButtonClicked(recipe: Recipe)
}