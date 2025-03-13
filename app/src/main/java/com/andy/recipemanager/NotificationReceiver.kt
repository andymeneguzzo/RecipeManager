package com.andy.recipemanager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.andy.recipemanager.activities.MainActivity
import com.andy.recipemanager.activities.SettingsActivity

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationText = intent.getStringExtra("notification_text") ?: "Cuciniamo qualcosa di buono!"
        val notificationIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val builder = NotificationCompat.Builder(context, SettingsActivity.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_recipe_temp) // Usa l'icona dell'app o un'icona dedicata
            .setContentTitle("Cuciniamo qualcosa di buono!")
            .setContentText(notificationText)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, builder.build())
    }
}