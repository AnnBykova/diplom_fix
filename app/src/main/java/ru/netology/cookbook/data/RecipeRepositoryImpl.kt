package ru.netology.cookbook.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps
import ru.netology.cookbook.db.RecipeDao
import ru.netology.cookbook.db.toEntity
import ru.netology.cookbook.db.toModel


class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override var data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override var dataSteps = dao.getAllSteps().map { entities ->
        entities.map { it.toModel() }
    }

    override fun like(recipeId: Long) {
        dao.likedById(recipeId)
    }

    override fun delete(id: Long) {
        dao.removeById(id)
        dao.removeStepsByRecipeId(id)
    }

    override fun deleteStep(stepId: Long) {
        dao.removeStepById(stepId)
    }

    override fun deleteStepsByRecipeId(id: Long) {
        dao.removeStepsByRecipeId(id)
    }

    override fun save(recipe: Recipe) {
        dao.insert(recipe.toEntity())
    }

    override fun update(recipe: Recipe) {
        dao.updateRecipeById(recipe.toEntity())
    }

    override fun searchDataBase(query: String): LiveData<List<Recipe>> {
        return dao.searchDataBase(query).map { entities ->
            entities.map { it.toModel() }
        }
    }

    override fun saveRecipe(recipe: Recipe) {
        val id: Long = dao.insert(recipe.toEntity())
        val steps = recipe.cooking
        dao.removeStepsByRecipeId(-1L)
        dao.insertSteps(steps.map { it.copy(recipeId = id) }.map { it.toEntity() })
    }

    override fun insertNewStep(step: Steps) {
        dao.insertNewStep(step.toEntity())
    }

    override fun getStepsByRecipeId(recipeId: Long): List<Steps> {
        return dao.getStepsByRecipeId(recipeId).map { entities ->
            entities.toModel()
        }
    }

    override fun getLastStepOrder(recipeId: Long): Int{
        return dao.getLastStepOrder(recipeId)
    }


}