package com.andy.recipemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.andy.recipemanager.R

class IconAdapter(
    private val context: Context,
    private val icons: List<Int>  // Lista di R.drawable.xxx
) : BaseAdapter() {

    override fun getCount(): Int {
        return icons.size
    }

    override fun getItem(position: Int): Any {
        return icons[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Ricicla la view se esiste, altrimenti la inflate
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_icon, parent, false)

        // Assegna l'icona
        val imageView = view.findViewById<ImageView>(R.id.iconImage)
        imageView.setImageResource(icons[position])

        return view
    }
}