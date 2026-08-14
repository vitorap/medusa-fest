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

        stages.forEach { tabLayout.addTab(tabLayout.newTab().setText(it)) }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) { showStage(tab.text.toString()) }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        val initialStage = intent.getStringExtra("stage")?.takeIf { it in stages } ?: stages.first()
        val initialIndex = stages.indexOf(initialStage)
        if (tabLayout.selectedTabPosition == initialIndex) {
            showStage(initialStage)
        } else {
            tabLayout.getTabAt(initialIndex)?.select()
        }

        findViewById<Button>(R.id.btnJumpNow).setOnClickListener { jumpToNow() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { finish() }
    }

    private var nowView: android.view.View? = null

    private fun showStage(stage: String) {
        content.removeAllViews()
        nowView = null
        val acts = lineup.filter { it.stage == stage }.sortedBy { actMinutes(it) }
        val now = System.currentTimeMillis()
        val currentAct = acts.firstOrNull { isActLive(it, now) }
        val color = stageColor(stage)
        val dimColor = stageDimColor(stage)
        val cardBg = stageCardBg(stage)

        content.addView(createStageGuideCard(stage, color, cardBg))

        var lastDay = -1
        var actIndex = 0
        for (act in acts) {
            if (act.eventDay != lastDay) {
                lastDay = act.eventDay
                val dayLabel = eventDayLabel(act.eventDay)
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

            val fav = if (Favorites.isFav(act)) "\u2B50 " else ""

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

            val tagsTv = TextView(this).apply {
                text = act.tags
                setTextColor(0xFFE7E7E7.toInt())
                textSize = 11.5f
                typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
                includeFontPadding = false
                setPadding(0, dp(5), 0, 0)
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
            if (act.tranceFocus != TranceFocus.NONE) {
                card.addView(TextView(this).apply {
                    text = act.tranceFocus.label
                    setTextColor(if (act.tranceFocus == TranceFocus.TRANCE) 0xFF7FE7FF.toInt() else 0xFFBEA7FF.toInt())
                    textSize = 10.5f
                    typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                    includeFontPadding = false
                    setPadding(0, dp(6), 0, 0)
                })
            }
            card.addView(tagsTv)
            card.addView(descTv)

            val bottomRow = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, dp(8), 0, 0)
            }
            if (isCurrent) {
                val nowLabel = TextView(this).apply {
                    text = "\u25B6 TOCANDO AGORA"
                    setTextColor(0xFFFFD36A.toInt())
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

    private fun createStageGuideCard(stage: String, color: Int, cardBg: Int): LinearLayout {
        val guide = stageGuide(stage)
        return LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundResource(cardBg)
            setPadding(dp(16), dp(14), dp(16), dp(14))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = dp(12)
                bottomMargin = dp(4)
            }

            addView(TextView(this@LineupActivity).apply {
                text = "COMO É ESTE PALCO"
                setTextColor(color)
                textSize = 11f
                typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                letterSpacing = 0.08f
            })
            addView(TextView(this@LineupActivity).apply {
                text = guide.genres
                setTextColor(0xFFFFFFFF.toInt())
                textSize = 14f
                typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                setPadding(0, dp(5), 0, 0)
            })
            addView(TextView(this@LineupActivity).apply {
                text = "${guide.vibe}\n\nSEXTA 14 · ${guide.friday}\n\nDOMINGO 16 · ${guide.sunday}\n\nVÁ SE… ${guide.chooseIf}\n\n${guide.intensity}"
                setTextColor(0xFFCBCBCB.toInt())
                textSize = 12.5f
                typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                setLineSpacing(0f, 1.08f)
                setPadding(0, dp(7), 0, 0)
            })
            addView(TextView(this@LineupActivity).apply {
                text = "Guia prático baseado no lineup e nos takeovers oficiais; o estilo de cada set pode variar."
                setTextColor(0xFF7F8E89.toInt())
                textSize = 10f
                setPadding(0, dp(9), 0, 0)
            })
        }
    }

    private fun dp(v: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), resources.displayMetrics).toInt()

    private fun jumpToNow() {
        nowView?.let { scrollView.post { scrollView.smoothScrollTo(0, it.top - dp(50)) } }
    }

    private fun toggleFavWithReminder(act: Act, stage: String) {
        val result = FavoriteReminders.toggle(this, act)
        if (result.isFavorite) {
            val msg = if (result.reminderScheduled) "\u2B50 ${act.name} \u2014 alerta agendado" else "\u2B50 ${act.name} salvo"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "${act.name} removido", Toast.LENGTH_SHORT).show()
        }
        showStage(stage)
    }
}
