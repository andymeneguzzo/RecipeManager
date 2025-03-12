package com.andy.recipemanager.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R
import com.andy.recipemanager.data.Recipe
import com.andy.recipemanager.data.RecipeDatabaseHelper
import com.google.android.material.textfield.TextInputEditText

class AddRecipeActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_CHOOSE_ICON = 100
    }

    private lateinit var forwardButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var ibRecipeIcon: ImageButton

    // Campi testuali
    private lateinit var etTitle: TextInputEditText
    private lateinit var etTime: TextInputEditText
    private lateinit var etDifficulty: TextInputEditText
    private lateinit var etDescription: TextInputEditText

    // Variabile per salvare l’icona selezionata (come risorsa ID, es. R.drawable.xxx)
    private var selectedIconResId: Int = R.drawable.ic_recipe_temp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        // Inizializza i view reference
        homeButton = findViewById(R.id.homeButton)
        forwardButton = findViewById(R.id.forwardButton)
        settingsButton = findViewById(R.id.settingsButton)
        ibRecipeIcon = findViewById(R.id.ibRecipeIcon)

        etTitle = findViewById(R.id.etTitle)
        etTime = findViewById(R.id.etTime)
        etDifficulty = findViewById(R.id.etDifficulty)
        etDescription = findViewById(R.id.etDescription)

        forwardButton.setOnClickListener {
            saveRecipeToDatabase()
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Avvio di IconsListActivity per scegliere l’icona
        ibRecipeIcon.setOnClickListener {
            val intent = Intent(this, IconsListActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_ICON)
        }
    }

    // Leggi i dati e salva nel DB
    private fun saveRecipeToDatabase() {
        val title = etTitle.text?.toString().orEmpty()
        val time = etTime.text?.toString().orEmpty()
        val difficulty = etDifficulty.text?.toString().orEmpty()
        val description = etDescription.text?.toString().orEmpty()

        if (title.isBlank()) {
            Toast.makeText(this, "Please enter a recipe title", Toast.LENGTH_SHORT).show()
            return
        }

        val newRecipe = Recipe(
            name = title,
            time = time,
            difficulty = difficulty,
            iconResId = selectedIconResId
        )

        val dbHelper = RecipeDatabaseHelper(this)
        val rowId = dbHelper.insertRecipe(newRecipe)

        if (rowId == -1L) {
            Toast.makeText(this, "Error saving recipe.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Recipe saved (ID: $rowId).", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AddStepsActivity::class.java)
            startActivity(intent)
        }
    }

    // Riceviamo il risultato da IconsListActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CHOOSE_ICON && resultCode == Activity.RESULT_OK) {
            val iconId = data?.getIntExtra("ICON_RES_ID", -1) ?: -1
            if (iconId != -1) {
                selectedIconResId = iconId
                // Aggiorna immediatamente l’immagine mostrata nel tuo ImageButton
                ibRecipeIcon.setImageResource(iconId)
            }
        }
    }
}