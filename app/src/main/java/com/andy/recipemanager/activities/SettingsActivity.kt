package com.andy.recipemanager.activities

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.andy.recipemanager.R
import com.andy.recipemanager.NotificationReceiver
import java.util.*
import kotlin.jvm.java

class SettingsActivity : BaseActivity() {

    private lateinit var notificationsSwitch: Switch
    private lateinit var darkModeSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "settings_prefs"
        const val KEY_NOTIFICATIONS = "notifications_enabled"
        const val KEY_DARK_MODE = "dark_mode_enabled"
        const val NOTIFICATION_REQUEST_CODE = 100
        const val NOTIFICATION_CHANNEL_ID = "daily_notifications"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Carica le impostazioni prima di chiamare super.onCreate per applicare il tema corretto
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val darkModeEnabled = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        if (darkModeEnabled) {
            setTheme(R.style.Theme_RecipeManager_Dark)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            setTheme(R.style.Theme_RecipeManager)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Assumi che nel layout activity_settings.xml gli switch abbiano gli id "switch_notifications" e "switch_dark_mode"
        notificationsSwitch = findViewById(R.id.switch_notifications)
        darkModeSwitch = findViewById(R.id.switch_dark_mode)

        notificationsSwitch.isChecked = sharedPreferences.getBoolean(KEY_NOTIFICATIONS, false)
        darkModeSwitch.isChecked = darkModeEnabled

        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply()
            if (isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_REQUEST_CODE)
                    } else {
                        scheduleDailyNotification()
                    }
                } else {
                    scheduleDailyNotification()
                }
            } else {
                cancelDailyNotification()
            }
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(this, "Dark mode enabled", Toast.LENGTH_SHORT).show()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(this, "Dark mode disabled", Toast.LENGTH_SHORT).show()
            }
            recreate()
        }

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Daily Notifications"
            val descriptionText = "Channel for daily recipe notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun scheduleDailyNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("notification_text", "Cuciniamo qualcosa di buono!")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }
        if (calendar.timeInMillis <= System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(this, "Daily notifications scheduled", Toast.LENGTH_SHORT).show()
    }

    private fun cancelDailyNotification() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )
        alarmManager.cancel(pendingIntent)
        Toast.makeText(this, "Daily notifications canceled", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == NOTIFICATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                scheduleDailyNotification()
            } else {
                notificationsSwitch.isChecked = false
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}