package com.example.ejercicio1cm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ejercicio1cm.ui.screens.Payment
import com.example.ejercicio1cm.ui.screens.Success

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.PayScreen.route){
        composable(route=Screen.PayScreen.route){
            PayScreen(navController = navController)
        }
        composable(
            route=Screen.SuccessScreen.route + "/{total}/{name}",
            arguments = listOf(
                navArgument("total"){
                    type= NavType.StringType
                } ,
                navArgument("name"){
                    type= NavType.StringType
                }
            )
        ){
            entry->
            SuccessScreen(
                total = entry.arguments?.getString("total"),
                name = entry.arguments?.getString("name")
            )
        }
    }
}

@Composable
fun PayScreen(navController: NavController){
    Payment(navController)
}

@Composable
fun SuccessScreen(total: String?, name: String?){
    Success(total, name)
}

