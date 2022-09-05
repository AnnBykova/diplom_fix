package ru.netology.cookbook.adapter

import ru.netology.cookbook.Recipe

interface RecipeListInteractionListener {
    fun onRecipeClicked (recipe: Recipe)
   }