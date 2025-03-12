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
    private val icons: List<Int>
) : BaseAdapter() {

    override fun getCount(): Int = icons.size

    override fun getItem(position: Int): Any = icons[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_icon, parent, false)
        val imageView = view.findViewById<ImageView>(R.id.iconImage)
        imageView.setImageResource(icons[position])
        return view
    }
}