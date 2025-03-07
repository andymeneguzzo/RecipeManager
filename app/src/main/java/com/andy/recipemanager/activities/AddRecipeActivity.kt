package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var addButton: FloatingActionButton
    private lateinit var homeButton: FloatingActionButton
    private lateinit var settingsButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}