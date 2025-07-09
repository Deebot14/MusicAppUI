package com.graissy.musicappui.ui.theme

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import com.graissy.musicappui.R

// (9) *** We create a Dummy Data class for our Library View  ***
// THEN we make a Library View
data class Lib(@DrawableRes val icon: Int, val name: String)

val libraries = listOf<Lib>(
    Lib(R.drawable.baseline_queue_music_24, "Playlist"),
    Lib(R.drawable.baseline_mic_external_on_24, "Artists"),
    Lib(R.drawable.baseline_album_24, "Albums"),
    Lib(R.drawable.baseline_music_note_24, "Songs"),
    Lib(R.drawable.baseline_genre, "Genre")
)
