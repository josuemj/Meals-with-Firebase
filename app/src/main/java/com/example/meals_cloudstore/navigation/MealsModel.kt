package com.example.meals_cloudstore.navigation

sealed class MealsModel(
    val route:String
){
    object HomeScreen: MealsModel("home_screen")
    object MealRecipes: MealsModel("meals")
    object Recipe:MealsModel("meal_recipe")

    fun withArgs(vararg args: String):String{
        return buildString {
            append(route)
            args.forEach {
                    arg->append("/$arg")
            }
        }
    }
}

