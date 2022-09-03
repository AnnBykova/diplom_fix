package ru.netology.cookbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.cookbook.Category
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps
import ru.netology.cookbook.db.RecipeDao
import ru.netology.cookbook.db.toEntity
import ru.netology.cookbook.db.toModel
import java.util.*

class RecipeRepositoryImpl(
    private val dao: RecipeDao
) : RecipeRepository {

    override var data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override var dataSteps = dao.getAllSteps().map { entities ->
        entities.map { it.toModel() }
    }

    override fun like(recipeId: Int) {
        dao.likedById(recipeId)
    }

    override fun showFavorites() {
        TODO("Not yet implemented")
    }

    override fun delete(id: Int) {
        dao.removeById(id)
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

    override fun saveRecipe(recipe: Recipe, steps: List<Steps>) {
        val id : Long = dao.insert(recipe.toEntity())
       dao.insertSteps(steps.map { it.copy(recipeId = id) }
           .map{it.toEntity()})






    }

}