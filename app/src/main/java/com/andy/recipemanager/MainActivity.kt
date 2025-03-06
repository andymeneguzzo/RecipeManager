package com.andy.recipemanager

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var hamburgerButton: Button
    private lateinit var userButton: Button
    private lateinit var addButton: Button
    private lateinit var settingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // BOTTONI
        hamburgerButton = findViewById(R.id.hamburgerButton)
        userButton = findViewById(R.id.userButton)
        addButton = findViewById(R.id.addButton)
        settingsButton = findViewById(R.id.settingsButton)


    }
}