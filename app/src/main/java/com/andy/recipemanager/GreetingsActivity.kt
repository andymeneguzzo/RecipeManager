package com.andy.recipemanager

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GreetingsActivity : AppCompatActivity() {

    private lateinit var fabHome: FloatingActionButton
    private lateinit var imgButtonGithub: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_greetings)

        val contentLayout = findViewById<LinearLayout>(R.id.contentLayout)
        val slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        contentLayout.startAnimation(slideUpAnimation)

        fabHome = findViewById(R.id.fab_home)
        imgButtonGithub = findViewById(R.id.img_button_github)

        fabHome.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }
}