package com.graissy.musicappui.ui.theme

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.primarySurface
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.graissy.musicappui.MainViewModel
import com.graissy.musicappui.Screen
import com.graissy.musicappui.screensInBottom
import com.graissy.musicappui.screensInDrawer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// (1) *** We create a Main View ***
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainView(){

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val scope: CoroutineScope = rememberCoroutineScope()

    // Allow us to find out which View/Screen we currently at
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Here --------------------------------------------------------------------------(3.1)
    val viewModel: MainViewModel = viewModel()
    val currentScreen = remember{
        viewModel.currentScreen.value
    }

    val title = remember{
        mutableStateOf(currentScreen.title)
    }
    // Here --------------------------------------------------------------------------(3.1)

    // Here -------------------------------------------------------------------------(11.1)
    // THEN we create Modal Bottom Sheet Layout ↓↓
    val isSheetFullScreen by remember { mutableStateOf(false) }
    val modifier = if(isSheetFullScreen) Modifier.fillMaxSize() else Modifier.fillMaxWidth()

    val modalSheetState = androidx.compose.material.rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded})

    val roundCornerRadius = if(isSheetFullScreen) 0.dp else 12.dp
    // ------------------------------------------------------------------------------(11.1)

    // Here --------------------------------------------------------------------------(7.1)
    // THEN we put it in Scaffold
    val bottomBar: @Composable () -> Unit = {
        if(currentScreen is Screen.DrawerScreen || currentScreen == Screen.BottomScreen.Home){
            BottomNavigation(Modifier.wrapContentSize()) {
                screensInBottom.forEach {
                    item ->
                    // Here ------------------------------------------------------------(10.2)
                    val isSelected = currentRoute == item.bRoute
                    Log.d("Navigation", "Item: ${item.bTitle}, Current Route: $currentRoute, Is Selected")
                    val tint = if(isSelected) Color.White else Color.Black
                    // -----------------------------------------------------------------(10.2)
                    BottomNavigationItem(selected = currentRoute == item.bRoute,
                        onClick = { controller.navigate(item.bRoute)
                                    title.value = item.bTitle // change titles when we navigate
                                  },
                        icon = {
                            Icon( tint = tint, // Here ←←-----------------------------(10.2)
                                contentDescription = item.bTitle, painter = painterResource(id = item.icon))
                               },
                        label = { Text(text = item.bTitle, color = tint) }, // Here ←←-(10.2)
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Black
                    )
                }
            }
        }
    }
    // -------------------------------------------------------------------------------(7.1)

    // We create variable here--------------------------------------------------------(4.1)
    val dialogOpen = remember{
        mutableStateOf(false)
    }
    // -------------------------------------------------------------------------------(4.1)

    // Here we create a Modal Bottom Sheet Layout and place 'Scaffold' in it --------(11.2)
    // THEN we create the '3 dots' menu in the top bar in 'Scaffold' ↓↓
    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = RoundedCornerShape(topStart = roundCornerRadius, topEnd = roundCornerRadius),
        sheetContent = {
            MoreBottomSheet(modifier = modifier)
    }) {

        Scaffold(
            // Here --------------------------------------------------------------------------(7.2)
            // THEN we need to navigate them in the Navigation Composable
            bottomBar = bottomBar,
            // -------------------------------------------------------------------------------(7.2)
            topBar = {
                TopAppBar(title = { Text(title.value) },
                    // Here -------------------------------------------------------------------(12)
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if(modalSheetState.isVisible)
                                        modalSheetState.hide()
                                    else
                                        modalSheetState.show()
                                }
                            }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                        }
                    },
                    // ------------------------------------------------------------------------(12)
                    navigationIcon = { IconButton(onClick = {
                        // Open the Drawer(Side)
                        scope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                    }}
                )
            },
            // Here -------------------------------------------------------------------------------(2.2)
            scaffoldState = scaffoldState,
            drawerContent = {
                LazyColumn(Modifier.padding(16.dp)){
                    items(screensInDrawer){
                            item ->
                        DrawerItem(selected = currentRoute == item.dRoute, item = item){
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                            if (item.dRoute == "add_account"){
                                // Open dialog
                                // Here -----------------------------------------------------------(4.3)
                                dialogOpen.value = true
                            }else {
                                controller.navigate(item.dRoute)
                                title.value = item.dTitle
                            }
                        }
                    }
                }

            }
            // ------------------------------------------------------------------------------------(2.2)


        ) {
            // Here --------------------------------------------------------------------------(3.3)
            Navigation(navController = controller, viewModel = viewModel, pd = it)
            // Here --------------------------------------------------------------------------(4.2)
            // THEN we need to add functionality if a person chooses 'add_account' ↑↑
            AccountDialog(dialogOpen = dialogOpen)
        }

    }



}

// Here -----------------------------------------------------------------------------------(2.1)
// THEN we put it in 'Scaffold' ↑↑
@Composable
fun DrawerItem(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
){

//    if (item == null) {
//        Log.d("ItemTag", "Item is null")
//        return
//    }

    val background = if(selected) Color.DarkGray else Color.White
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(background)
            .clickable {
                onDrawerItemClicked()
            }) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)
        )
        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.h5
        )
    }
}
// ----------------------------------------------------------------------------------------(2.1)

// (11) *** We create A Bottom Sheet ***
// THEN we create variables ↑↑
@Composable
fun MoreBottomSheet(modifier: Modifier){
    Box (
        Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(
                MaterialTheme.colors.primarySurface
            )
    ){
        Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {

            Row (modifier = modifier.padding(16.dp)){
                Icon(modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = com.graissy.musicappui.R.drawable.baseline_settings_24),
                    contentDescription = "Settings")
                Text(text = "Settings", fontSize = 20.sp, color = Color.White)
            }

            Row (modifier = modifier.padding(16.dp)){
                Icon(modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = com.graissy.musicappui.R.drawable.baseline_share_24),
                    contentDescription = "Share")
                Text(text = "Share", fontSize = 20.sp, color = Color.White)
            }

            Row (modifier = modifier.padding(16.dp)){
                Icon(modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = com.graissy.musicappui.R.drawable.baseline_help_outline_24),
                    contentDescription = "Help")
                Text(text = "Help", fontSize = 20.sp, color = Color.White)
            }

        }
    }
}
// --------------------------------------------------------------------------------(11)

// Here --------------------------------------------------------------------------(3.2)
// THEN we use it in Scaffold ↑↑
@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues){
    NavHost(navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.AddAccount.route, modifier = Modifier.padding(pd)){

        composable(Screen.DrawerScreen.Account.route){
            // Here ---------------------------------------------------------------------------(5.1)
            AccountView()

        }
        composable(Screen.DrawerScreen.Subscription.route){
            // Here ---------------------------------------------------------------------------(6.1)
            Subscription()

        }

        composable(Screen.DrawerScreen.AddAccount.route) {
            // Content for the 'add_account' screen
            // Replace with the appropriate composable function or content
        }

        // Here --------------------------------------------------------------------------(7.3)
        composable(Screen.BottomScreen.Home.bRoute){
            // TODO Add Home Screen
            // Here ----------------------------------------------------------------------(8.1)
            Home()
        }
        composable(Screen.BottomScreen.Library.bRoute){
            // TODO Add Library Screen
            // Here ----------------------------------------------------------------------(9.2)
            Library()

        }
        composable(Screen.BottomScreen.Browse.bRoute){
            // TODO Add Browse Screen
            // Here ----------------------------------------------------------------------(10.1)
            Browse()
            // THEN we need to fix the problem with the Navigation:
            // we we navigate between different composables they don't appear white when selected
            // we fix this in 'Scaffold.BottomBar(7.1)'
        }
        // -------------------------------------------------------------------------------(7.3)





    }
}

// Here --------------------------------------------------------------------------(3.2)

















