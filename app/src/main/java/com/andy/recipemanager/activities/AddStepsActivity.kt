package com.andy.recipemanager.activities

import android.app.Activity
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

class AddStepsActivity : BaseActivity() {

    private lateinit var rvSteps: RecyclerView
    private lateinit var fabAddStep: FloatingActionButton
    private lateinit var etStepDescription: TextInputEditText
    private lateinit var etStepTimer: TextInputEditText
    private lateinit var finishButton: TextView

    private val stepsList = mutableListOf<Step>()
    private lateinit var stepsAdapter: StepsAdapter

    private var recipeId: Long = -1L
    private lateinit var dbHelper: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_steps)

        rvSteps = findViewById(R.id.rvSteps)
        fabAddStep = findViewById(R.id.fabAddStep)
        etStepDescription = findViewById(R.id.etStepDescription)
        etStepTimer = findViewById(R.id.etStepTimer)
        finishButton = findViewById(R.id.finishButton)

        dbHelper = RecipeDatabaseHelper(this)

        // Recupera l'ID della ricetta dall'extra
        recipeId = intent.getLongExtra("RECIPE_ID", -1L)
        if (recipeId == -1L) {
            Toast.makeText(this, "Recipe ID not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Passa recipeId al costruttore dello StepsAdapter
        stepsAdapter = StepsAdapter(stepsList, recipeId)
        rvSteps.layoutManager = LinearLayoutManager(this)
        rvSteps.adapter = stepsAdapter

        fabAddStep.setOnClickListener {
            addStep()
        }

        finishButton.setOnClickListener {
            finishSteps()
        }
    }

    private fun addStep() {
        val description = etStepDescription.text?.toString()?.trim().orEmpty()
        val timer = etStepTimer.text?.toString()?.trim().orEmpty()

        if (description.isEmpty() || timer.isEmpty()) {
            Toast.makeText(this, "Please enter both description and timer.", Toast.LENGTH_SHORT).show()
            return
        }

        val step = Step(description = description, timer = timer)
        val rowId = dbHelper.insertStep(recipeId, step)
        if (rowId == -1L) {
            Toast.makeText(this, "Error saving step.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Step added.", Toast.LENGTH_SHORT).show()
            // Crea un nuovo oggetto Step con l'id corretto
            val savedStep = step.copy(id = rowId)
            stepsList.add(savedStep)
            stepsAdapter.notifyItemInserted(stepsList.size - 1)
            etStepDescription.text?.clear()
            etStepTimer.text?.clear()
        }
    }

    // Avoid creating multiple instances of main activity
    private fun finishSteps() {
        Toast.makeText(this, "Recipe completed.", Toast.LENGTH_SHORT).show()
        setResult(Activity.RESULT_OK)
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}