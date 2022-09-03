package ru.netology.cookbook.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.cookbook.Category

@Entity(tableName = "recipes")
class RecipeEntity(

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "components")
    val components: String,

    @ColumnInfo(name = "cooking")
    val cooking: String,

    @ColumnInfo(name = "picture")
    val picture: String,

    @ColumnInfo(name = "category")
    val category: Category,

    @ColumnInfo(name = "isLiked")
    val isLiked: Boolean = false
)