package ru.netology.cookbook.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity) : Long

    @Query("""
        UPDATE recipes SET
        isLiked = CASE WHEN isLiked THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun likedById(id: Int)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Int)

    fun updateRecipeById(recipe: RecipeEntity){
        removeById(recipe.id)
        insert(recipe)
    }

    @Query("SELECT * FROM recipes WHERE name LIKE  :query")
    fun searchDataBase (query: String) : LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM steps  ORDER BY stepOrder DESC")
    fun getAllSteps(): LiveData<List<StepsEntity>>

    @Insert
    fun insertSteps (steps: List<StepsEntity>)

    fun insertRecipe(recipe: RecipeEntity){
        fun insert(recipe:RecipeEntity) {}
    }


}