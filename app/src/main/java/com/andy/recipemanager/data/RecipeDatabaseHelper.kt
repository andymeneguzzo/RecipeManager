package com.andy.recipemanager.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class RecipeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crea la tabella "recipes" (puoi chiamarla come vuoi, es. "recipe_table")
        val createTableQuery = """
            CREATE TABLE ${RecipeContract.RecipeEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${RecipeContract.RecipeEntry.COLUMN_TITLE} TEXT NOT NULL,
                ${RecipeContract.RecipeEntry.COLUMN_TIME} TEXT,
                ${RecipeContract.RecipeEntry.COLUMN_DIFFICULTY} TEXT,
                ${RecipeContract.RecipeEntry.COLUMN_DESCRIPTION} TEXT,
                ${RecipeContract.RecipeEntry.COLUMN_ICON} TEXT
            );
        """.trimIndent()

        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Se cambi versione, droppa la tabella e ricreala (metodo semplice, per sviluppo)
        db.execSQL("DROP TABLE IF EXISTS ${RecipeContract.RecipeEntry.TABLE_NAME}")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "recipes.db"
        const val DATABASE_VERSION = 1
    }

    // INSERT METHOD

    fun insertRecipe(recipe: Recipe): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", recipe.name)
            put("time", recipe.time)
            put("difficulty", recipe.difficulty)
            put("description", "") // se la tua data class ha un campo description
            put("icon", recipe.iconResId) // ecco l'Int
        }
        return db.insert("recipes", null, values)
    }
}

/**
 * Oggetto che definisce costanti per la tabella "recipes"
 * (struttura simile ai contratti di provider)
 */
object RecipeContract {
    object RecipeEntry : BaseColumns {
        const val TABLE_NAME = "recipes"
        const val COLUMN_TITLE = "title"
        const val COLUMN_TIME = "time"
        const val COLUMN_DIFFICULTY = "difficulty"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ICON = "icon" // per salvare il nome dell'icona o eventuale path
    }
}