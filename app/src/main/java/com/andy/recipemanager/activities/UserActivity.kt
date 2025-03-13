package com.andy.recipemanager.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.andy.recipemanager.R

class UserActivity : BaseActivity() {

    private lateinit var btnHome: ImageButton
    private lateinit var settingsButton: ImageButton
    private lateinit var saveButton: ImageButton
    private lateinit var imgButtonProfile: ImageButton

    // Altri campi utente
    private lateinit var etUsername: EditText
    private lateinit var etSurname: EditText
    private lateinit var etEmail: EditText
    private lateinit var etBirthdate: EditText
    private lateinit var etBio: EditText

    // Launcher per selezionare immagine dalla galleria
    private lateinit var imagePickerLauncher: androidx.activity.result.ActivityResultLauncher<String>

    private val prefsName = "user_prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        btnHome = findViewById(R.id.homeButton)
        settingsButton = findViewById(R.id.settingsButton)
        saveButton = findViewById(R.id.saveButton)
        imgButtonProfile = findViewById(R.id.imgButtonProfile)

        // Assumiamo che gli id degli altri campi siano impostati in activity_user.xml
        etUsername = findViewById(R.id.etUsername)
        etSurname = findViewById(R.id.etSurname)
        etEmail = findViewById(R.id.etEmail)
        etBirthdate = findViewById(R.id.etBirthdate)
        etBio = findViewById(R.id.etBio)

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Salva i dati utente al click del saveButton
        saveButton.setOnClickListener {
            saveUserData()
        }

        // Configura il launcher per ottenere l'immagine dalla galleria
        imagePickerLauncher = registerForActivityResult(
            androidx.activity.result.contract.ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                // Carica il bitmap dall'URI
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, it)
                // Ritaglia il bitmap a forma quadrata (se necessario)
                val squareBitmap = getSquareBitmap(bitmap)
                imgButtonProfile.setImageBitmap(squareBitmap)
                // Salva l'URI (come stringa) nelle SharedPreferences
                val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
                prefs.edit().putString("profile_image_uri", it.toString()).apply()
            }
        }

        // Avvia il picker quando l'utente tocca l'immagine di profilo
        imgButtonProfile.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        // Carica eventuali dati salvati
        loadUserData()
    }

    private fun saveUserData() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString("username", etUsername.text.toString())
            putString("surname", etSurname.text.toString())
            putString("email", etEmail.text.toString())
            putString("birthdate", etBirthdate.text.toString())
            putString("bio", etBio.text.toString())
            apply()
        }
        Toast.makeText(this, "User data saved", Toast.LENGTH_SHORT).show()
    }

    private fun loadUserData() {
        val prefs = getSharedPreferences(prefsName, Context.MODE_PRIVATE)
        etUsername.setText(prefs.getString("username", ""))
        etSurname.setText(prefs.getString("surname", ""))
        etEmail.setText(prefs.getString("email", ""))
        etBirthdate.setText(prefs.getString("birthdate", ""))
        etBio.setText(prefs.getString("bio", ""))

        prefs.getString("profile_image_uri", null)?.let {
            val uri = Uri.parse(it)
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val squareBitmap = getSquareBitmap(bitmap)
            imgButtonProfile.setImageBitmap(squareBitmap)
        }
    }

    // Funzione helper: ritaglia il bitmap in modo da ottenere un'immagine quadrata
    private fun getSquareBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        return if (width == height) {
            bitmap
        } else {
            val newEdge = width.coerceAtMost(height)
            val xOffset = (width - newEdge) / 2
            val yOffset = (height - newEdge) / 2
            Bitmap.createBitmap(bitmap, xOffset, yOffset, newEdge, newEdge)
        }
    }
}