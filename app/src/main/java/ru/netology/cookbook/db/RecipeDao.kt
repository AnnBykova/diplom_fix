package ru.netology.cookbook.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query



@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id= :id")
    fun getById(id:Long): RecipeEntity

    @Insert
    fun insert(recipe: RecipeEntity) : Long

    @Query("""
        UPDATE recipes SET
        isLiked = CASE WHEN isLiked THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likedById(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

    @Query("DELETE FROM steps WHERE stepId = :id")
    fun removeStepById(id: Long)

    @Query("DELETE FROM steps WHERE recipeId = :id")
    fun removeStepsByRecipeId(id: Long)

    fun updateRecipeById(recipe: RecipeEntity){
        removeById(recipe.id)
        insert(recipe)
    }

    @Query("SELECT * FROM recipes WHERE name LIKE  :query")
    fun searchDataBase (query: String) : LiveData<List<RecipeEntity>>

    @Insert
    fun insertSteps (steps: List<StepsEntity>)
    @Insert
    fun insertNewStep (step: StepsEntity)

    @Query("SELECT * FROM steps  WHERE recipeId = :recipeId ORDER BY stepOrder DESC")
    fun getStepsByRecipeId(recipeId:Long):List<StepsEntity>

    @Query("SELECT * FROM steps ORDER BY stepOrder DESC")
    fun getAllSteps(): LiveData<List<StepsEntity>>

    @Query("SELECT MAX (stepOrder) FROM steps  WHERE recipeId = :recipeId")
    fun getLastStepOrder(recipeId:Long): Int












}