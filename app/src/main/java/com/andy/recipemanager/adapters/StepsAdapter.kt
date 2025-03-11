package com.andy.recipemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.data.Step

class StepsAdapter(private val steps: List<Step>) : RecyclerView.Adapter<StepsAdapter.StepViewHolder>() {

    inner class StepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvStepDescription: TextView = itemView.findViewById(R.id.tvStepDescription)
        val tvStepTimer: TextView = itemView.findViewById(R.id.tvStepTimer)
        val btnMore: ImageButton = itemView.findViewById(R.id.btnMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        // Infliamo il layout item_step
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_step, parent, false)
        return StepViewHolder(view)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]

        // Assegna i campi al layout
        holder.tvStepDescription.text = step.description
        holder.tvStepTimer.text = step.timer

        // Gestione pulsante More
        holder.btnMore.setOnClickListener { view ->
            // Mostra un PopupMenu con le voci (Modify step, Delete, ecc.)
            showPopupMenu(view, step)
        }
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    private fun showPopupMenu(anchorView: View, step: Step) {
        val popupMenu = PopupMenu(anchorView.context, anchorView)
        // Inflatea un menu personalizzato (da definire in res/menu/popup_step_menu.xml)
        popupMenu.menuInflater.inflate(R.menu.popup_step_menu, popupMenu.menu)

        // Gestisci i click
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_modify_step -> {
                    // Azione di modifica step
                    true
                }
                R.id.action_delete_step -> {
                    // Azione di cancellazione step
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}