package com.andy.recipemanager.adapters

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.activities.EditStepsActivity
import com.andy.recipemanager.data.RecipeDatabaseHelper
import com.andy.recipemanager.data.Step

class StepsAdapter(
    private val steps: MutableList<Step>,
    private val recipeId: Long
) : RecyclerView.Adapter<StepsAdapter.StepViewHolder>() {

    inner class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStepDescription: TextView = itemView.findViewById(R.id.tvStepDescription)
        val tvStepTimer: TextView = itemView.findViewById(R.id.tvStepTimer)
        val btnMore: ImageButton = itemView.findViewById(R.id.btnMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_step, parent, false)
        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]
        holder.tvStepDescription.text = step.description
        holder.tvStepTimer.text = step.timer

        holder.btnMore.setOnClickListener { view ->
            showPopupMenu(view, step, position)
        }
    }

    override fun getItemCount(): Int = steps.size

    private fun showPopupMenu(anchorView: View, step: Step, position: Int) {
        val popupMenu = PopupMenu(anchorView.context, anchorView)
        popupMenu.menuInflater.inflate(R.menu.popup_step_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_modify_step -> {
                    // Lancia EditStepsActivity passando STEP_ID e RECIPE_ID
                    val context = anchorView.context
                    val intent = Intent(context, EditStepsActivity::class.java)
                    intent.putExtra("STEP_ID", step.id)
                    intent.putExtra("RECIPE_ID", recipeId)
                    context.startActivity(intent)
                    true
                }
                R.id.action_delete_step -> {
                    // Mostra AlertDialog per la conferma e cancella lo step
                    val context = anchorView.context
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Delete Step")
                    builder.setMessage("Are you sure you want to delete this step?")
                    builder.setPositiveButton("Yes") { dialog, _ ->
                        val dbHelper = RecipeDatabaseHelper(context)
                        val rowsDeleted = dbHelper.deleteStep(step.id)
                        if (rowsDeleted > 0) {
                            Toast.makeText(context, "Step deleted", Toast.LENGTH_SHORT).show()
                            steps.removeAt(position)
                            notifyItemRemoved(position)
                        } else {
                            Toast.makeText(context, "Error deleting step", Toast.LENGTH_SHORT).show()
                        }
                    }
                    builder.setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    builder.show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}