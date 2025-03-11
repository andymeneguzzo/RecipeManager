package com.andy.recipemanager.activities

import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.andy.recipemanager.R
import com.andy.recipemanager.adapters.IconAdapter

class IconsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_icons)

        // Riferimento al GridView
        val iconGridView = findViewById<GridView>(R.id.iconGridView)

        // Esempio di dati: array di drawable resource ID
        val iconsList = listOf(
            R.drawable.ic_pasta,
            R.drawable.ic_burger,
            R.drawable.ic_pizza,
            R.drawable.ic_carne,
            R.drawable.ic_pesce,
            R.drawable.ic_sushi,
            R.drawable.ic_veggie,
            R.drawable.ic_dolci,
        )

        // Crea e imposta l'Adapter
        val adapter = IconAdapter(this, iconsList)
        iconGridView.adapter = adapter

        // Eventuale listener per gestire il click su una specifica icona
        iconGridView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val clickedIconResId = iconsList[position]
            // Fai qualcosa col drawable selezionato, ad es. mostra un Toast
            Toast.makeText(this, "Icon $clickedIconResId selected!", Toast.LENGTH_SHORT).show()
        }
    }
}