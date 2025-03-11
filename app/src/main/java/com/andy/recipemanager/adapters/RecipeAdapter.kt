package com.andy.recipemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.data.Recipe

class RecipeAdapter(private val recipes: List<Recipe>)
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
        val recipe = recipes[position]

        // Set text
        holder.tvRecipeName.text = recipe.name
        holder.tvRecipeTime.text = recipe.time
        holder.tvRecipeDifficulty.text = recipe.difficulty

        // If you have an image resource or a URL, set it here
        // e.g., holder.ivRecipeImage.setImageResource(recipe.imageResId)

        // Show PopupMenu when user presses the More button
        holder.btnMore.setOnClickListener { view ->
            // Crea il PopupMenu
            val popupMenu = PopupMenu(view.context, view)
            // Gonfia il layout del menu
            popupMenu.menuInflater.inflate(R.menu.popup_recipe_menu, popupMenu.menu)

            // Gestione click sulle voci del menu
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_modify_recipe -> {
                        // Azione: "Modify recipe"
                        // apri EditRecipeActivity
                        true
                    }
                    R.id.action_modify_steps -> {
                        // Azione: "Modify steps"
                        // apri StepsListActivity (da creare)
                        true
                    }
                    R.id.action_delete -> {
                        // Azione: "Delete"
                        // Mostra un dialog di conferma e rimuovi la ricetta
                        true
                    }
                    else -> false
                }
            }

            // Mostra il menu popup
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}