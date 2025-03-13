package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.adapters.StepsAdapter
import com.andy.recipemanager.data.RecipeDatabaseHelper
import com.andy.recipemanager.data.Step
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class EditStepsActivity : BaseActivity() {

    private lateinit var rvSteps: RecyclerView
    private lateinit var etEditStep: TextInputEditText
    private lateinit var etEditStepTimer: TextInputEditText
    private lateinit var fabSaveEdits: FloatingActionButton

    private val stepsList = mutableListOf<Step>()
    private lateinit var stepsAdapter: StepsAdapter

    private var recipeId: Long = -1L
    private var stepId: Long = -1L
    private lateinit var dbHelper: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_steps)

        rvSteps = findViewById(R.id.rvSteps)
        etEditStep = findViewById(R.id.etEditStep)
        etEditStepTimer = findViewById(R.id.etEditStepTimer)
        fabSaveEdits = findViewById(R.id.fabSaveEdits)

        dbHelper = RecipeDatabaseHelper(this)

        recipeId = intent.getLongExtra("RECIPE_ID", -1L)
        stepId = intent.getLongExtra("STEP_ID", -1L)
        if (recipeId == -1L || stepId == -1L) {
            Toast.makeText(this, "Recipe or Step ID not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Carica tutti gli step della ricetta
        stepsList.clear()
        stepsList.addAll(dbHelper.getStepsForRecipe(recipeId))
        stepsAdapter = StepsAdapter(stepsList, recipeId)
        rvSteps.layoutManager = LinearLayoutManager(this)
        rvSteps.adapter = stepsAdapter

        // Trova lo step da modificare e popola i campi
        val stepToEdit = stepsList.find { it.id == stepId }
        if (stepToEdit != null) {
            etEditStep.setText(stepToEdit.description)
            etEditStepTimer.setText(stepToEdit.timer)
        } else {
            Toast.makeText(this, "Step not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        fabSaveEdits.setOnClickListener {
            saveStepEdits()
        }

        val finishButton = findViewById<TextView>(R.id.finishButton)
        finishButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun saveStepEdits() {
        val newDescription = etEditStep.text?.toString()?.trim().orEmpty()
        val newTimer = etEditStepTimer.text?.toString()?.trim().orEmpty()

        if (newDescription.isEmpty() || newTimer.isEmpty()) {
            Toast.makeText(this, "Please enter both description and timer.", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedStep = Step(stepId, newDescription, newTimer)
        val rowsAffected = dbHelper.updateStep(stepId, updatedStep)
        if (rowsAffected > 0) {
            Toast.makeText(this, "Step updated.", Toast.LENGTH_SHORT).show()
            // Aggiorna l’elemento nella lista e notifica l’adapter
            val index = stepsList.indexOfFirst { it.id == stepId }
            if (index != -1) {
                stepsList[index] = updatedStep
                stepsAdapter.notifyItemChanged(index)
            }
        } else {
            Toast.makeText(this, "Error updating step.", Toast.LENGTH_SHORT).show()
        }
    }
}
