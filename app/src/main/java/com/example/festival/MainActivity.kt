package com.example.festival

import android.app.*
import android.content.*
import android.os.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

data class Act(val stage: String, val day: Int, val hour: Int, val minute: Int, val name: String, val description: String)

val lineup = listOf(
    Act("Rage", 15, 20, 0, "Karmn'eugen / Necropsycho", "Iconic Brazilian Darkpsy project known for organic sounds and deep rituals."),
    Act("Rage", 16, 0, 0, "Kasatka", "High-intensity Darkpsy/Hi-Tech with metallic and aggressive sounds."),
    Act("Rage", 16, 2, 0, "Infra Retro", "Project focused on classic Darkpsy nocturnal atmospheres."),
    Act("Rage", 16, 4, 0, "Tzu Jan", "Fast and experimental sonorities within the Dark/Hi-Tech spectrum."),
    Act("Rage", 16, 6, 0, "Absurdus", "Pure Hi-Tech focused on extreme speed and technical precision."),
    Act("Rage", 16, 8, 0, "Varasurdum (Varazslo & Absurdus)", "Collaboration focused on high speed and acidic textures."),
    Act("Rage", 16, 9, 0, "Varazslo", "Darkpsy focused on hypnotic, constant and immersive grooves."),
    Act("Rage", 16, 12, 0, "Killatk", "One of the biggest names in world Hi-Tech, known for aggressive drops and frenetic rhythms."),
    Act("Rage", 16, 14, 0, "Mentalecho", "Melodic and complex Hi-Tech with live guitar in this set."),
    Act("Rage", 16, 16, 0, "Psynonima", "Spanish Hi-Tech of extreme speed, energy and power."),
    Act("Rage", 16, 18, 0, "Maiko", "Deep Darkpsy with Forest elements and dense atmospheres."),
    Act("Rage", 16, 20, 0, "Tetraktyz", "Experimental project focused on sonic geometry and fast rhythms."),
    Act("Rage", 16, 22, 0, "Will O Wisp", "Technical Darkpsy focused on modular, abstract and futuristic sound design."),
    Act("Rage", 17, 0, 0, "Megalopsy", "Argentine Darkpsy giant with dense, mental and highly psychedelic sound."),
    Act("Rage", 17, 2, 0, "Zik", "Greek Darkpsy master focused on powerful, dark and serious sonorities."),
    Act("Rage", 17, 4, 0, "Orestis", "Greek Dark/Forest reference known for extreme technical precision and deep atmospheres."),
    Act("Rage", 17, 6, 0, "Zikore (Zik & Orestis)", "Collaboration between two Greek titans uniting technical mastery and complex grooves."),
    Act("Rage", 17, 8, 0, "Osom & Zikore", "Special collaborative set uniting Osom Music powers with the Greek sound."),
    Act("Rage", 17, 9, 0, "Osom (Kindzadza & Psykovsky)", "Pure experimentalism, non-linear rhythms and maximum complexity."),
    Act("Rage", 17, 11, 0, "Kindzadza", "The father of Hi-Tech, famous for experimental, ultra-fast and alien sounds."),
    Act("Rage", 17, 13, 0, "Psykovsky", "The Maestro of Darkpsy; creates long complex narratives mixing classic and abstract."),
    Act("Rage", 17, 15, 0, "Sectio Aurea", "Obscure Swiss Darkpsy, highly technical with refined sound design."),
    Act("Rage", 17, 18, 0, "Altura (Sectio Aurea & Necropsycho)", "Collaboration uniting Swiss technicality with Brazilian organic ritualism."),
    Act("Rage", 17, 19, 0, "Tzu Jan & Necropsycho", "Partnership focused on intense experimentation and accelerated rhythms."),
    Act("Rage", 17, 21, 0, "Necrowillo (Necropsycho & Will O Wisp)", "Union of two of the greatest sound designers in world Darkpsy."),
    Act("Rage", 17, 22, 0, "Psykovsky (Retro Set)", "Special set focused on the roots and classic productions of the project."),
    Act("Rage", 18, 1, 0, "Kindzadza (Retro Set)", "Exploration of the pioneering sounds that gave birth to Hi-Tech."),
    Act("Rage", 18, 4, 0, "Alpscore", "Cutting-edge Italian Hi-Tech focused on sonic clarity and dancefloor energy."),
    Act("Rage", 18, 6, 0, "Maramba", "Brazilian Forest/Dark reference with high-quality organic sounds."),
    Act("Rage", 18, 8, 0, "Aquarius Orb", "Introspective, hypnotic Darkpsy focused on trance states."),
    Act("Rage", 18, 10, 0, "Paula", "DJ focused on high-energy sets and refined curation of national Forest and Darkpsy."),
    Act("Rage", 18, 12, 0, "Booo", "DJ focused on high-energy sets and refined curation of national Forest and Darkpsy."),
    Act("Rage", 18, 14, 0, "Anginha", "DJ focused on high-energy sets and refined curation of national Forest and Darkpsy."),
    Act("Kodama", 15, 21, 0, "Purist", "Pure Forest Psytrance with focus on jungle textures and organic grooves."),
    Act("Kodama", 15, 23, 0, "Ananga Ranga", "Brazilian Forest focused on spirituality and nature sounds."),
    Act("Kodama", 16, 1, 0, "Tera", "Nocturnal Psytrance ranging between deep Forest and Twilight."),
    Act("Kodama", 16, 3, 0, "Metatron", "Nocturnal Psytrance ranging between deep Forest and Twilight."),
    Act("Kodama", 16, 5, 0, "Cyk", "Nocturnal Psytrance ranging between deep Forest and Twilight."),
    Act("Kodama", 16, 7, 0, "Konebu", "Nocturnal Psytrance ranging between deep Forest and Twilight."),
    Act("Kodama", 16, 9, 0, "Del Torto", "Brazilian nocturnal Psytrance with striking and enveloping basslines."),
    Act("Kodama", 16, 11, 0, "Mysterion", "Forest/Dark with dense and mysterious atmospheres."),
    Act("Kodama", 16, 13, 0, "Procs (Retro Set)", "Legendary Swede; playful, peculiar sound full of elastic timbres."),
    Act("Kodama", 16, 15, 0, "Abyss Ooze", "Deep, viscous Forest with total sonic immersion."),
    Act("Kodama", 16, 17, 0, "Encephalopaticys", "Macedonian Darkpsy focused on broken and complex rhythms."),
    Act("Kodama", 16, 19, 0, "Traskel", "Classic Swedish Forest focused on sonic hypnosis and forest atmospheres."),
    Act("Kodama", 16, 21, 0, "Hallucinogenic Horses (Traskel & Derango)", "One of the most legendary Forest projects in the world; dense and melodic."),
    Act("Kodama", 16, 23, 0, "Derango", "Swedish Forest pioneers; the sound that defined the genre aesthetic."),
    Act("Kodama", 17, 1, 0, "The Surrealist Committee (Megalopsy, Elowinz & Derango)", "Supergroup uniting Darkpsy and Forest from three generations."),
    Act("Kodama", 17, 2, 0, "Farebi Jalebi", "Groovy, bouncy Forest full of Indian sonic personality."),
    Act("Kodama", 17, 5, 0, "Atriohm", "High-quality Forest reference with deep textures and epic atmospheres."),
    Act("Kodama", 17, 7, 0, "Arjuna", "Root Forest balancing percussive rhythms with hypnotic Goa atmospheres."),
    Act("Kodama", 17, 9, 0, "Elowinz", "Brazilian highlight in world Forest, focused on modular synthesis and organic sounds."),
    Act("Kodama", 17, 11, 0, "Mubali", "Intelligent nocturnal sound full of glitches and mechanical grooves."),
    Act("Kodama", 17, 13, 0, "Giuseppe", "Founder of Parvati Records and master curator of traditional Forest sound."),
    Act("Kodama", 17, 15, 0, "Antonymous", "Atmospheric Twilight/Forest focused on guiding the audience through a mental ascension."),
    Act("Kodama", 17, 17, 0, "Quadraphonic", "Greek project focused on powerful, clean and high-energy nocturnal Psytrance."),
    Act("Kodama", 17, 19, 0, "Tromo", "Greek project focused on powerful, clean and high-energy nocturnal Psytrance."),
    Act("Kodama", 17, 21, 0, "Gesh", "Nocturnal Psytrance focused on constant dancefloor grooves."),
    Act("Kodama", 17, 23, 0, "Arjuna & Sutemi", "Collaboration focused on a more aggressive, dynamic and danceable Forest."),
    Act("Kodama", 18, 1, 0, "Arkanum", "Technical Darkpsy focused on nocturnal rituals and immersion."),
    Act("Kodama", 18, 3, 0, "Terratech", "Technical Darkpsy focused on nocturnal rituals and immersion."),
    Act("Kodama", 18, 5, 0, "Alien Trancesistor", "DJ and Vantara Vichitra curator, master of deep and mental Forest."),
    Act("Kodama", 18, 7, 0, "Oak Tales", "Meditative, deep Forest full of sonic stories."),
    Act("Kodama", 18, 9, 30, "Ghitta", "National representative focused on Forest and Night Psytrance sets."),
    Act("Kodama", 18, 12, 0, "D Psy", "National representative focused on Forest and Night Psytrance sets."),
    Act("Tortuga", 15, 18, 0, "Eder FM", "Dub and sound system sonorities for the opening."),
    Act("Tortuga", 16, 10, 0, "Zion Groove", "Project focused on Dub, Reggae and slow psychedelic beats."),
    Act("Tortuga", 16, 12, 0, "Avan7 in Dub", "Project focused on Dub, Reggae and slow psychedelic beats."),
    Act("Tortuga", 16, 14, 0, "Giuseppe in Dub", "Dub and Downtempo version from the Parvati Records owner."),
    Act("Tortuga", 16, 16, 0, "Disfunction", "Techno or Minimal strands oriented towards psychedelia."),
    Act("Tortuga", 16, 18, 0, "Klipsun", "Techno or Minimal strands oriented towards psychedelia."),
    Act("Tortuga", 16, 20, 0, "Farebi Drum Bass", "The Drum & Bass and broken beats side of the Farebi Jalebi project."),
    Act("Tortuga", 17, 8, 0, "Prisma by Megalopsy", "The meditative, Ambient and Downtempo side of Megalopsy."),
    Act("Tortuga", 17, 10, 0, "Rica Amaral Chill Out", "Special set from the Brazilian master focused on ambient and immersive music."),
    Act("Tortuga", 17, 12, 0, "Atriohm Chill Out", "The calm forest and meditative side of the Atriohm project."),
    Act("Tortuga", 17, 14, 0, "Cidade Verde Sounds", "National Reggae and Dub with message and groove."),
    Act("Tortuga", 17, 16, 20, "Ventania & Banda Hippie", "Icon of Brazilian folk/psychedelic music."),
    Act("Tortuga", 17, 18, 0, "Treant on Tek", "Slow, dense experimental strands from nocturnal sound producers."),
    Act("Tortuga", 17, 20, 0, "Obsidian Mirror", "Slow, dense experimental strands from nocturnal sound producers."),
    Act("Tortuga", 18, 10, 0, "Zion Gate", "Dub and low-frequency rhythms for the stage closing.")
)

fun actMinutes(act: Act): Int = act.day * 24 * 60 + act.hour * 60 + act.minute

fun getNextAct(act: Act): Act? {
    val stageActs = lineup.filter { it.stage == act.stage }.sortedBy { actMinutes(it) }
    val idx = stageActs.indexOf(act)
    return if (idx < stageActs.size - 1) stageActs[idx + 1] else null
}

fun getEndTime(act: Act): String {
    val next = getNextAct(act)
    return if (next != null) "%02d:%02d".format(next.hour, next.minute) else "END"
}

fun formatTime(act: Act): String = "%02d:%02d-%s".format(act.hour, act.minute, getEndTime(act))

object Favorites {
    private const val PREF = "favorites"
    private lateinit var prefs: SharedPreferences
    fun init(ctx: Context) { prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE) }
    fun isFav(name: String) = prefs.getBoolean(name, false)
    fun toggle(name: String) { prefs.edit().putBoolean(name, !isFav(name)).apply() }
}

class MainActivity : AppCompatActivity() {
    private lateinit var tvRage: TextView
    private lateinit var tvKodama: TextView
    private lateinit var tvTortuga: TextView
    private lateinit var linksRage: LinearLayout
    private lateinit var linksKodama: LinearLayout
    private lateinit var linksTortuga: LinearLayout
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Favorites.init(this)
        tvRage = findViewById(R.id.tvRage)
        tvKodama = findViewById(R.id.tvKodama)
        tvTortuga = findViewById(R.id.tvTortuga)
        linksRage = findViewById(R.id.linksRage)
        linksKodama = findViewById(R.id.linksKodama)
        linksTortuga = findViewById(R.id.linksTortuga)
        findViewById<Button>(R.id.btnLineup).setOnClickListener {
            startActivity(Intent(this, LineupActivity::class.java))
        }
        showWelcome()
        updateDisplay()
    }

    override fun onResume() { super.onResume(); handler.post(ticker) }
    override fun onPause() { super.onPause(); handler.removeCallbacks(ticker) }

    private val ticker = object : Runnable {
        override fun run() { updateDisplay(); handler.postDelayed(this, 30_000) }
    }

    private fun showWelcome() {
        val prefs = getSharedPreferences("app", MODE_PRIVATE)
        if (prefs.getBoolean("welcome_shown", false)) return
        prefs.edit().putBoolean("welcome_shown", true).apply()

        AlertDialog.Builder(this)
            .setTitle("Welcome to MOP Brasil! \uD83C\uDFB5")
            .setMessage(
                "Here are some tips:\n\n" +
                "\u2022 This screen shows what's playing NOW on each stage with a countdown timer.\n\n" +
                "\u2022 Tap \"Full Lineup\" to see all acts. Tap any act to \u2B50 favorite it \u2014 you'll get a notification 15 minutes before it starts.\n\n" +
                "\u2022 Tap again to unfavorite and cancel the reminder.\n\n" +
                "\u2022 Use \"Jump to Now\" in the lineup to scroll to the current act.\n\n" +
                "\u2022 A home screen widget is available! Long-press your home screen \u2192 Widgets \u2192 Master Of Puppets.\n\n" +
                "Enjoy the festival! \uD83D\uDD25\uD83D\uDC80"
            )
            .setPositiveButton("Got it!") { d, _ -> d.dismiss() }
            .show()
    }

    private fun updateDisplay() {
        val cal = Calendar.getInstance()
        val nowMin = cal.get(Calendar.DAY_OF_MONTH) * 24 * 60 + cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)
        updateStageCard("Rage", nowMin, tvRage, linksRage)
        updateStageCard("Kodama", nowMin, tvKodama, linksKodama)
        updateStageCard("Tortuga", nowMin, tvTortuga, linksTortuga)
    }

    private fun updateStageCard(stage: String, nowMin: Int, textView: TextView, links: LinearLayout) {
        val current = getCurrentAct(stage, nowMin)
        textView.text = getNowPlaying(stage, nowMin, current)
        links.removeAllViews()
        if (current != null) links.addView(createArtistSearchRow(this, current.name))
    }

    private fun getCurrentAct(stage: String, nowMin: Int): Act? {
        val stageActs = lineup.filter { it.stage == stage }
        return stageActs.filter { actMinutes(it) <= nowMin }.maxByOrNull { actMinutes(it) }
    }

    private fun getNowPlaying(stage: String, nowMin: Int, current: Act?): String {
        if (current == null) return "\uD83C\uDFB5 $stage Stage\n\nNo act playing yet"
        val next = getNextAct(current)
        val endMin = if (next != null) actMinutes(next) else actMinutes(current) + 120
        val remaining = endMin - nowMin
        val countdown = if (remaining > 0) "${remaining}min left" else "ending"
        val fav = if (Favorites.isFav(current.name)) " \u2B50" else ""
        val nextInfo = if (next != null) "\n\n\u23ED Next: ${next.name} at %02d:%02d".format(next.hour, next.minute) else ""
        return "\uD83C\uDFB5 $stage Stage\n\n${current.name}$fav  [${formatTime(current)}]\n\u23F1 $countdown\n\n${current.description}$nextInfo"
    }
}
