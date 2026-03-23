package com.example.lemon


interface Destinations {
    val route: String
    val icon: Int
    val title: String
}

object Menu : Destinations {
    override val route = "Home"
    override val icon = R.drawable.ic_home
    override val title = "Home"
}

object Home : Destinations {
    override val route = "Menu"
    override val icon = R.drawable.ic_menu
    override val title = "Menu"
}

object User : Destinations {
    override val route = "User"
    override val icon = R.drawable.ic_location
    override val title = "User"
}
