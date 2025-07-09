package com.graissy.musicappui

import androidx.annotation.DrawableRes

// (2) *** We create a Screen class to manage the screens/routes ***
// THEN we add a Composable 'DrawerItem' in 'MainView'
sealed class Screen(val title: String, val route: String) {

    // Drawer and Drawer objects
    sealed class DrawerScreen(val dTitle: String, val dRoute: String, @DrawableRes val icon: Int)
        : Screen(dTitle, dRoute){
            object Account: DrawerScreen(
                "Account",
                "account",
                R.drawable.ic_account
            )
            object Subscription: DrawerScreen(
                "Subscription",
                "subscribe",
                R.drawable.ic_subscribe
            )
            object AddAccount: DrawerScreen(
                "Add Account",
                "add_account",
                R.drawable.ic_baseline_person_add_alt_1_24
            )
        }

    // (7) *** We create a Bottom Screen class and a list of the objects 'screensInBottom' ↓↓ ***
    // THEN we add variable in MainBView.kt

    sealed class BottomScreen(val bTitle: String, val bRoute: String, @DrawableRes val icon: Int)
        : Screen(bTitle, bRoute){
            object Home: BottomScreen("Home", "home", R.drawable.ic_music_player_green)

            object Library: BottomScreen("Library", "library", R.drawable.baseline_library_music_24)

            object Browse: BottomScreen("Browse", "browse", R.drawable.baseline_apps_24)
        }

    // --------------------------------------------------------------------------------(7)

}

val screensInBottom = listOf(
    Screen.BottomScreen.Home,
    Screen.BottomScreen.Library,
    Screen.BottomScreen.Browse
)

val screensInDrawer = listOf(
    Screen.DrawerScreen.Account,
    Screen.DrawerScreen.Subscription,
    Screen.DrawerScreen.AddAccount
)
