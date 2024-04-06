package com.example.ejercicio1cm.navigation

sealed class Screen (val route:String){
    object PayScreen : Screen(route = "pay_screen")
    object SuccessScreen : Screen(route = "success_screen")

    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach {arg->
                append("/$arg")
            }
        }
    }
}