package br.com.fiap.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import br.com.fiap.navigation.screens.LoginScreen
import br.com.fiap.navigation.screens.MenuScreen
import br.com.fiap.navigation.screens.OrdersScreen
import br.com.fiap.navigation.screens.ProfileScreen
import br.com.fiap.navigation.ui.theme.NavigationTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberAnimatedNavController()
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable(route = "login") {
                            LoginScreen(navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(navController)
                        }
                        composable(
                            route = "profile/{name}/{age}",
                            arguments = listOf(
                                navArgument(name = "name") {
                                    type = NavType.StringType
                                },
                                navArgument(name = "age") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val name = it.arguments?.getString("name")
                            val age = it.arguments?.getInt("age")
                            ProfileScreen(navController, name!!, age!!)
                        }
                        composable(
                            route = "orders?orderNumber={orderNumber}",
                            arguments = listOf(navArgument(name = "orderNumber") {
                                defaultValue = "no value"
                            })
                        ) {
                            OrdersScreen(
                                navController,
                                it.arguments?.getString("orderNumber").toString()
                            )
                        }
                    }
                }
            }
        }
    }
}