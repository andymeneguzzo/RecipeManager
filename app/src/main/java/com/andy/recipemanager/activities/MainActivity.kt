package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.adapters.RecipeAdapter
import com.andy.recipemanager.data.Recipe
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 *
 * TODO: animazione searchBar
 * */
class MainActivity : AppCompatActivity() {

    private lateinit var hamburgerButton: FloatingActionButton
    private lateinit var userButton: FloatingActionButton
    private lateinit var addButton: FloatingActionButton
    private lateinit var settingsButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ADAPTER TEST

        // Reference RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recipeList)

        // Chose LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Provide some data
        val sampleRecipes = listOf(
            Recipe("Rigatoni alla Carbonara", "15 min", "Easy"),
            Recipe("Arrosto e Patate", "2 hrs", "Medium"),
            Recipe("Pizza Margherita", "30 min", "Easy")
        )

        // Create Adapter
        val adapter = RecipeAdapter(sampleRecipes)
        recyclerView.adapter = adapter



        // BUTTONS
        hamburgerButton = findViewById(R.id.hamburgerButton)
        userButton = findViewById(R.id.userButton)
        addButton = findViewById(R.id.addButton)
        settingsButton = findViewById(R.id.settingsButton)

        // SEARCH
        val searchButton = findViewById<ImageButton>(R.id.searchButton)
        val searchBar = findViewById<EditText>(R.id.searchBar)

        searchButton.setOnClickListener {
            // Toggle if button pressed
            if(searchBar.isGone) {
                searchBar.visibility = View.VISIBLE
                searchBar.pivotX = 0f
                searchBar.scaleX = 0f

                searchBar.animate()
                    .scaleX(1f)
                    .setDuration(250)
                    .start()

                // searchBar.requestFocus() -> optional
            } else {
                searchBar.animate()
                    .scaleX(0f)
                    .setDuration(250)
                    .withEndAction { searchBar.visibility = View.GONE }
                    .start()
            }
        }

        val topBar = findViewById<LinearLayout>(R.id.clickableTopBar)
        topBar.setOnClickListener {
            startActivity(Intent(this, GreetingsActivity::class.java))
            finish()
        }
    }
}