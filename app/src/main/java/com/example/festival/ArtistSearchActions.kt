package com.example.festival

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.net.Uri
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

private data class ArtistSearchTarget(
    val label: String,
    val color: Int,
    val uriFor: (String) -> Uri
)

private val artistSearchTargets = listOf(
    ArtistSearchTarget("Spotify", Color.rgb(29, 185, 84)) { artist ->
        Uri.parse("https://open.spotify.com/search/${Uri.encode(artist)}")
    },
    ArtistSearchTarget("YouTube", Color.rgb(255, 0, 51)) { artist ->
        Uri.Builder()
            .scheme("https")
            .authority("www.youtube.com")
            .path("results")
            .appendQueryParameter("search_query", artist)
            .build()
    },
    ArtistSearchTarget("SoundCloud", Color.rgb(255, 119, 0)) { artist ->
        Uri.Builder()
            .scheme("https")
            .authority("soundcloud.com")
            .path("search")
            .appendQueryParameter("q", artist)
            .build()
    },
    ArtistSearchTarget("Google", Color.rgb(66, 133, 244)) { artist ->
        Uri.Builder()
            .scheme("https")
            .authority("www.google.com")
            .path("search")
            .appendQueryParameter("q", artist)
            .build()
    }
)

fun createArtistSearchRow(context: Context, artistName: String): LinearLayout {
    return LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.topMargin = context.dp(10)
        layoutParams = rowParams

        artistSearchTargets.forEachIndexed { index, target ->
            addView(context.createArtistSearchButton(target, artistName, index))
        }
    }
}

private fun Context.createArtistSearchButton(
    target: ArtistSearchTarget,
    artistName: String,
    index: Int
): TextView {
    return TextView(this).apply {
        text = target.label
        contentDescription = "Search $artistName on ${target.label}"
        setTextColor(target.color)
        textSize = 10f
        setTypeface(null, Typeface.BOLD)
        gravity = Gravity.CENTER
        includeFontPadding = false
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
        setPadding(dp(6), 0, dp(6), 0)
        background = searchButtonBackground(target.color)
        isClickable = true
        isFocusable = true
        setOnClickListener { openArtistSearch(this@createArtistSearchButton, target, artistName) }

        val params = LinearLayout.LayoutParams(0, dp(32), 1f)
        if (index > 0) params.leftMargin = dp(6)
        layoutParams = params
    }
}

private fun openArtistSearch(context: Context, target: ArtistSearchTarget, artistName: String) {
    val intent = Intent(Intent.ACTION_VIEW, target.uriFor(artistName))
    if (context !is Activity) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    try {
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No app found for ${target.label}", Toast.LENGTH_SHORT).show()
    }
}

private fun Context.searchButtonBackground(color: Int): RippleDrawable {
    val chip = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = dp(8).toFloat()
        setColor(Color.argb(24, Color.red(color), Color.green(color), Color.blue(color)))
        setStroke(dp(1), Color.argb(150, Color.red(color), Color.green(color), Color.blue(color)))
    }
    val mask = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = dp(8).toFloat()
        setColor(Color.WHITE)
    }
    return RippleDrawable(
        ColorStateList.valueOf(Color.argb(55, Color.red(color), Color.green(color), Color.blue(color))),
        chip,
        mask
    )
}

private fun Context.dp(v: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, v.toFloat(), resources.displayMetrics).toInt()
}
