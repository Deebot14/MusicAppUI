package com.graissy.musicappui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// (3) *** We create a View Model ***
// THEN we create a variable in 'MainView'
// THEN we create 'Navigation' in 'MainView'
class MainViewModel : ViewModel() {
    private val _currentScreen: MutableState<Screen> = mutableStateOf(Screen.DrawerScreen.AddAccount)

    val currentScreen: MutableState<Screen> get() = _currentScreen

    fun setCurrentScreen(screen: Screen){
        _currentScreen.value = screen
    }
}