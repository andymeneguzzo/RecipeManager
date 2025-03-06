package com.andy.recipemanager

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var hamburgerButton: FloatingActionButton
    private lateinit var userButton: FloatingActionButton
    private lateinit var addButton: FloatingActionButton
    private lateinit var settingsButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BOTTONI
        hamburgerButton = findViewById(R.id.hamburgerButton)
        userButton = findViewById(R.id.userButton)
        addButton = findViewById(R.id.addButton)
        settingsButton = findViewById(R.id.settingsButton)

        val topBar = findViewById<LinearLayout>(R.id.clickableTopBar)
        topBar.setOnClickListener {
            startActivity(Intent(this, GreetingsActivity::class.java))
            finish()
        }
    }
}