package com.andy.recipemanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.core.view.isGone

class MainActivity : AppCompatActivity() {

    private lateinit var hamburgerButton: FloatingActionButton
    private lateinit var userButton: FloatingActionButton
    private lateinit var addButton: FloatingActionButton
    private lateinit var settingsButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

                // searchBar.requestFocus() -> optional
            } else {
                searchBar.visibility = View.GONE
            }
        }

        val topBar = findViewById<LinearLayout>(R.id.clickableTopBar)
        topBar.setOnClickListener {
            startActivity(Intent(this, GreetingsActivity::class.java))
            finish()
        }
    }
}