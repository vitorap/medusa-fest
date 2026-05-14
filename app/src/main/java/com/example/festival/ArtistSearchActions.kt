package com.example.festival

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.net.Uri
import android.util.TypedValue
import android.view.Gravity
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast

private data class ArtistSearchTarget(
    val label: String,
    val color: Int,
    val iconRes: Int,
    val uriFor: (String) -> Uri
)

private val artistSearchTargets = listOf(
    ArtistSearchTarget("Spotify", Color.rgb(29, 185, 84), R.drawable.ic_spotify) { artist ->
        Uri.parse("https://open.spotify.com/search/${Uri.encode(artist)}")
    },
    ArtistSearchTarget("YouTube", Color.rgb(255, 0, 51), R.drawable.ic_youtube) { artist ->
        Uri.Builder()
            .scheme("https")
            .authority("www.youtube.com")
            .path("results")
            .appendQueryParameter("search_query", artist)
            .build()
    },
    ArtistSearchTarget("SoundCloud", Color.rgb(255, 119, 0), R.drawable.ic_soundcloud) { artist ->
        Uri.Builder()
            .scheme("https")
            .authority("soundcloud.com")
            .path("search")
            .appendQueryParameter("q", artist)
            .build()
    },
    ArtistSearchTarget("Google", Color.rgb(66, 133, 244), R.drawable.ic_google) { artist ->
        Uri.Builder()
            .scheme("https")
            .authority("www.google.com")
            .path("search")
            .appendQueryParameter("q", artist)
            .build()
    }
)

fun createArtistSearchRow(context: Context, artistName: String, topMarginDp: Int = 8): LinearLayout {
    return LinearLayout(context).apply {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        val rowParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        rowParams.topMargin = context.dp(topMarginDp)
        layoutParams = rowParams

        artistSearchTargets.forEachIndexed { index, target ->
            addView(context.createArtistSearchButton(target, artistName, index))
        }

        alpha = 0f
        translationY = context.dp(4).toFloat()
        post {
            animate()
                .alpha(1f)
                .translationY(0f)
                .setDuration(180)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }
}

private fun Context.createArtistSearchButton(
    target: ArtistSearchTarget,
    artistName: String,
    index: Int
): ImageButton {
    return ImageButton(this).apply {
        contentDescription = "Search $artistName on ${target.label}"
        setImageResource(target.iconRes)
        imageTintList = ColorStateList.valueOf(target.color)
        scaleType = ImageView.ScaleType.CENTER
        setPadding(dp(6), dp(6), dp(6), dp(6))
        background = searchButtonBackground(target.color)
        alpha = 0.82f
        isClickable = true
        isFocusable = true
        setOnClickListener { animateLaunch(this, this@createArtistSearchButton, target, artistName) }

        val params = LinearLayout.LayoutParams(dp(28), dp(28))
        if (index > 0) params.leftMargin = dp(5)
        layoutParams = params
    }
}

private fun animateLaunch(button: ImageButton, context: Context, target: ArtistSearchTarget, artistName: String) {
    button.animate().cancel()
    button.animate()
        .scaleX(0.88f)
        .scaleY(0.88f)
        .alpha(1f)
        .setDuration(70)
        .withEndAction {
            button.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(0.82f)
                .setDuration(130)
                .setInterpolator(OvershootInterpolator(2f))
                .start()
            openArtistSearch(context, target, artistName)
        }
        .start()
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
        cornerRadius = dp(10).toFloat()
        setColor(Color.argb(18, Color.red(color), Color.green(color), Color.blue(color)))
        setStroke(dp(1), Color.argb(92, Color.red(color), Color.green(color), Color.blue(color)))
    }
    val mask = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = dp(10).toFloat()
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
