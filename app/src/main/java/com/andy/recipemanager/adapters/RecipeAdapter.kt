package com.andy.recipemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.data.Recipe

class RecipeAdapter(private val recipies: List<Recipe>)
    : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRecipeImage: ImageView = itemView.findViewById(R.id.ivRecipeImage)
        val tvRecipeName: TextView = itemView.findViewById(R.id.tvRecipeName)
        val tvRecipeTime: TextView = itemView.findViewById(R.id.tvRecipeTime)
        val tvRecipeDifficulty: TextView = itemView.findViewById(R.id.tvRecipeDifficulty)
        val btnMore: ImageButton = itemView.findViewById(R.id.btnMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipies[position]

        holder.tvRecipeName.text = recipe.name
        holder.tvRecipeTime.text = recipe.time
        holder.tvRecipeDifficulty.text = recipe.difficulty

        // If you have an image resource or a URL, set it on ivRecipeImage here
        // e.g., holder.ivRecipeImage.setImageResource(recipe.imageResId)

        // Handle clicks on the more button
        holder.btnMore.setOnClickListener {
            // Show pop up menu
        }
    }

    override fun getItemCount(): Int {
        return recipies.size
    }
}