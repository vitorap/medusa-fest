package com.example.festival

import android.app.*
import android.content.*
import android.graphics.Typeface
import android.os.*
import android.util.TypedValue
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import java.util.Calendar

class LineupActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var content: LinearLayout
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lineup)
        Favorites.init(this)
        tabLayout = findViewById(R.id.tabLayout)
        content = findViewById(R.id.content)
        scrollView = findViewById(R.id.scrollView)

        listOf("Rage", "Kodama", "Tortuga").forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }
        showStage("Rage")

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { showStage(tab.text.toString()) }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        findViewById<Button>(R.id.btnJumpNow).setOnClickListener { jumpToNow() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val ch = NotificationChannel("festival_reminders", "Festival Reminders", NotificationManager.IMPORTANCE_HIGH)
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(ch)
        }
    }

    private var nowView: android.view.View? = null

    private fun showStage(stage: String) {
        content.removeAllViews()
        nowView = null
        val acts = lineup.filter { it.stage == stage }.sortedBy { actMinutes(it) }
        val cal = Calendar.getInstance()
        val nowMin = cal.get(Calendar.DAY_OF_MONTH) * 24 * 60 + cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)
        val currentAct = acts.filter { actMinutes(it) <= nowMin }.maxByOrNull { actMinutes(it) }

        val color = when (stage) {
            "Rage" -> 0xFFff6b6b.toInt(); "Kodama" -> 0xFF69f0ae.toInt(); else -> 0xFFffd740.toInt()
        }
        val dimColor = when (stage) {
            "Rage" -> 0xFF993333.toInt(); "Kodama" -> 0xFF338855.toInt(); else -> 0xFF997722.toInt()
        }
        val cardBg = when (stage) {
            "Rage" -> R.drawable.card_rage; "Kodama" -> R.drawable.card_kodama; else -> R.drawable.card_tortuga
        }

        var lastDay = -1
        for (act in acts) {
            if (act.day != lastDay) {
                lastDay = act.day
                val dayLabel = when (act.day) { 15 -> "FRIDAY"; 16 -> "SATURDAY"; 17 -> "SUNDAY"; else -> "MONDAY" }
                val header = TextView(this).apply {
                    text = "\u2014  $dayLabel  \u2014"
                    setTextColor(0xFF9e9e9e.toInt())
                    textSize = 13f
                    setTypeface(null, Typeface.BOLD)
                    letterSpacing = 0.2f
                    gravity = Gravity.CENTER
                    setPadding(0, dp(20), 0, dp(8))
                }
                content.addView(header)
            }

            val isCurrent = act == currentAct
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(cardBg)
                setPadding(dp(14), dp(12), dp(14), dp(12))
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.bottomMargin = dp(8)
                layoutParams = lp
                alpha = if (isCurrent) 1f else 0.7f
            }

            val fav = if (Favorites.isFav(act.name)) "\u2B50 " else ""

            val titleTv = TextView(this).apply {
                text = "$fav${act.name}"
                setTextColor(color)
                textSize = if (isCurrent) 16f else 14f
                setTypeface(null, if (isCurrent) Typeface.BOLD else Typeface.NORMAL)
            }

            val timeTv = TextView(this).apply {
                text = formatTime(act)
                setTextColor(dimColor)
                textSize = 12f
                setPadding(0, dp(2), 0, 0)
            }

            val descTv = TextView(this).apply {
                text = act.description
                setTextColor(0xFF9e9e9e.toInt())
                textSize = 12f
                setPadding(0, dp(4), 0, 0)
            }

            card.addView(titleTv)
            card.addView(timeTv)
            card.addView(descTv)
            card.addView(createArtistSearchRow(this, act.name))

            if (isCurrent) {
                val nowLabel = TextView(this).apply {
                    text = "\u25B6 NOW PLAYING"
                    setTextColor(0xFFbb86fc.toInt())
                    textSize = 10f
                    letterSpacing = 0.15f
                    setTypeface(null, Typeface.BOLD)
                    setPadding(0, dp(6), 0, 0)
                }
                card.addView(nowLabel)
            }

            card.setOnClickListener { toggleFavWithReminder(act, stage) }
            content.addView(card)
            if (isCurrent) nowView = card
        }
    }

    private fun dp(v: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), resources.displayMetrics).toInt()

    private fun jumpToNow() {
        nowView?.let { scrollView.post { scrollView.smoothScrollTo(0, it.top - dp(50)) } }
    }

    private fun toggleFavWithReminder(act: Act, stage: String) {
        Favorites.toggle(act.name)
        if (Favorites.isFav(act.name)) {
            scheduleReminder(act)
            Toast.makeText(this, "\u2B50 ${act.name} \u2014 reminder set", Toast.LENGTH_SHORT).show()
        } else {
            cancelReminder(act)
            Toast.makeText(this, "Removed ${act.name}", Toast.LENGTH_SHORT).show()
        }
        showStage(stage)
    }

    private fun scheduleReminder(act: Act) {
        try {
            val cal = Calendar.getInstance().apply {
                set(Calendar.MONTH, Calendar.MAY)
                set(Calendar.YEAR, 2026)
                set(Calendar.DAY_OF_MONTH, act.day)
                set(Calendar.HOUR_OF_DAY, act.hour)
                set(Calendar.MINUTE, act.minute)
                set(Calendar.SECOND, 0)
                add(Calendar.MINUTE, -15)
            }
            if (cal.timeInMillis < System.currentTimeMillis()) return
            val intent = Intent(this, ReminderReceiver::class.java).apply {
                putExtra("act_name", act.name)
                putExtra("act_stage", act.stage)
            }
            val pi = PendingIntent.getBroadcast(this, act.name.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
            } else {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.timeInMillis, pi)
            }
        } catch (_: Exception) {}
    }

    private fun cancelReminder(act: Act) {
        val intent = Intent(this, ReminderReceiver::class.java)
        val pi = PendingIntent.getBroadcast(this, act.name.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        (getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(pi)
    }
}
