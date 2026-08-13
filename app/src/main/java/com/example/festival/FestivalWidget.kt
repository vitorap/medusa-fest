package com.example.festival

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.*
import android.content.*
import android.widget.RemoteViews
import java.util.Calendar

class FestivalWidget : AppWidgetProvider() {

    override fun onUpdate(ctx: Context, mgr: AppWidgetManager, ids: IntArray) {
        val now = System.currentTimeMillis()

        fun current(stage: String): String {
            val live = lineup.firstOrNull { it.stage == stage && isActLive(it, now) }
            if (live != null) return "${live.name}\n${formatTime(live)}"

            val next = lineup
                .filter { it.stage == stage && actStartMillis(it) > now }
                .minByOrNull(::actMinutes)
            return if (next != null) "Próximo: ${next.name}\n${eventDayLabel(next.eventDay)} · ${formatTime(next)}" else "Programação encerrada"
        }

        val launchIntent = Intent(ctx, MainActivity::class.java)
        val pi = PendingIntent.getActivity(ctx, 0, launchIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        for (id in ids) {
            val views = RemoteViews(ctx.packageName, R.layout.widget_layout)
            views.setTextViewText(R.id.wRage, "✨ Apsaras\n${current("Apsaras")}")
            views.setTextViewText(R.id.wKodama, "⚡ Resonance\n${current("Resonance")}")
            views.setTextViewText(R.id.wTortuga, "🎮 Arcade Land\n${current("Arcade Land")}")
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
