package com.andy.recipemanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.data.Recipe
import com.andy.recipemanager.data.RecipeDatabaseHelper
import com.andy.recipemanager.activities.EditRecipeActivity
import com.andy.recipemanager.activities.EditStepsActivity

class RecipeAdapter(private val recipes: MutableList<Recipe>)
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

        holder.tvRecipeName.text = recipe.name
        holder.tvRecipeTime.text = recipe.time
        holder.tvRecipeDifficulty.text = recipe.difficulty
        holder.ivRecipeImage.setImageResource(recipe.iconResId)

        holder.btnMore.setOnClickListener { view ->
            val popupMenu = PopupMenu(view.context, view)
            popupMenu.menuInflater.inflate(R.menu.popup_recipe_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_modify_recipe -> {
                        val context = view.context
                        val intent = Intent(context, EditRecipeActivity::class.java)
                        intent.putExtra("RECIPE_ID", recipe.id)
                        context.startActivity(intent)
                        true
                    }
                    R.id.action_delete -> {
                        val context = view.context
                        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                        builder.setTitle("Delete Recipe")
                        builder.setMessage("Are you sure you want to delete this recipe?")
                        builder.setPositiveButton("Yes") { dialog, _ ->
                            val dbHelper = RecipeDatabaseHelper(context)
                            val rows = dbHelper.deleteRecipe(recipe.id)
                            if (rows > 0) {
                                Toast.makeText(context, "Recipe deleted", Toast.LENGTH_SHORT).show()
                                recipes.removeAt(position)
                                notifyItemRemoved(position)
                            } else {
                                Toast.makeText(context, "Error deleting recipe", Toast.LENGTH_SHORT).show()
                            }
                        }
                        builder.setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        builder.show()
                        true
                    }
                    R.id.action_modify_steps -> {
                        val context = view.context
                        val dbHelper = RecipeDatabaseHelper(context)
                        val steps = dbHelper.getStepsForRecipe(recipe.id)
                        if (steps.isNotEmpty()) {
                            // Passa l'ID del primo step per esempio
                            val intent = Intent(context, EditStepsActivity::class.java)
                            intent.putExtra("STEP_ID", steps.first().id)
                            intent.putExtra("RECIPE_ID", recipe.id)
                            context.startActivity(intent)
                        } else {
                            Toast.makeText(context, "No steps available for editing.", Toast.LENGTH_SHORT).show()
                        }
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }
}