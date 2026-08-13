package com.example.festival

import android.content.Context
import android.content.SharedPreferences
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

data class Act(
    val stage: String,
    val eventDay: Int,
    val day: Int,
    val hour: Int,
    val minute: Int,
    val durationMinutes: Int,
    val name: String,
    val description: String
)

val stages = listOf(
    "Apsaras",
    "Resonance",
    "Arcade Land",
    "Beyond",
    "Dharma",
    "Beach Club",
    "Vertigo",
    "Church Club",
    "Poliakow Club"
)

fun stageSubtitle(stage: String): String = when (stage) {
    "Apsaras" -> "Mainstage · EDM & big room"
    "Resonance" -> "Techno & hard techno"
    "Arcade Land" -> "Hardstyle & hardcore"
    "Beyond" -> "Remember & dance classics"
    "Dharma" -> "Exclusive stage takeovers"
    "Beach Club" -> "House & club sounds"
    "Vertigo" -> "Underground club"
    "Church Club" -> "Medusa community sets"
    else -> "Local club & electronic"
}

fun stageColor(stage: String): Int = when (stage) {
    "Apsaras" -> 0xFFFFD36A.toInt()
    "Resonance" -> 0xFFFFC247.toInt()
    "Arcade Land" -> 0xFFFF7EB6.toInt()
    "Beyond" -> 0xFF68C9FF.toInt()
    "Dharma" -> 0xFFFF9B63.toInt()
    "Beach Club" -> 0xFFBEFF55.toInt()
    "Vertigo" -> 0xFFE8E8E8.toInt()
    "Church Club" -> 0xFFDFA7FF.toInt()
    else -> 0xFF69E6F2.toInt()
}

fun stageDimColor(stage: String): Int = when (stage) {
    "Apsaras" -> 0xFFB89542.toInt()
    "Resonance" -> 0xFFB17A21.toInt()
    "Arcade Land" -> 0xFFB64E7E.toInt()
    "Beyond" -> 0xFF357FA6.toInt()
    "Dharma" -> 0xFFAD5B35.toInt()
    "Beach Club" -> 0xFF6E9B2D.toInt()
    "Vertigo" -> 0xFF8C8C8C.toInt()
    "Church Club" -> 0xFF8E5AA8.toInt()
    else -> 0xFF338B94.toInt()
}

fun stageCardBg(stage: String): Int = when (stage) {
    "Apsaras", "Dharma", "Beach Club" -> R.drawable.card_organic
    "Arcade Land", "Beyond", "Church Club" -> R.drawable.card_neon
    else -> R.drawable.card_techno
}

fun stageImage(stage: String): Int = when (stage) {
    "Apsaras", "Dharma", "Beach Club" -> R.drawable.stage_organic
    "Arcade Land", "Beyond", "Church Club" -> R.drawable.stage_neon
    else -> R.drawable.stage_techno
}

private fun act(
    stage: String,
    eventDay: Int,
    day: Int,
    start: String,
    durationMinutes: Int,
    name: String,
    description: String = stageSubtitle(stage)
): Act {
    val (hour, minute) = start.split(":").map(String::toInt)
    return Act(stage, eventDay, day, hour, minute, durationMinutes, name, description)
}

// Official timetable for the two festival days in Vitor's itinerary: Friday 14 and Sunday 16.
// Acts after midnight use their real calendar day (15 or 17) while eventDay keeps the poster day.
val lineup = listOf(
    // Friday 14 · Apsaras
    act("Apsaras", 14, 14, "19:00", 60, "Hektor Mass"),
    act("Apsaras", 14, 14, "20:00", 60, "Rello"),
    act("Apsaras", 14, 14, "21:00", 120, "Wade"),
    act("Apsaras", 14, 14, "23:30", 60, "Miss Monique", "Opening ceremony · Mainstage"),
    act("Apsaras", 14, 15, "00:30", 60, "HALŌ"),
    act("Apsaras", 14, 15, "01:30", 90, "Dimitri Vegas"),
    act("Apsaras", 14, 15, "03:00", 60, "DJs From Mars"),
    act("Apsaras", 14, 15, "04:00", 60, "MANDY"),
    act("Apsaras", 14, 15, "05:00", 60, "Energy Time"),

    // Friday 14 · Resonance
    act("Resonance", 14, 14, "18:00", 120, "Luxi Villar"),
    act("Resonance", 14, 14, "20:00", 120, "Fatima Hajji"),
    act("Resonance", 14, 14, "22:00", 120, "Pawlowski"),
    act("Resonance", 14, 15, "00:00", 120, "Nico Moreno"),
    act("Resonance", 14, 15, "02:00", 120, "Vendex"),
    act("Resonance", 14, 15, "04:00", 120, "Fantasm"),

    // Friday 14 · Arcade Land
    act("Arcade Land", 14, 14, "18:00", 60, "Papero"),
    act("Arcade Land", 14, 14, "19:00", 60, "Yeyo"),
    act("Arcade Land", 14, 14, "20:00", 60, "Hnos. Kapiya"),
    act("Arcade Land", 14, 14, "21:00", 60, "Wakan B2B Dr. Evil"),
    act("Arcade Land", 14, 14, "22:00", 60, "Bassdrum Project & Ogalla"),
    act("Arcade Land", 14, 14, "23:00", 60, "Rebelion"),
    act("Arcade Land", 14, 15, "00:00", 60, "Sound Rush"),
    act("Arcade Land", 14, 15, "01:00", 60, "Phuture Noize"),
    act("Arcade Land", 14, 15, "02:00", 60, "D-Sturb"),
    act("Arcade Land", 14, 15, "03:00", 60, "Wildstylez"),
    act("Arcade Land", 14, 15, "04:00", 45, "Gunz For Hire"),
    act("Arcade Land", 14, 15, "04:45", 75, "Partyraiser"),

    // Friday 14 · Beyond
    act("Beyond", 14, 14, "17:00", 90, "Miguel Moore"),
    act("Beyond", 14, 14, "18:30", 60, "Ismael Lora"),
    act("Beyond", 14, 14, "19:30", 60, "Coqui Selection"),
    act("Beyond", 14, 14, "20:30", 75, "Chumi DJ"),
    act("Beyond", 14, 14, "21:45", 75, "Javi Boss"),
    act("Beyond", 14, 14, "23:00", 60, "DJ Marta"),
    act("Beyond", 14, 15, "00:00", 60, "Rafa XL"),
    act("Beyond", 14, 15, "01:05", 145, "Raul Ortiz", "Intro 01:00 · Remember & dance classics"),
    act("Beyond", 14, 15, "03:30", 60, "Miguel Serna"),
    act("Beyond", 14, 15, "04:30", 90, "Nuria Jump"),

    // Friday 14 · Dharma
    act("Dharma", 14, 14, "17:00", 720, "BRESH", "Exclusive stage takeover · 17:00–05:00"),

    // Friday 14 · Beach Club
    act("Beach Club", 14, 14, "19:00", 60, "DJ German", "Techno Flamenco takeover"),
    act("Beach Club", 14, 14, "20:00", 60, "Eloy GC B2B Erik Romero", "Techno Flamenco takeover"),
    act("Beach Club", 14, 14, "21:00", 60, "Ian Tules", "Techno Flamenco takeover"),
    act("Beach Club", 14, 14, "22:00", 240, "Marsal Ventura", "Techno Flamenco takeover"),
    act("Beach Club", 14, 15, "02:00", 60, "Los Prados", "Techno Flamenco takeover"),
    act("Beach Club", 14, 15, "03:00", 60, "Dany BPM", "Techno Flamenco takeover"),
    act("Beach Club", 14, 15, "04:00", 60, "Pomata", "Techno Flamenco takeover"),

    // Friday 14 · Vertigo
    act("Vertigo", 14, 14, "17:00", 150, "Mireia CJ"),
    act("Vertigo", 14, 14, "19:30", 105, "Paula Fields"),
    act("Vertigo", 14, 14, "21:15", 105, "Karlos Molina"),
    act("Vertigo", 14, 14, "23:00", 120, "Jaime Soeiro"),
    act("Vertigo", 14, 15, "01:00", 120, "Fran Hernandez"),
    act("Vertigo", 14, 15, "03:00", 120, "Easttown"),

    // Friday 14 · Church Club
    act("Church Club", 14, 14, "22:00", 360, "DJs Comunidad Medusa"),

    // Friday 14 · Poliakow Club
    act("Poliakow Club", 14, 14, "19:00", 180, "Asesor B2B Anderson R"),
    act("Poliakow Club", 14, 14, "22:00", 60, "Dario Huerta"),
    act("Poliakow Club", 14, 14, "23:00", 60, "Nico Guerra"),
    act("Poliakow Club", 14, 15, "00:00", 120, "Ruben Vibes"),
    act("Poliakow Club", 14, 15, "02:00", 120, "Àlex Mapi"),

    // Sunday 16 · Apsaras
    act("Apsaras", 16, 16, "18:00", 60, "Lynne"),
    act("Apsaras", 16, 16, "19:00", 60, "Hektor Mass"),
    act("Apsaras", 16, 16, "20:00", 80, "Alvama Ice"),
    act("Apsaras", 16, 16, "21:30", 90, "Tiësto"),
    act("Apsaras", 16, 16, "23:10", 50, "T.B.A", "Opening ceremony · Mainstage"),
    act("Apsaras", 16, 17, "00:00", 90, "Oliver Heldens"),
    act("Apsaras", 16, 17, "01:30", 75, "Timmy Trumpet"),
    act("Apsaras", 16, 17, "02:45", 90, "Holy Priest"),
    act("Apsaras", 16, 17, "04:15", 90, "NERVO"),

    // Sunday 16 · Resonance
    act("Resonance", 16, 16, "18:00", 180, "Xune"),
    act("Resonance", 16, 16, "21:00", 120, "Lola Bozzano"),
    act("Resonance", 16, 16, "23:00", 90, "Hugel"),
    act("Resonance", 16, 17, "00:30", 90, "Franky Rizardo"),
    act("Resonance", 16, 17, "02:00", 90, "Marco Carola"),
    act("Resonance", 16, 17, "03:30", 90, "Adam Beyer"),
    act("Resonance", 16, 17, "05:00", 90, "Sara Landry"),

    // Sunday 16 · Arcade Land
    act("Arcade Land", 16, 16, "18:00", 120, "Javi Boss"),
    act("Arcade Land", 16, 16, "20:00", 60, "Furyan"),
    act("Arcade Land", 16, 16, "21:00", 60, "Ophidian"),
    act("Arcade Land", 16, 16, "22:00", 90, "Sakyra B2B Namara"),
    act("Arcade Land", 16, 16, "23:30", 60, "Tha Playah"),
    act("Arcade Land", 16, 17, "00:30", 60, "Angerfist"),
    act("Arcade Land", 16, 17, "01:30", 60, "Mad Dog"),
    act("Arcade Land", 16, 17, "02:30", 60, "Anime"),
    act("Arcade Land", 16, 17, "03:30", 30, "N-Vitral presents Bombsquad"),
    act("Arcade Land", 16, 17, "04:00", 60, "Lil Texas"),
    act("Arcade Land", 16, 17, "05:00", 60, "DRS"),

    // Sunday 16 · Beyond
    act("Beyond", 16, 16, "17:00", 60, "Gil"),
    act("Beyond", 16, 16, "18:00", 60, "Joel Toro"),
    act("Beyond", 16, 16, "19:00", 60, "Danny Mad"),
    act("Beyond", 16, 16, "20:00", 60, "Maggie"),
    act("Beyond", 16, 16, "21:00", 60, "R.Flow"),
    act("Beyond", 16, 16, "22:00", 60, "Arnny Montana"),
    act("Beyond", 16, 16, "23:00", 60, "DJ Bacardit"),
    act("Beyond", 16, 17, "00:00", 60, "J.Beren"),
    act("Beyond", 16, 17, "01:00", 60, "Totote"),
    act("Beyond", 16, 17, "02:00", 60, "Mon DJ"),
    act("Beyond", 16, 17, "03:00", 60, "Carlittos"),
    act("Beyond", 16, 17, "04:00", 60, "Space Elephants"),
    act("Beyond", 16, 17, "05:00", 60, "Michael Rod"),

    // Sunday 16 · Dharma
    act("Dharma", 16, 16, "19:00", 120, "Taia", "Makina takeover"),
    act("Dharma", 16, 16, "21:00", 120, "Carnada", "Makina takeover"),
    act("Dharma", 16, 16, "23:00", 300, "Pastis & Buenri & DJ Sisu", "Makina takeover"),
    act("Dharma", 16, 17, "04:00", 120, "Rage Amoretty", "Makina takeover"),

    // Sunday 16 · Beach Club
    act("Beach Club", 16, 16, "18:00", 90, "Sephax", "H4R exclusive stage"),
    act("Beach Club", 16, 16, "19:30", 90, "Lucia Gea", "H4R exclusive stage"),
    act("Beach Club", 16, 16, "21:00", 120, "Winson", "H4R exclusive stage"),
    act("Beach Club", 16, 16, "23:00", 120, "Brenda Serna", "H4R exclusive stage"),
    act("Beach Club", 16, 17, "01:00", 90, "Onlynumbers", "H4R exclusive stage"),
    act("Beach Club", 16, 17, "02:30", 120, "Dyen", "H4R exclusive stage"),
    act("Beach Club", 16, 17, "04:30", 90, "Nico Bondi B3B Krow B3B TBR", "H4R exclusive stage"),

    // Sunday 16 · Vertigo
    act("Vertigo", 16, 16, "17:00", 150, "Mario Vice"),
    act("Vertigo", 16, 16, "19:30", 90, "Javi Palmero"),
    act("Vertigo", 16, 16, "21:00", 180, "Wololo Soundsystem (Cortezz + Rizzu)"),
    act("Vertigo", 16, 17, "00:00", 120, "Rendher"),
    act("Vertigo", 16, 17, "02:00", 180, "Pive"),

    // Sunday 16 · Church Club
    act("Church Club", 16, 16, "22:00", 360, "DJs Comunidad Medusa"),

    // Sunday 16 · Poliakow Club
    act("Poliakow Club", 16, 16, "19:00", 60, "Ink 83"),
    act("Poliakow Club", 16, 16, "20:00", 60, "Jorge Quel"),
    act("Poliakow Club", 16, 16, "21:00", 60, "Toni Tega"),
    act("Poliakow Club", 16, 16, "22:00", 60, "Sweet Suarez"),
    act("Poliakow Club", 16, 16, "23:00", 60, "Alvaro Varen"),
    act("Poliakow Club", 16, 17, "00:00", 120, "Fercho Energy"),
    act("Poliakow Club", 16, 17, "02:00", 120, "Saldivar")
)

private val festivalTimeZone = TimeZone.getTimeZone("Europe/Madrid")

fun actStartMillis(act: Act): Long = Calendar.getInstance(festivalTimeZone).apply {
    clear()
    set(2026, Calendar.AUGUST, act.day, act.hour, act.minute, 0)
}.timeInMillis

fun actEndMillis(act: Act): Long = actStartMillis(act) + act.durationMinutes * 60_000L

fun actMinutes(act: Act): Long = actStartMillis(act)

fun isActLive(act: Act, nowMillis: Long = System.currentTimeMillis()): Boolean =
    nowMillis >= actStartMillis(act) && nowMillis < actEndMillis(act)

fun formatTime(act: Act): String {
    val end = Calendar.getInstance(festivalTimeZone).apply { timeInMillis = actEndMillis(act) }
    return String.format(
        Locale.US,
        "%02d:%02d–%02d:%02d",
        act.hour,
        act.minute,
        end.get(Calendar.HOUR_OF_DAY),
        end.get(Calendar.MINUTE)
    )
}

fun eventDayLabel(eventDay: Int): String = when (eventDay) {
    14 -> "SEXTA 14"
    16 -> "DOMINGO 16"
    else -> "DIA $eventDay"
}

fun actKey(act: Act): String = "${act.stage}|${act.day}|${act.hour}|${act.minute}|${act.name}"

object Favorites {
    private const val PREF = "favorites"
    private lateinit var prefs: SharedPreferences

    fun init(ctx: Context) {
        prefs = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    }

    fun isFav(act: Act) = prefs.getBoolean(actKey(act), false)

    fun toggle(act: Act) {
        prefs.edit().putBoolean(actKey(act), !isFav(act)).apply()
    }
}
