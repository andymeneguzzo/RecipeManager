package com.andy.recipemanager.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class RecipeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crea la tabella delle ricette
        val createRecipesTableQuery = """
            CREATE TABLE ${RecipeContract.RecipeEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${RecipeContract.RecipeEntry.COLUMN_NAME} TEXT NOT NULL,
                ${RecipeContract.RecipeEntry.COLUMN_TIME} TEXT,
                ${RecipeContract.RecipeEntry.COLUMN_DIFFICULTY} TEXT,
                ${RecipeContract.RecipeEntry.COLUMN_ICON_RES_ID} INTEGER,
                ${RecipeContract.RecipeEntry.COLUMN_DESCRIPTION} TEXT
            );
        """.trimIndent()
        db.execSQL(createRecipesTableQuery)

        // Crea la tabella degli step
        val createStepsTableQuery = """
            CREATE TABLE ${StepsContract.StepEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${StepsContract.StepEntry.COLUMN_RECIPE_ID} INTEGER NOT NULL,
                ${StepsContract.StepEntry.COLUMN_DESCRIPTION} TEXT NOT NULL,
                ${StepsContract.StepEntry.COLUMN_TIMER} TEXT NOT NULL,
                FOREIGN KEY(${StepsContract.StepEntry.COLUMN_RECIPE_ID}) REFERENCES ${RecipeContract.RecipeEntry.TABLE_NAME}(${BaseColumns._ID}) ON DELETE CASCADE
            );
        """.trimIndent()
        db.execSQL(createStepsTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${RecipeContract.RecipeEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${StepsContract.StepEntry.TABLE_NAME}")
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

    fun updateRecipe(recipeId: Long, recipe: Recipe): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(RecipeContract.RecipeEntry.COLUMN_NAME, recipe.name)
            put(RecipeContract.RecipeEntry.COLUMN_TIME, recipe.time)
            put(RecipeContract.RecipeEntry.COLUMN_DIFFICULTY, recipe.difficulty)
            put(RecipeContract.RecipeEntry.COLUMN_ICON_RES_ID, recipe.iconResId)
            put(RecipeContract.RecipeEntry.COLUMN_DESCRIPTION, recipe.description)
        }
        return db.update(RecipeContract.RecipeEntry.TABLE_NAME, values, "${BaseColumns._ID} = ?", arrayOf(recipeId.toString()))
    }

    fun getRecipe(recipeId: Long): Recipe? {
        val db = this.readableDatabase
        val cursor: Cursor = db.query(
            RecipeContract.RecipeEntry.TABLE_NAME,
            null,
            "${BaseColumns._ID} = ?",
            arrayOf(recipeId.toString()),
            null,
            null,
            null
        )
        var recipe: Recipe? = null
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_NAME))
            val time = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_TIME))
            val difficulty = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_DIFFICULTY))
            val iconResId = cursor.getInt(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_ICON_RES_ID))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(RecipeContract.RecipeEntry.COLUMN_DESCRIPTION))
            recipe = Recipe(name, time, difficulty, iconResId, description)
        }
        cursor.close()
        return recipe
    }

    fun insertStep(recipeId: Long, step: Step): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(StepsContract.StepEntry.COLUMN_RECIPE_ID, recipeId)
            put(StepsContract.StepEntry.COLUMN_DESCRIPTION, step.description)
            put(StepsContract.StepEntry.COLUMN_TIMER, step.timer)
        }
        return db.insert(StepsContract.StepEntry.TABLE_NAME, null, values)
    }

    companion object {
        private const val DATABASE_NAME = "recipes.db"
        private const val DATABASE_VERSION = 2  // aggiornato per includere la tabella degli step
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

object StepsContract {
    object StepEntry : BaseColumns {
        const val TABLE_NAME = "steps"
        const val COLUMN_RECIPE_ID = "recipe_id"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_TIMER = "timer"
    }
}