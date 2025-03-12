package com.andy.recipemanager.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R
import com.andy.recipemanager.data.Recipe
import com.andy.recipemanager.data.RecipeDatabaseHelper
import com.google.android.material.textfield.TextInputEditText

class EditRecipeActivity : AppCompatActivity() {

    private lateinit var homeButton: ImageButton
    private lateinit var editButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var ibRecipeIcon: ImageButton

    private lateinit var etTitle: TextInputEditText
    private lateinit var etTime: TextInputEditText
    private lateinit var etDifficulty: TextInputEditText
    private lateinit var etDescription: TextInputEditText

    private var selectedIconResId: Int = R.drawable.ic_recipe_temp

    private lateinit var chooseIconLauncher: ActivityResultLauncher<Intent>

    private var recipeId: Long = -1L
    private lateinit var dbHelper: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_recipe)

        homeButton = findViewById(R.id.homeButton)
        editButton = findViewById(R.id.editButton)
        settingsButton = findViewById(R.id.settingsButton)
        ibRecipeIcon = findViewById(R.id.ibRecipeIcon)

        etTitle = findViewById(R.id.etTitle)
        etTime = findViewById(R.id.etTime)
        etDifficulty = findViewById(R.id.etDifficulty)
        etDescription = findViewById(R.id.etDescription)

        dbHelper = RecipeDatabaseHelper(this)
        recipeId = intent.getLongExtra("RECIPE_ID", -1L)
        if (recipeId == -1L) {
            Toast.makeText(this, "Recipe ID not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Carica la ricetta dal database
        val recipe = dbHelper.getRecipe(recipeId)
        if (recipe != null) {
            etTitle.setText(recipe.name)
            etTime.setText(recipe.time)
            etDifficulty.setText(recipe.difficulty)
            etDescription.setText(recipe.description)
            selectedIconResId = recipe.iconResId
            ibRecipeIcon.setImageResource(selectedIconResId)
        } else {
            Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        chooseIconLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                if (data != null && data.hasExtra("ICON_RES_ID")) {
                    val iconId = data.getIntExtra("ICON_RES_ID", -1)
                    if (iconId != -1) {
                        selectedIconResId = iconId
                        ibRecipeIcon.setImageResource(iconId)
                    } else {
                        Toast.makeText(this, "Icon not found.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        editButton.setOnClickListener {
            updateRecipe()
        }

        ibRecipeIcon.setOnClickListener {
            val intent = Intent(this, IconsListActivity::class.java)
            chooseIconLauncher.launch(intent)
        }
    }

    private fun updateRecipe() {
        val title = etTitle.text?.toString()?.trim().orEmpty()
        val time = etTime.text?.toString()?.trim().orEmpty()
        val difficulty = etDifficulty.text?.toString()?.trim().orEmpty()
        val description = etDescription.text?.toString()?.trim().orEmpty()

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a recipe title", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedRecipe = Recipe(name = title, time = time, difficulty = difficulty, iconResId = selectedIconResId, description = description)
        val rowsAffected = dbHelper.updateRecipe(recipeId, updatedRecipe)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Recipe updated.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Error updating recipe.", Toast.LENGTH_SHORT).show()
        }
    }
}