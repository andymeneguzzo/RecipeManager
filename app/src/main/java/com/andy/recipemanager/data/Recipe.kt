package com.andy.recipemanager.data

data class Recipe(
    val name: String,
    val time: String,
    val difficulty: String,
    val iconResId: Int,
    val description: String = ""
)