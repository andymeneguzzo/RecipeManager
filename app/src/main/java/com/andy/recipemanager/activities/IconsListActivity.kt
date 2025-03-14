package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R
import com.andy.recipemanager.adapters.IconAdapter

class IconsListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_icons)

        val iconGridView = findViewById<GridView>(R.id.iconGridView)

        val iconsList = listOf(
            R.drawable.ic_pasta,
            R.drawable.ic_burger,
            R.drawable.ic_pizza,
            R.drawable.ic_carne,
            R.drawable.ic_pesce,
            R.drawable.ic_sushi,
            R.drawable.ic_veggie,
            R.drawable.ic_dolci
        )

        val adapter = IconAdapter(this, iconsList)
        iconGridView.adapter = adapter

        iconGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val clickedIconResId = iconsList[position]
            Toast.makeText(this, "Icon $clickedIconResId selected!", Toast.LENGTH_SHORT).show()

            val resultIntent = Intent().apply {
                putExtra("ICON_RES_ID", clickedIconResId)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}