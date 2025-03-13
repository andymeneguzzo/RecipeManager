package com.andy.recipemanager.activities


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Legge il valore dal SharedPreferences e imposta il tema prima di super.onCreate()
        val sharedPreferences = getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE)
        val darkModeEnabled = sharedPreferences.getBoolean(SettingsActivity.KEY_DARK_MODE, false)
        if (darkModeEnabled) {
            setTheme(R.style.Theme_RecipeManager_Dark)
        } else {
            setTheme(R.style.Theme_RecipeManager)
        }
        super.onCreate(savedInstanceState)
    }
}