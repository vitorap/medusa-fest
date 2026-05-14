package com.example.festival

import android.app.*
import android.content.*
import android.graphics.Typeface
import android.os.*
import android.util.TypedValue
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
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
        FavoriteReminders.createNotificationChannel(this)

        listOf("Rage", "Kodama", "Tortuga").forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }
        showStage("Rage")

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { showStage(tab.text.toString()) }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        findViewById<Button>(R.id.btnJumpNow).setOnClickListener { jumpToNow() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
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
        var actIndex = 0
        for (act in acts) {
            if (act.day != lastDay) {
                lastDay = act.day
                val dayLabel = when (act.day) { 15 -> "FRIDAY"; 16 -> "SATURDAY"; 17 -> "SUNDAY"; else -> "MONDAY" }
                val header = TextView(this).apply {
                    text = "\u2014  $dayLabel  \u2014"
                    setTextColor(0xFF9e9e9e.toInt())
                    textSize = 13f
                    setTypeface(null, Typeface.BOLD)
                    letterSpacing = 0f
                    gravity = Gravity.CENTER
                    setPadding(0, dp(20), 0, dp(8))
                }
                content.addView(header)
            }

            val isCurrent = act == currentAct
            val targetAlpha = if (isCurrent) 1f else 0.7f
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(cardBg)
                setPadding(dp(14), dp(12), dp(14), dp(12))
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.bottomMargin = dp(8)
                layoutParams = lp
                alpha = 0f
                translationY = dp(10).toFloat()
            }

            val fav = if (Favorites.isFav(act.name)) "\u2B50 " else ""

            val titleTv = TextView(this).apply {
                text = "$fav${act.name}"
                setTextColor(color)
                textSize = if (isCurrent) 15.5f else 14f
                typeface = Typeface.create("sans-serif-medium", if (isCurrent) Typeface.BOLD else Typeface.NORMAL)
                includeFontPadding = false
            }

            val timeTv = TextView(this).apply {
                text = formatTime(act)
                setTextColor(dimColor)
                textSize = 11.5f
                typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
                includeFontPadding = false
                setPadding(0, dp(2), 0, 0)
            }

            val descTv = TextView(this).apply {
                text = act.description
                setTextColor(0xFF9e9e9e.toInt())
                textSize = 12f
                typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                setPadding(0, dp(4), 0, 0)
            }

            card.addView(titleTv)
            card.addView(timeTv)
            card.addView(descTv)

            val bottomRow = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, dp(8), 0, 0)
            }
            if (isCurrent) {
                val nowLabel = TextView(this).apply {
                    text = "\u25B6 NOW PLAYING"
                    setTextColor(0xFFbb86fc.toInt())
                    textSize = 10f
                    letterSpacing = 0f
                    typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                    includeFontPadding = false
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }
                bottomRow.addView(nowLabel)
            } else {
                bottomRow.addView(Space(this).apply {
                    layoutParams = LinearLayout.LayoutParams(0, 1, 1f)
                })
            }
            bottomRow.addView(createArtistSearchRow(this, act.name, topMarginDp = 0))
            card.addView(bottomRow)

            card.setOnClickListener { toggleFavWithReminder(act, stage) }
            content.addView(card)
            card.animate()
                .alpha(targetAlpha)
                .translationY(0f)
                .setStartDelay((actIndex * 18).coerceAtMost(180).toLong())
                .setDuration(220)
                .setInterpolator(DecelerateInterpolator())
                .start()
            actIndex++
            if (isCurrent) nowView = card
        }
    }

    private fun dp(v: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), resources.displayMetrics).toInt()

    private fun jumpToNow() {
        nowView?.let { scrollView.post { scrollView.smoothScrollTo(0, it.top - dp(50)) } }
    }

    private fun toggleFavWithReminder(act: Act, stage: String) {
        val result = FavoriteReminders.toggle(this, act)
        if (result.isFavorite) {
            val msg = if (result.reminderScheduled) "\u2B50 ${act.name} \u2014 start alert set" else "\u2B50 ${act.name} saved"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Removed ${act.name}", Toast.LENGTH_SHORT).show()
        }
        showStage(stage)
    }
}
