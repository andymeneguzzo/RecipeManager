package com.andy.recipemanager.data

data class Recipe(
    val id: Long = 0L,
    val name: String,
    val time: String,
    val difficulty: String,
    val iconResId: Int,
    val description: String = ""

)