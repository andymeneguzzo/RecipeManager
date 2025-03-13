package com.andy.recipemanager.drawer_activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.andy.recipemanager.R
import com.andy.recipemanager.activities.BaseActivity
import com.andy.recipemanager.activities.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SushiListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sushi)

        val fabHome = findViewById<FloatingActionButton>(R.id.fab_home)

        fabHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}