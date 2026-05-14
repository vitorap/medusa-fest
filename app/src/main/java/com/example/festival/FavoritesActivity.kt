package com.example.festival

import android.os.Bundle
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FavoritesActivity : AppCompatActivity() {
    private lateinit var content: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        Favorites.init(this)
        FavoriteReminders.createNotificationChannel(this)
        content = findViewById(R.id.favoritesContent)
        findViewById<Button>(R.id.btnBackFavorites).setOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        renderFavorites()
    }

    private fun renderFavorites() {
        content.removeAllViews()
        val favorites = lineup.filter { Favorites.isFav(it.name) }.sortedBy { actMinutes(it) }

        if (favorites.isEmpty()) {
            content.addView(TextView(this).apply {
                text = "No favorites yet"
                setTextColor(0xFF9e9e9e.toInt())
                textSize = 14f
                gravity = Gravity.CENTER
                setPadding(0, dp(36), 0, 0)
            })
            return
        }

        favorites.forEachIndexed { index, act ->
            val color = stageColor(act.stage)
            val dimColor = stageDimColor(act.stage)
            val card = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setBackgroundResource(stageCardBg(act.stage))
                setPadding(dp(14), dp(12), dp(14), dp(12))
                alpha = 0f
                translationY = dp(8).toFloat()
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = dp(8) }
            }

            card.addView(TextView(this).apply {
                text = "★ ${act.name}"
                setTextColor(color)
                textSize = 14.5f
                typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                includeFontPadding = false
            })
            card.addView(TextView(this).apply {
                text = "${act.stage} - Day ${act.day} - ${formatTime(act)}"
                setTextColor(dimColor)
                textSize = 11.5f
                typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
                includeFontPadding = false
                setPadding(0, dp(2), 0, 0)
            })
            card.addView(TextView(this).apply {
                text = act.description
                setTextColor(0xFF9e9e9e.toInt())
                textSize = 12f
                typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                setPadding(0, dp(4), 0, 0)
            })

            val actions = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(0, dp(8), 0, 0)
            }
            actions.addView(TextView(this).apply {
                text = "Remove"
                setTextColor(0xFFbb86fc.toInt())
                textSize = 11f
                typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                setPadding(0, 0, dp(14), 0)
                layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                setOnClickListener {
                    FavoriteReminders.toggle(this@FavoritesActivity, act)
                    Toast.makeText(this@FavoritesActivity, "Removed ${act.name}", Toast.LENGTH_SHORT).show()
                    renderFavorites()
                }
            })
            actions.addView(createArtistSearchRow(this, act.name, topMarginDp = 0))
            card.addView(actions)

            content.addView(card)
            card.animate()
                .alpha(1f)
                .translationY(0f)
                .setStartDelay((index * 18).coerceAtMost(160).toLong())
                .setDuration(200)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }

    private fun stageColor(stage: String): Int = when (stage) {
        "Rage" -> 0xFFff6b6b.toInt()
        "Kodama" -> 0xFF69f0ae.toInt()
        else -> 0xFFffd740.toInt()
    }

    private fun stageDimColor(stage: String): Int = when (stage) {
        "Rage" -> 0xFF993333.toInt()
        "Kodama" -> 0xFF338855.toInt()
        else -> 0xFF997722.toInt()
    }

    private fun stageCardBg(stage: String): Int = when (stage) {
        "Rage" -> R.drawable.card_rage
        "Kodama" -> R.drawable.card_kodama
        else -> R.drawable.card_tortuga
    }

    private fun dp(v: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), resources.displayMetrics).toInt()
    }
}
