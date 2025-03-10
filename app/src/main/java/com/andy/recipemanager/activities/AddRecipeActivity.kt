package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.jvm.java

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var forwardButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var settingsButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        homeButton = findViewById(R.id.homeButton)
        forwardButton = findViewById(R.id.forwardButton)

        forwardButton.setOnClickListener {
            val intent = Intent(this, AddStepsActivity::class.java)
            startActivity(intent)
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}