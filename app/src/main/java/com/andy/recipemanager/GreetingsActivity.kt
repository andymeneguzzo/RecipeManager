package com.andy.recipemanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GreetingsActivity : AppCompatActivity() {

    private lateinit var fabHome: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greetings)

        fabHome = findViewById(R.id.fab_home)

        fabHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}