package ru.netology.cookbook.adapter

import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps

interface SingleRecipeInteractionListener {
    fun onLikeClicked (recipe: Recipe)
    fun onRemoveClicked (recipeId: Long)
    fun onEditClicked (recipeId: Long)
    fun onSaveButtonClicked(recipe: Recipe)
    fun onStepTextClicked(text: String)
    fun onAddStepClicked(stepNumber: Int, text: String)
    fun onAddStepEditClicked(step : Steps)
    fun onStepDeleteClicked(step: Steps)
}