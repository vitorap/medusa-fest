package com.example.festival

import android.Manifest
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

data class FavoriteToggleResult(val isFavorite: Boolean, val reminderScheduled: Boolean)

object FavoriteReminders {
    private const val CHANNEL_ID = "festival_reminders"
    private const val REQUEST_NOTIFICATIONS = 41

    fun createNotificationChannel(ctx: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel(CHANNEL_ID, "Festival Reminders", NotificationManager.IMPORTANCE_HIGH)
            (ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(ch)
        }
    }

    fun requestNotificationPermission(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            activity.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_NOTIFICATIONS)
        }
    }

    fun toggle(ctx: Context, act: Act): FavoriteToggleResult {
        Favorites.toggle(act.name)
        val isFavorite = Favorites.isFav(act.name)
        val scheduled = if (isFavorite) scheduleStartNotification(ctx, act) else {
            cancel(ctx, act)
            false
        }
        return FavoriteToggleResult(isFavorite, scheduled)
    }

    private fun scheduleStartNotification(ctx: Context, act: Act): Boolean {
        createNotificationChannel(ctx)
        val cal = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.MONTH, java.util.Calendar.MAY)
            set(java.util.Calendar.YEAR, 2026)
            set(java.util.Calendar.DAY_OF_MONTH, act.day)
            set(java.util.Calendar.HOUR_OF_DAY, act.hour)
            set(java.util.Calendar.MINUTE, act.minute)
            set(java.util.Calendar.SECOND, 0)
        }
        if (cal.timeInMillis < System.currentTimeMillis()) return false

        val intent = Intent(ctx, ReminderReceiver::class.java).apply {
            putExtra("act_name", act.name)
            putExtra("act_stage", act.stage)
        }
        val pi = PendingIntent.getBroadcast(
            ctx,
            act.name.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
        }
        return true
    }

    fun cancel(ctx: Context, act: Act) {
        val intent = Intent(ctx, ReminderReceiver::class.java)
        val pi = PendingIntent.getBroadcast(
            ctx,
            act.name.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        (ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(pi)
    }
}
