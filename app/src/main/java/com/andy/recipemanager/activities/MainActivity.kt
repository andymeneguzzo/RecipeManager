package com.andy.recipemanager.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andy.recipemanager.R
import com.andy.recipemanager.drawer_activities.BurgerListActivity
import com.andy.recipemanager.drawer_activities.DessertListActivity
import com.andy.recipemanager.drawer_activities.FishListActivity
import com.andy.recipemanager.drawer_activities.MeatListActivity
import com.andy.recipemanager.drawer_activities.PastaListActivity
import com.andy.recipemanager.drawer_activities.PizzaListActivity
import com.andy.recipemanager.drawer_activities.SushiListActivity
import com.andy.recipemanager.drawer_activities.VeggieListActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // Drawer e NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    // Pulsanti principali
    private lateinit var hamburgerButton: ImageButton
    private lateinit var userButton: ImageButton
    private lateinit var addButton: ImageButton
    private lateinit var settingsButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Usa il tuo layout con il DrawerLayout

        // Se in futuro desideri mostrare una lista di ricette reali,
        // puoi recuperarle dal DB e collegarle a un RecyclerView qui.
        // Per ora, non carichiamo alcun adapter né ricette di esempio.

        // --- RIFERIMENTI AI PULSANTI E VIEW ---
        hamburgerButton = findViewById(R.id.hamburgerButton)
        userButton = findViewById(R.id.userButton)
        addButton = findViewById(R.id.addButton)
        settingsButton = findViewById(R.id.settingsButton)

        val searchButton = findViewById<ImageButton>(R.id.searchButton)
        val searchBar = findViewById<EditText>(R.id.searchBar)
        val topBar = findViewById<LinearLayout>(R.id.clickableTopBar)

        // --- LISTENER PULSANTI ---
        addButton.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }

        userButton.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        searchButton.setOnClickListener {
            // Mostra o nascondi la barra di ricerca con un'animazione
            if (searchBar.isGone) {
                searchBar.visibility = View.VISIBLE
                searchBar.pivotX = 0f
                searchBar.scaleX = 0f

                searchBar.animate()
                    .scaleX(1f)
                    .setDuration(250)
                    .start()
            } else {
                searchBar.animate()
                    .scaleX(0f)
                    .setDuration(250)
                    .withEndAction { searchBar.visibility = View.GONE }
                    .start()
            }
        }

        topBar.setOnClickListener {
            val intent = Intent(this, GreetingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.fade_out)
            finish()
        }

        // --- GESTIONE DRAWER ---
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        // Imposta il listener per la NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.itemIconTintList = null

        // Al click del bottone hamburger, apri il cassetto laterale
        hamburgerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    // Gestione dei click sulle voci del menu a scomparsa
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.nav_pasta -> {
                val intent = Intent(this, PastaListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_carne -> {
                val intent = Intent(this, MeatListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_pesce -> {
                val intent = Intent(this, FishListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_dolci -> {
                val intent = Intent(this, DessertListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_burger -> {
                val intent = Intent(this, BurgerListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_pizza -> {
                val intent = Intent(this, PizzaListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_sushi -> {
                val intent = Intent(this, SushiListActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_veggie -> {
                val intent = Intent(this, VeggieListActivity::class.java)
                startActivity(intent)
            }
        }
        // Chiudi il drawer dopo il click
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    // Se l'utente preme il tasto "indietro" mentre il cassetto è aperto, chiudi il drawer
    @Deprecated("This method has been deprecated in favor of using the {@link OnBackPressedDispatcher}.")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}