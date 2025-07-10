# MusicAppUI
This is a personal project — an Android app designed as a music streaming interface using Jetpack Compose. The app includes a bottom navigation bar and a side drawer menu, allowing users to switch between different UI screens like Home, Library, Browse, and Account-related views.

## Programming Languages / Technologies
Kotlin, Jetpack Compose, Android SDK, ViewModel, MutableState, Navigation (manual), Material 3

## Features

- ### Navigation Structure:
The app uses a combination of Bottom Navigation (Home, Library, Browse) and Drawer Navigation (Account, Subscription, Add Account). These are handled using a sealed Screen class with composable routes.

- ### State Management:
A MainViewModel tracks the currently selected screen using MutableState<Screen>, which updates the UI reactively upon user interaction.

- ### Composable Architecture:
Each screen is represented as a @Composable function, encouraging reusable and modular UI blocks based on the Jetpack Compose framework.

- ### Theming:
The UI theme is implemented using MaterialTheme.colorScheme, offering consistency with Material Design 3 principles.
