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
import com.andy.recipemanager.data.Recipe
import com.andy.recipemanager.data.RecipeDatabaseHelper
import com.andy.recipemanager.adapters.RecipeAdapter
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

    // Altri riferimenti
    private lateinit var searchButton: ImageButton
    private lateinit var searchBar: EditText
    private lateinit var topBar: LinearLayout

    private lateinit var recipeListRecyclerView: RecyclerView
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipes = mutableListOf<Recipe>()
    private lateinit var dbHelper: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hamburgerButton = findViewById(R.id.hamburgerButton)
        userButton = findViewById(R.id.userButton)
        addButton = findViewById(R.id.addButton)
        settingsButton = findViewById(R.id.settingsButton)
        searchButton = findViewById(R.id.searchButton)
        searchBar = findViewById(R.id.searchBar)
        topBar = findViewById(R.id.clickableTopBar)

        // Imposta il DrawerLayout e NavigationView
        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.itemIconTintList = null

        // Imposta RecyclerView per la lista delle ricette
        recipeListRecyclerView = findViewById(R.id.recipeList)
        recipeListRecyclerView.layoutManager = LinearLayoutManager(this)
        recipeAdapter = RecipeAdapter(recipes)
        recipeListRecyclerView.adapter = recipeAdapter

        // Inizializza il database helper
        dbHelper = RecipeDatabaseHelper(this)

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
            if (searchBar.isGone) {
                searchBar.visibility = View.VISIBLE
                searchBar.pivotX = 0f
                searchBar.scaleX = 0f
                searchBar.animate().scaleX(1f).setDuration(250).start()
            } else {
                searchBar.animate().scaleX(0f).setDuration(250)
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

        hamburgerButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshRecipeList()
    }

    private fun refreshRecipeList() {
        recipes.clear()
        recipes.addAll(dbHelper.getAllRecipes())
        recipeAdapter.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        // Gestione delle voci del menu laterale...
        when (menuItem.itemId) {
            R.id.nav_pasta -> {
                startActivity(Intent(this, PastaListActivity::class.java))
            }
            R.id.nav_carne -> {
                startActivity(Intent(this, MeatListActivity::class.java))
            }
            R.id.nav_pesce -> {
                startActivity(Intent(this, FishListActivity::class.java))
            }
            R.id.nav_dolci -> {
                startActivity(Intent(this, DessertListActivity::class.java))
            }
            R.id.nav_burger -> {
                startActivity(Intent(this, BurgerListActivity::class.java))
            }
            R.id.nav_pizza -> {
                startActivity(Intent(this, PizzaListActivity::class.java))
            }
            R.id.nav_sushi -> {
                startActivity(Intent(this, SushiListActivity::class.java))
            }
            R.id.nav_veggie -> {
                startActivity(Intent(this, VeggieListActivity::class.java))
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("This method has been deprecated in favor of using OnBackPressedDispatcher.")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}