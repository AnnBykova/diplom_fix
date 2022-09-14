package ru.netology.cookbook.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps
import ru.netology.cookbook.adapter.SingleRecipeInteractionListener
import ru.netology.cookbook.adapter.StepsAdapter.Companion.stepNumber
import ru.netology.cookbook.data.RecipeRepository
import ru.netology.cookbook.data.RecipeRepositoryImpl
import ru.netology.cookbook.db.AppDb
import ru.netology.cookbook.util.SingleLiveEvent

class SingleRecipeViewModel(
    application: Application
) : AndroidViewModel(application), SingleRecipeInteractionListener {
    private val repository: RecipeRepository =
        RecipeRepositoryImpl(
            dao = AppDb.getInstance(
                context = application
            ).recipeDao
        )
    val data by repository::data
    val newRecipeSteps by repository::dataSteps


    val stepTextAdded = SingleLiveEvent<String>()
    val editRecipe = SingleLiveEvent<Long>()
    val addPhotoClicked = SingleLiveEvent<String>()
    val editPhotoClicked = SingleLiveEvent<String>()


    override fun onLikeClicked(recipe: Recipe) {
        repository.like(recipe.id)
    }

    override fun onRemoveClicked(recipeId: Long) {
        repository.delete(recipeId)
    }

    override fun onEditClicked(recipeId: Long) {
        editRecipe.value = recipeId
    }

    override fun onSaveButtonClicked(recipe: Recipe) {
        repository.saveRecipe(recipe)
        addPhotoClicked.value = "no"
        stepNumber=1
    }

    override fun onStepTextClicked(text: String) {
        stepTextAdded.value = text
    }

    override fun onAddStepClicked(stepNumber: Int, text: String) {
        val newStep = Steps(0L, stepNumber, text, -1L)
        repository.insertNewStep(newStep)
    }

    override fun onAddStepEditClicked(step: Steps) {
        repository.insertNewStep(step)
    }

    override fun onStepDeleteClicked(step: Steps) {
        repository.deleteStep(step.stepId)
    }

    fun onUpdateButtonClicked(recipe: Recipe) {
        repository.update(recipe)
    }

    fun clearSteps(recipeId: Long) {
        repository.deleteStepsByRecipeId(recipeId)
        stepNumber=1
    }

    fun getStepsByRecipeId(recipeId: Long): List<Steps> {
        return repository.getStepsByRecipeId(recipeId)
    }

    fun getLastStepOrder (recipeId: Long): Int {
        return repository.getLastStepOrder(recipeId)
    }

}