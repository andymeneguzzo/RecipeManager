package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.data.RecipeDatabaseHelper
import com.andy.recipemanager.data.Step

class ViewStepsActivity : BaseActivity() {

    private lateinit var rvSteps: RecyclerView
    private lateinit var finishButton: TextView

    private var recipeId: Long = -1L
    private lateinit var dbHelper: RecipeDatabaseHelper
    private val stepsList = mutableListOf<Step>()
    private lateinit var adapter: ViewStepsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_steps)

        rvSteps = findViewById(R.id.rvSteps)
        finishButton = findViewById(R.id.finishButton)

        dbHelper = RecipeDatabaseHelper(this)
        recipeId = intent.getLongExtra("RECIPE_ID", -1L)
        if (recipeId == -1L) {
            Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show()
            finish()
        }

        stepsList.addAll(dbHelper.getStepsForRecipe(recipeId))
        adapter = ViewStepsAdapter(stepsList)
        rvSteps.layoutManager = LinearLayoutManager(this)
        rvSteps.adapter = adapter

        finishButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }
    }

    // Adapter per visualizzare gli step
    inner class ViewStepsAdapter(private val steps: List<Step>) : RecyclerView.Adapter<ViewStepsAdapter.StepViewHolder>() {

        inner class StepViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            val tvStepDescription: android.widget.TextView = itemView.findViewById(R.id.tvStepDescription)
            val tvStepTimer: android.widget.TextView = itemView.findViewById(R.id.tvStepTimer)
        }

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): StepViewHolder {
            val view = android.view.LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false)
            return StepViewHolder(view)
        }

        override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
            val step = steps[position]
            holder.tvStepDescription.text = step.description
            holder.tvStepTimer.text = step.timer
        }

        override fun getItemCount(): Int = steps.size
    }
}