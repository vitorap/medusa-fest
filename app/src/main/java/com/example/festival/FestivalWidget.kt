package com.example.festival

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.*
import android.content.*
import android.widget.RemoteViews
import java.util.Calendar

class FestivalWidget : AppWidgetProvider() {

    override fun onUpdate(ctx: Context, mgr: AppWidgetManager, ids: IntArray) {
        val cal = Calendar.getInstance()
        val nowMin = cal.get(Calendar.DAY_OF_MONTH) * 24 * 60 + cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)

        fun current(stage: String): String {
            val act = lineup.filter { it.stage == stage && actMinutes(it) <= nowMin }.maxByOrNull { actMinutes(it) }
            return if (act != null) "${act.name}\n${formatTime(act)}" else "\u2014"
        }

        val launchIntent = Intent(ctx, MainActivity::class.java)
        val pi = PendingIntent.getActivity(ctx, 0, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        for (id in ids) {
            val views = RemoteViews(ctx.packageName, R.layout.widget_layout)
            views.setTextViewText(R.id.wRage, "\uD83D\uDD34 ${current("Rage")}")
            views.setTextViewText(R.id.wKodama, "\uD83D\uDFE2 ${current("Kodama")}")
            views.setTextViewText(R.id.wTortuga, "\uD83D\uDFE1 ${current("Tortuga")}")
            views.setOnClickPendingIntent(R.id.widgetRoot, pi)
            mgr.updateAppWidget(id, views)
        }

        scheduleNext(ctx)
    }

    override fun onEnabled(ctx: Context) { scheduleNext(ctx) }

    override fun onDisabled(ctx: Context) {
        (ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(getPI(ctx))
    }

    private fun scheduleNext(ctx: Context) {
        val cal = Calendar.getInstance().apply {
            val min = get(Calendar.MINUTE)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            val nextMin = ((min / 10) + 1) * 10
            if (nextMin >= 60) { set(Calendar.MINUTE, 0); add(Calendar.HOUR_OF_DAY, 1) }
            else set(Calendar.MINUTE, nextMin)
        }
        val am = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, getPI(ctx))
    }

    private fun getPI(ctx: Context): PendingIntent {
        val intent = Intent(ctx, FestivalWidget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
                AppWidgetManager.getInstance(ctx).getAppWidgetIds(ComponentName(ctx, FestivalWidget::class.java)))
        }
        return PendingIntent.getBroadcast(ctx, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }
}
