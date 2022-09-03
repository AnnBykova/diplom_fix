package ru.netology.cookbook.viewModel

import android.app.Application
import android.net.Uri
import android.widget.PopupMenu
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import ru.netology.cookbook.R
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.adapter.RecipeInteractionListener
import ru.netology.cookbook.data.RecipeRepository
import ru.netology.cookbook.data.RecipeRepositoryImpl
import ru.netology.cookbook.db.AppDb
import ru.netology.cookbook.util.SingleLiveEvent

class RecipeViewModel (application: Application
): AndroidViewModel(application), RecipeInteractionListener {
     private val repository : RecipeRepository =
        RecipeRepositoryImpl (
        dao = AppDb.getInstance(
            context = application
        ).recipeDao
            )
    val data by repository::data
    val dataSearch by repository::data

    val showRecipe= SingleLiveEvent<Int>()
    val editRecipe= SingleLiveEvent<Int>()
    val addPhotoClicked= SingleLiveEvent<String>()
    val editPhotoClicked= SingleLiveEvent<String>()


    override fun onLikeClicked(recipe: Recipe) {
        repository.like(recipe.id)
    }

    override fun onRecipeClicked(recipe: Recipe) {
        showRecipe.value = recipe.id
    }

    override fun onRemoveClicked(recipeId: Int) {
        repository.delete(recipeId)
    }

    override fun onEditClicked(recipeId: Int) {
        editRecipe.value = recipeId
    }

    override fun onSaveButtonClicked(recipe: Recipe) {
        repository.save(recipe)
        addPhotoClicked.value="no"
    }

    fun onUpdateButtonClicked(recipe: Recipe) {
        repository.update(recipe)
    }

    fun searchDataBase (query : String): LiveData<List<Recipe>> {
        return repository.searchDataBase(query)
    }

}