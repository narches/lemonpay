package com.example.lemon.navigation

import com.example.lemon.R






interface Destinations {
    val route: String
    val icon: Int
    val title: String
}

object Menu : Destinations {
    override val route = Routes.HOME
    override val icon = R.drawable.ic_home
    override val title = "Home"
}

object Home : Destinations {
    override val route = Routes.MENU
    override val icon = R.drawable.ic_menu
    override val title = "Menu"
}

object User : Destinations {
    override val route = Routes.USER
    override val icon = R.drawable.ic_location
    override val title = "User"
}
