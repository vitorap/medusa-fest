package com.example.festival

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private data class StageViews(
        val stage: String,
        val text: TextView,
        val links: LinearLayout,
        val favorite: TextView
    )

    private val stageViews = mutableListOf<StageViews>()
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Favorites.init(this)
        FavoriteReminders.createNotificationChannel(this)
        FavoriteReminders.requestNotificationPermission(this)

        findViewById<Button>(R.id.btnLineup).setOnClickListener {
            startActivity(Intent(this, LineupActivity::class.java))
        }
        findViewById<Button>(R.id.btnFavorites).setOnClickListener {
            startActivity(Intent(this, FavoritesActivity::class.java))
        }

        val container = findViewById<LinearLayout>(R.id.stageContainer)
        container.addView(createTranceRadarCard())
        stages.forEach { stage -> container.addView(createStageCard(stage)) }
        showWelcome()
        updateDisplay()
    }

    override fun onResume() {
        super.onResume()
        handler.post(ticker)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(ticker)
    }

    private val ticker = object : Runnable {
        override fun run() {
            updateDisplay()
            handler.postDelayed(this, 30_000)
        }
    }

    private fun showWelcome() {
        val prefs = getSharedPreferences("app", MODE_PRIVATE)
        if (prefs.getBoolean("medusa_welcome_shown", false)) return
        prefs.edit().putBoolean("medusa_welcome_shown", true).apply()

        AlertDialog.Builder(this)
            .setTitle("Bem-vindo ao Medusa Fest 2026 ✨")
            .setMessage(
                "Seu guia pessoal para sexta 14 e domingo 16:\n\n" +
                    "• Veja o que está tocando agora nos 9 palcos.\n\n" +
                    "• Abra o RADAR TRANCE para separar trance de verdade dos artistas apenas adjacentes.\n\n" +
                    "• Toque em GUIA DO PALCO para comparar gêneros, clima e intensidade em cada dia.\n\n" +
                    "• Abra LINEUP e toque num artista para favoritar.\n\n" +
                    "• O app agenda um alerta para o início do set.\n\n" +
                    "• Pesquise cada artista no Spotify, YouTube, SoundCloud ou Google.\n\n" +
                    "Os horários seguem a grade oficial do Medusa. Boa festa! 🦋"
            )
            .setPositiveButton("Bora!") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun createTranceRadarCard(): MaterialCardView {
        val card = MaterialCardView(this).apply {
            radius = dp(16).toFloat()
            cardElevation = 0f
            strokeColor = 0xFF62DFFF.toInt()
            strokeWidth = dp(1)
            setCardBackgroundColor(0xFF07171C.toInt())
            isClickable = true
            isFocusable = true
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = dp(12) }
            setOnClickListener { showTranceRadar() }
        }

        card.addView(LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(14), dp(16), dp(14))
            addView(TextView(this@MainActivity).apply {
                text = "🌀 RADAR TRANCE"
                setTextColor(0xFF7FE7FF.toInt())
                textSize = 15f
                typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                includeFontPadding = false
            })
            addView(TextView(this@MainActivity).apply {
                text = "TRANCE EM FOCO · Tiësto + Brenda Serna\nADJACENTES · Pawlowski, Oliver Heldens, Timmy Trumpet, DYEN, Onlynumbers e mais"
                setTextColor(Color.WHITE)
                textSize = 12f
                typeface = Typeface.create("sans-serif", Typeface.NORMAL)
                setLineSpacing(0f, 1.08f)
                setPadding(0, dp(7), 0, 0)
            })
            addView(TextView(this@MainActivity).apply {
                text = "VER ARTISTAS, PALCOS E HORÁRIOS  ›"
                setTextColor(0xFF7FE7FF.toInt())
                textSize = 10.5f
                typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
                setPadding(0, dp(10), 0, 0)
            })
        })
        return card
    }

    private fun showTranceRadar() {
        val confirmed = tranceRadarActs().filter { it.tranceFocus == TranceFocus.TRANCE }
        val adjacent = tranceRadarActs().filter { it.tranceFocus == TranceFocus.ADJACENT }

        fun lines(acts: List<Act>): String = acts.joinToString("\n\n") { act ->
            "${act.name}\n${eventDayLabel(act.eventDay)} · ${formatTime(act)} · ${act.stage}\n${act.tags}"
        }

        val message = "TRANCE EM FOCO\n${lines(confirmed)}\n\n" +
            "RADAR ADJACENTE\n${lines(adjacent)}\n\n" +
            "Adjacente significa que o artista usa melodias, synths ou momentos de trance, mas o set principal pertence a outro gênero. Timmy Trumpet pode incluir psytrance, porém não é um projeto de psytrance puro."

        AlertDialog.Builder(this)
            .setTitle("🌀 Radar Trance")
            .setMessage(message)
            .setNegativeButton("Fechar", null)
            .setPositiveButton("Ver lineup") { _, _ ->
                startActivity(Intent(this, LineupActivity::class.java).putExtra("stage", "Apsaras"))
            }
            .show()
    }

    private fun createStageCard(stage: String): MaterialCardView {
        val card = MaterialCardView(this).apply {
            radius = dp(16).toFloat()
            cardElevation = 0f
            strokeColor = withAlpha(stageColor(stage), 92)
            strokeWidth = dp(1)
            setCardBackgroundColor(Color.BLACK)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                dp(194)
            ).apply { bottomMargin = dp(10) }
        }

        val frame = FrameLayout(this)
        frame.addView(ImageView(this).apply {
            setImageResource(stageImage(stage))
            scaleType = ImageView.ScaleType.CENTER_CROP
            contentDescription = null
        }, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        frame.addView(View(this).apply { setBackgroundResource(R.drawable.home_stage_scrim) },
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))

        val content = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(16), dp(13), dp(14), dp(12))
        }

        content.addView(TextView(this).apply {
            text = stage.uppercase()
            setTextColor(stageColor(stage))
            textSize = 15f
            typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
            includeFontPadding = false
        })

        content.addView(TextView(this).apply {
            text = stageGuide(stage).genres
            setTextColor(stageColor(stage))
            alpha = 0.78f
            textSize = 10.5f
            typeface = Typeface.create("sans-serif-medium", Typeface.NORMAL)
            includeFontPadding = false
            maxLines = 1
            ellipsize = android.text.TextUtils.TruncateAt.END
            setPadding(0, dp(2), 0, 0)
        })

        val status = TextView(this).apply {
            setTextColor(Color.WHITE)
            textSize = 12.5f
            typeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
            includeFontPadding = false
            maxLines = 5
            setPadding(0, dp(4), dp(96), 0)
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f)
        }
        content.addView(status)

        val bottom = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
        }
        val guide = TextView(this).apply {
            text = "ⓘ GUIA DO PALCO"
            setTextColor(stageColor(stage))
            textSize = 10f
            typeface = Typeface.create("sans-serif-medium", Typeface.BOLD)
            gravity = Gravity.CENTER_VERTICAL
            setOnClickListener { showStageGuide(stage) }
        }
        val favorite = TextView(this).apply {
            gravity = Gravity.CENTER
            setTextColor(0xFFFFD36A.toInt())
            textSize = 21f
            setPadding(0, 0, dp(10), 0)
        }
        val links = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        bottom.addView(guide, LinearLayout.LayoutParams(0, dp(30), 1f))
        bottom.addView(favorite, LinearLayout.LayoutParams(dp(34), dp(30)))
        bottom.addView(links)
        content.addView(bottom)

        frame.addView(content, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
        card.addView(frame)
        stageViews += StageViews(stage, status, links, favorite)
        return card
    }

    private fun showStageGuide(stage: String) {
        val guide = stageGuide(stage)
        val message = "GÊNEROS\n${guide.genres}\n\n" +
            "CLIMA\n${guide.vibe}\n\n" +
            "SEXTA 14\n${guide.friday}\n\n" +
            "DOMINGO 16\n${guide.sunday}\n\n" +
            "VÁ SE…\n${guide.chooseIf}\n\n" +
            "INTENSIDADE\n${guide.intensity}"

        AlertDialog.Builder(this)
            .setTitle(stage)
            .setMessage(message)
            .setNegativeButton("Fechar", null)
            .setPositiveButton("Ver lineup") { _, _ ->
                startActivity(Intent(this, LineupActivity::class.java).putExtra("stage", stage))
            }
            .show()
    }

    private fun updateDisplay() {
        val now = System.currentTimeMillis()
        stageViews.forEach { holder ->
            val stageActs = lineup.filter { it.stage == holder.stage }.sortedBy(::actMinutes)
            val current = stageActs.firstOrNull { isActLive(it, now) }
            val upcoming = stageActs.firstOrNull { actStartMillis(it) > now }
            val shown = current ?: upcoming

            if (shown == null) {
                holder.text.text = "Programação encerrada\n${stageSubtitle(holder.stage)}"
                holder.favorite.visibility = View.GONE
                holder.links.removeAllViews()
                return@forEach
            }

            val target = if (current != null) actEndMillis(shown) else actStartMillis(shown)
            val countdown = countdown(target - now)
            val focusLine = if (shown.tranceFocus != TranceFocus.NONE) shown.tranceFocus.label else shown.tags
            holder.text.text = if (current != null) {
                "AGORA · ${shown.name}\n${formatTime(shown)} · termina em $countdown\n$focusLine\n${shown.description}"
            } else {
                "PRÓXIMO · ${shown.name}\n${eventDayLabel(shown.eventDay)} · ${formatTime(shown)} · em $countdown\n$focusLine\n${shown.description}"
            }
            updateActions(holder, shown)
        }
    }

    private fun updateActions(holder: StageViews, act: Act) {
        holder.favorite.visibility = View.VISIBLE
        holder.favorite.text = if (Favorites.isFav(act)) "★" else "☆"
        holder.favorite.contentDescription = if (Favorites.isFav(act)) {
            "Remover ${act.name} dos favoritos"
        } else {
            "Adicionar ${act.name} aos favoritos"
        }
        holder.favorite.setOnClickListener {
            val result = FavoriteReminders.toggle(this, act)
            val message = when {
                !result.isFavorite -> "${act.name} removido"
                result.reminderScheduled -> "⭐ ${act.name} · alerta agendado"
                else -> "⭐ ${act.name} salvo"
            }
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            updateDisplay()
        }

        if (holder.links.tag == act.name) return
        holder.links.removeAllViews()
        holder.links.tag = act.name
        holder.links.addView(createArtistSearchRow(this, act.name, topMarginDp = 0))
    }

    private fun countdown(deltaMillis: Long): String {
        val totalMinutes = ceil(deltaMillis.coerceAtLeast(0) / 60_000.0).toLong()
        val days = totalMinutes / (24 * 60)
        val hours = (totalMinutes % (24 * 60)) / 60
        val minutes = totalMinutes % 60
        return when {
            days > 0 -> "${days}d ${hours}h"
            hours > 0 -> "${hours}h ${minutes}min"
            else -> "${minutes}min"
        }
    }

    private fun withAlpha(color: Int, alpha: Int): Int = Color.argb(
        alpha,
        Color.red(color),
        Color.green(color),
        Color.blue(color)
    )

    private fun dp(value: Int): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(),
        resources.displayMetrics
    ).toInt()
}
