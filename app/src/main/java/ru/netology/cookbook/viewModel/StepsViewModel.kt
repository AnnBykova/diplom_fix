package ru.netology.cookbook.viewModel

import android.app.Application
import android.widget.PopupMenu
import androidx.lifecycle.AndroidViewModel
import ru.netology.cookbook.R
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.adapter.RecipeInteractionListener
import ru.netology.cookbook.data.RecipeRepository
import ru.netology.cookbook.data.RecipeRepositoryImpl
import ru.netology.cookbook.db.AppDb
import ru.netology.cookbook.util.SingleLiveEvent

class StepsViewModel (application: Application
): AndroidViewModel(application), RecipeInteractionListener {
     private val repository : RecipeRepository =
        RecipeRepositoryImpl (
        dao = AppDb.getInstance(
            context = application
        ).recipeDao
            )
    val data by repository::data

    val showRecipe= SingleLiveEvent<Int>()


    override fun onLikeClicked(recipe: Recipe) {
        repository.like(recipe.id)
    }

    override fun onRecipeClicked(recipe: Recipe) {
        showRecipe.value = recipe.id
    }

    override fun onRemoveClicked(recipeId: Int) {
        TODO("Not yet implemented")
    }

    override fun onEditClicked(recipeId: Int) {
        TODO("Not yet implemented")
    }

    override fun onSaveButtonClicked(recipe: Recipe) {
        repository.save(recipe)
    }
}