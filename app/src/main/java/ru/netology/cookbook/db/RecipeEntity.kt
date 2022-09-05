package ru.netology.cookbook.db

import androidx.room.*
import ru.netology.cookbook.Category


@Entity(tableName = "recipes")
class RecipeEntity(

    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "components")
    val components: String,

    @ColumnInfo(name = "picture")
    val picture: String,

    @ColumnInfo(name = "category")
    val category: Category,

    @ColumnInfo(name = "isLiked")
    val isLiked: Boolean = false
)