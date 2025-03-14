package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.andy.recipemanager.R
import com.andy.recipemanager.data.RecipeDatabaseHelper

class ViewInfoActivity : BaseActivity() {

    private lateinit var tvRecipeTitle: TextView
    private lateinit var ivRecipeIcon: ImageView
    private lateinit var tvRecipeTime: TextView
    private lateinit var tvRecipeDifficulty: TextView
    private lateinit var tvRecipeDescription: TextView

    private lateinit var homeButton: ImageButton
    private lateinit var forwardButton: ImageButton
    private lateinit var settingsButton: ImageButton

    private var recipeId: Long = -1L
    private lateinit var dbHelper: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_info)

        tvRecipeTitle = findViewById(R.id.tvRecipeTitle)
        ivRecipeIcon = findViewById(R.id.ivRecipeIcon)
        tvRecipeTime = findViewById(R.id.tvRecipeTime)
        tvRecipeDifficulty = findViewById(R.id.tvRecipeDifficulty)
        tvRecipeDescription = findViewById(R.id.tvRecipeDescription)

        homeButton = findViewById(R.id.homeButton)
        forwardButton = findViewById(R.id.forwardButton)
        settingsButton = findViewById(R.id.settingsButton)

        dbHelper = RecipeDatabaseHelper(this)
        recipeId = intent.getLongExtra("RECIPE_ID", -1L)
        if (recipeId == -1L) {
            Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            loadRecipeInfo()
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        forwardButton.setOnClickListener {
            val intent = Intent(this, ViewStepsActivity::class.java)
            intent.putExtra("RECIPE_ID", recipeId)
            startActivity(intent)
        }
        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun loadRecipeInfo() {
        val recipe = dbHelper.getRecipe(recipeId)
        if (recipe != null) {
            tvRecipeTitle.text = recipe.name
            tvRecipeTime.text = "Time: ${recipe.time}"
            tvRecipeDifficulty.text = "Difficulty: ${recipe.difficulty}"
            tvRecipeDescription.text = recipe.description
            ivRecipeIcon.setImageResource(recipe.iconResId)
        } else {
            Toast.makeText(this, "Recipe not found.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}