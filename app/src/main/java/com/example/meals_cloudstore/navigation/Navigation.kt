package com.example.meals_cloudstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meals_cloudstore.database.DataBase
import com.example.meals_cloudstore.ui.RecipeScreen
import com.example.meals_cloudstore.ui.home.HomeScreen
import com.example.meals_cloudstore.ui.mealsrecipe.MealsRecipe


@Composable
fun Navigation(){
    val db = DataBase()
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MealsModel.HomeScreen.route){
        composable(route = MealsModel.HomeScreen.route){
            HomeScreen(db,navController)
        }

        composable(route = MealsModel.MealRecipes.route+"/{meal}",
            arguments = listOf(
                navArgument("meal"){
                    type = NavType.StringType
                    defaultValue=""
                    nullable = true
                })){entry->
            MealsRecipe(
                dataBase = db,
                navController = navController,
                meal =entry.arguments?.getString("meal")!!
            )
        }

        composable(route = MealsModel.RecipeScreen.route+"/{meal}/{recipeSelected}",
            arguments = listOf(
                navArgument("meal"){
                    type = NavType.StringType
                    defaultValue=""
                    nullable = true
                },
                navArgument("recipeSelected"){
                    type = NavType.StringType
                    defaultValue=""
                    nullable = true
                }
            )){entry->
            RecipeScreen(
                dataBase = db,
                meal =entry.arguments?.getString("meal")!!,
                recipeSelected =entry.arguments?.getString("recipeSelected")!!
            )
        }
    }
}