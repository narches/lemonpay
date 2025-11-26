
package com.example.lemon



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import android.os.Build
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.composable
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.example.lemon.profile.ProfileScreen
import com.example.lemon.onboarding.OnboardingScreen
import com.example.lemon.datastore.DataStoreManager
import kotlinx.coroutines.delay
import com.example.lemon.onboarding.OnboardingScreen
import androidx.compose.material.CircularProgressIndicator





class MainActivity : ComponentActivity() {
    private lateinit var dataStoreManager: DataStoreManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.TRANSPARENT
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
        }
        setContent {
            AppEntry()
        }
    }
}



@Composable
fun AppEntry() {
    val context = LocalContext.current
    val navController = rememberNavController()
    val dataStore = remember { DataStoreManager(context) }
    val user by dataStore.userFlow.collectAsState(initial = null)
    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    val isRegistered = prefs.getBoolean("isRegistered", false)
    // Loading state
    if (user == null) {
        Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // UI states
//    var refresh by remember { mutableStateOf(false) }

//    when {
//        isRegistered -> {
//            MyApp()
//        }
//
//        else -> {
//            OnboardingScreen(
//                navController = navController,
//                prefs = prefs,
//                onRegistered = { App() }
//            )
//        }
//    }
    var showApp by remember { mutableStateOf(isRegistered) }

    if (showApp) {
        MyApp()
    } else {
        val navController = rememberNavController()

        OnboardingScreen(
            navController = navController,
            prefs = prefs,
            onRegistered = {
                showApp = true   // ← SIMPLE & FINAL
            }
        )
    }
}







@Composable
fun MyApp() {

    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }

    val navController = rememberNavController()

    var showSplash by remember { mutableStateOf(true) }

    // Drawer state
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    val isDrawerOpen = scaffoldState.drawerState.isOpen

    // Splash delay
    LaunchedEffect(Unit) {
        delay(1000)
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerContent = {
                DrawerContent(
                    onClose = {
                        scope.launch { scaffoldState.drawerState.close() }
                    },
                    onItemClick = { item ->
                        scope.launch { scaffoldState.drawerState.close() }
                        when (item) {
                            "Home" -> navController.navigate(Home.route)
                            "Menu" -> navController.navigate(Menu.route)
                            "Company" -> navController.navigate(Location.route)
                        }
                    },
                    onLoginClick = {
                        scope.launch { scaffoldState.drawerState.close() }
                        navController.navigate("login")
                    }
                )
            },

            bottomBar = {
                if (!isDrawerOpen) {
                    MyBottomNavigation(navController = navController)
                }
            }
        ) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {

                MyBar(scaffoldState = scaffoldState, scope = scope, dataStoreManager = dataStoreManager, onProfileClick = {navController.navigate("profile")} )

                NavHost(
                    navController = navController,
                    startDestination = Home.route,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    // Old routes (unchanged)
                    composable(Menu.route) {
                        MenuScreen(navController)
                    }
                    composable(Home.route) {
                        HomeScreen(navController)
                    }
                    composable(Location.route) {
                        LocationScreen()
                    }

                    // Your existing dishDetails navigation
                    composable(
                        route = "dishDetails/{dishName}",
                        arguments = listOf(navArgument("dishName") {
                            type = NavType.StringType
                        })
                    ) { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("dishName")
                        val dish = Dishes.find { it.name == name }

                        if (dish != null) DishDetails(dish)
                        else Text("Dish not found")
                    }

                    // NEW ROUTES
                    composable("login") {
                        LoginScreen(
                            navController = navController,
                            dataStoreManager = dataStoreManager
                        )
                    }

                    composable("register") {
                        RegistrationScreen(
                            navController = navController,
                            dataStoreManager = dataStoreManager
                        )
                    }

                    composable("profile") {
                        ProfileScreen(
                            navController = navController,
                            dataStore = dataStoreManager
                        )
                    }
                }
            }
        }
    }
}






@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4CE14)), // bright yellow
        contentAlignment = Alignment.Center
    ) {
        // Image on top
        Image(
            painter = painterResource(id = R.drawable.gllemon), // replace with your image
            contentDescription = "Lemon Logo",
            modifier = Modifier
                .size(120.dp) // adjust size as needed
        )
    }
}



@Composable
fun MyBottomNavigation(navController: NavController) {
    val destinationList = listOf(
        Menu,
        Home,
        Location
    )
    val selectedIndex = rememberSaveable {
        mutableStateOf(1)
    }
    BottomNavigation (
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = Color(0xFF495E57),
        contentColor = Color.Yellow
    ) {

        destinationList.forEachIndexed { index, destination ->
            BottomNavigationItem(
                label = { Text(text = destination.title) },
                icon = {
                    Icon(
                        painter = painterResource(id = destination.icon),
                        contentDescription = destination.title
                    )
                },
                selected = index == selectedIndex.value,
                onClick = {
                    selectedIndex.value = index
                    navController.navigate(destinationList[index].route) {
                        popUpTo(Home.route)
                        launchSingleTop = true
                    }
                })
        }
    }
}