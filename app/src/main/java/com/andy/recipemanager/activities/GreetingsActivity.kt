package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.andy.recipemanager.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GreetingsActivity : BaseActivity() {

    private lateinit var fabHome: FloatingActionButton
    private lateinit var imgButtonGithub: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greetings)

        fabHome = findViewById(R.id.fab_home)
        imgButtonGithub = findViewById(R.id.img_button_github)

        fabHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        imgButtonGithub.setOnClickListener {
            val githubUrl = "https://github.com/andymeneguzzo"
            val intent = Intent(Intent.ACTION_VIEW, githubUrl.toUri())
            startActivity(intent)
        }
    }
}