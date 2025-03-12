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

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var homeButton: ImageButton
    private lateinit var forwardButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var ibRecipeIcon: ImageButton

    private lateinit var etTitle: TextInputEditText
    private lateinit var etTime: TextInputEditText
    private lateinit var etDifficulty: TextInputEditText
    private lateinit var etDescription: TextInputEditText

    // Icona di default
    private var selectedIconResId: Int = R.drawable.ic_recipe_temp

    // Launcher per IconsListActivity
    private lateinit var chooseIconLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        homeButton = findViewById(R.id.homeButton)
        forwardButton = findViewById(R.id.forwardButton)
        settingsButton = findViewById(R.id.settingsButton)
        ibRecipeIcon = findViewById(R.id.ibRecipeIcon)

        etTitle = findViewById(R.id.etTitle)
        etTime = findViewById(R.id.etTime)
        etDifficulty = findViewById(R.id.etDifficulty)
        etDescription = findViewById(R.id.etDescription)

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

        forwardButton.setOnClickListener {
            saveRecipeToDatabase()
        }

        ibRecipeIcon.setOnClickListener {
            val intent = Intent(this, IconsListActivity::class.java)
            chooseIconLauncher.launch(intent)
        }
    }

    private fun saveRecipeToDatabase() {
        val title = etTitle.text?.toString()?.trim().orEmpty()
        val time = etTime.text?.toString()?.trim().orEmpty()
        val difficulty = etDifficulty.text?.toString()?.trim().orEmpty()
        val description = etDescription.text?.toString()?.trim().orEmpty()

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a recipe title", Toast.LENGTH_SHORT).show()
            return
        }

        val recipe = Recipe(name = title, time = time, difficulty = difficulty, iconResId = selectedIconResId, description = description)
        val dbHelper = RecipeDatabaseHelper(this)
        val rowId = dbHelper.insertRecipe(recipe)
        if (rowId == -1L) {
            Toast.makeText(this, "Error saving recipe.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Recipe saved (ID: $rowId).", Toast.LENGTH_SHORT).show()
            // Passa l'ID della ricetta a AddStepsActivity
            val intent = Intent(this, AddStepsActivity::class.java)
            intent.putExtra("RECIPE_ID", rowId)
            startActivity(intent)
        }
    }
}