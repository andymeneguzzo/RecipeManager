package com.andy.recipemanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class RecipeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE ${RecipeContract.RecipeEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${RecipeContract.RecipeEntry.COLUMN_NAME} TEXT NOT NULL,
                ${RecipeContract.RecipeEntry.COLUMN_TIME} TEXT,
                ${RecipeContract.RecipeEntry.COLUMN_DIFFICULTY} TEXT,
                ${RecipeContract.RecipeEntry.COLUMN_ICON_RES_ID} INTEGER,
                ${RecipeContract.RecipeEntry.COLUMN_DESCRIPTION} TEXT
            );
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${RecipeContract.RecipeEntry.TABLE_NAME}")
        onCreate(db)
    }

    fun insertRecipe(recipe: Recipe): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(RecipeContract.RecipeEntry.COLUMN_NAME, recipe.name)
            put(RecipeContract.RecipeEntry.COLUMN_TIME, recipe.time)
            put(RecipeContract.RecipeEntry.COLUMN_DIFFICULTY, recipe.difficulty)
            put(RecipeContract.RecipeEntry.COLUMN_ICON_RES_ID, recipe.iconResId)
            put(RecipeContract.RecipeEntry.COLUMN_DESCRIPTION, recipe.description)
        }
        return db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, values)
    }

    companion object {
        private const val DATABASE_NAME = "recipes.db"
        private const val DATABASE_VERSION = 1
    }
}

object RecipeContract {
    object RecipeEntry : BaseColumns {
        const val TABLE_NAME = "recipes"
        const val COLUMN_NAME = "name"
        const val COLUMN_TIME = "time"
        const val COLUMN_DIFFICULTY = "difficulty"
        const val COLUMN_ICON_RES_ID = "iconResId"
        const val COLUMN_DESCRIPTION = "description"
    }
}