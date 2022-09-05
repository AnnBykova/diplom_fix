package ru.netology.cookbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.adapter.RecipeListInteractionListener
import ru.netology.cookbook.data.RecipeRepository
import ru.netology.cookbook.data.RecipeRepositoryImpl
import ru.netology.cookbook.db.AppDb
import ru.netology.cookbook.util.SingleLiveEvent

class RecipeListViewModel(
    application: Application
) : AndroidViewModel(application), RecipeListInteractionListener {
    private val repository: RecipeRepository =
        RecipeRepositoryImpl(
            dao = AppDb.getInstance(
                context = application
            ).recipeDao
        )
    val data by repository::data

    val showRecipe = SingleLiveEvent<Long>()

    override fun onRecipeClicked(recipe: Recipe) {
        showRecipe.value = recipe.id
    }

    fun searchDataBase(query: String): LiveData<List<Recipe>> {
        return repository.searchDataBase(query)
    }

}