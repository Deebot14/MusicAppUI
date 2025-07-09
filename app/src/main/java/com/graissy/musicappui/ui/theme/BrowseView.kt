package com.graissy.musicappui.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.graissy.musicappui.R

// (10) *** We create a Browse file class to put in 'Navigation' ***
@Composable
fun Browse() {
    // List of categories
    val categories = listOf("Hits", "Motivation", "Workout", "Jogging", "Yoga", "Meditation")

    // LazyVerticalGrid with two columns
    LazyVerticalGrid(GridCells.Fixed(2)) {
        items(categories) { cat ->
            BrowserItem(cat = cat, drawable = R.drawable.baseline_apps_24)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBrowseScreen() {
    MaterialTheme {
        // You can wrap BrowseScreen in a padding or other layout composable if needed for styling
        Box(modifier = Modifier.padding(16.dp)) {
            Browse()
        }
    }
}