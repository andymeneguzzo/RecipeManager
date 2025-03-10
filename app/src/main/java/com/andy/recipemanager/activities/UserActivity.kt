package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        val btnHome = findViewById<ImageButton>(R.id.homeButton)

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Will have to save gender selected and shown everytime the user enters the User activity
        val genderDropdown = findViewById<AutoCompleteTextView>(R.id.etGender)
        genderDropdown.setOnItemClickListener { parent, view, position, id ->
            val selectedGender = parent.getItemAtPosition(position).toString()
            // Do something with the selectedGender
        }
    }
}