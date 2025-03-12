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

    private lateinit var homeButton: ImageButton
    private lateinit var forwardButton: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var ibRecipeIcon: ImageButton

    private lateinit var etTitle: TextInputEditText
    private lateinit var etTime: TextInputEditText
    private lateinit var etDifficulty: TextInputEditText
    private lateinit var etDescription: TextInputEditText

    // ID di default (puoi mettere una tua icona di default)
    private var selectedIconResId: Int = R.drawable.ic_recipe_temp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        // Riferimenti ai pulsanti
        homeButton = findViewById(R.id.homeButton)
        forwardButton = findViewById(R.id.forwardButton)
        settingsButton = findViewById(R.id.settingsButton)
        ibRecipeIcon = findViewById(R.id.ibRecipeIcon)

        // Riferimenti ai campi di testo
        etTitle = findViewById(R.id.etTitle)
        etTime = findViewById(R.id.etTime)
        etDifficulty = findViewById(R.id.etDifficulty)
        etDescription = findViewById(R.id.etDescription)

        // Listener pulsanti
        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Quando l'utente preme il pulsante Avanti (forward), salviamo la ricetta
        forwardButton.setOnClickListener {
            saveRecipeToDatabase()
        }

        // Clic sull'icona: apri la schermata delle icone
        ibRecipeIcon.setOnClickListener {
            val intent = Intent(this, IconsListActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CHOOSE_ICON)
        }
    }

    private fun saveRecipeToDatabase() {
        val title = etTitle.text?.toString()?.trim().orEmpty()
        val time = etTime.text?.toString()?.trim().orEmpty()
        val difficulty = etDifficulty.text?.toString()?.trim().orEmpty()
        val description = etDescription.text?.toString()?.trim().orEmpty()

        // Controllo minimo: il titolo non deve essere vuoto
        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a recipe title", Toast.LENGTH_SHORT).show()
            return
        }

        // Crea il Recipe
        val recipe = Recipe(
            name = title,
            time = time,
            difficulty = difficulty,
            iconResId = selectedIconResId,
            description = description
        )

        // Inserisci nel DB
        val dbHelper = RecipeDatabaseHelper(this)
        val rowId = dbHelper.insertRecipe(recipe)
        if (rowId == -1L) {
            Toast.makeText(this, "Error saving recipe.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Recipe saved (ID: $rowId).", Toast.LENGTH_SHORT).show()
            // Prosegui, ad esempio, alla AddStepsActivity
            startActivity(Intent(this, AddStepsActivity::class.java))
        }
    }

    // Ricevi l'icona selezionata dalla IconsListActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_CHOOSE_ICON && resultCode == Activity.RESULT_OK) {
            val chosenIcon = data?.getIntExtra("ICON_RES_ID", -1) ?: -1
            if (chosenIcon != -1) {
                selectedIconResId = chosenIcon
                // Aggiorna l'icona sul pulsante per mostrare immediatamente la scelta
                ibRecipeIcon.setImageResource(chosenIcon)
            }
        }
    }
}