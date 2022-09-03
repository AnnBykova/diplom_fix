package ru.netology.cookbook.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import ru.netology.cookbook.Category
import ru.netology.cookbook.Recipe

@Entity(tableName = "steps", foreignKeys = [ForeignKey(
    entity = RecipeEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("userId"),
    onDelete = CASCADE
)]
)
class StepsEntity (

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "stepId")
    val stepId : Long,

    @ColumnInfo(name = "stepOrder")
    val stepOrder : Int,

    @ColumnInfo(name = "stepText")
    val stepText : String,

    @ColumnInfo(name = "recipeId")
    val recipeId : Long,

    @ColumnInfo(name = "stepPicture")
    val stepPicture: String ="no",

)