package com.example.meals_cloudstore.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.meals_cloudstore.database.DataBase
import com.example.meals_cloudstore.navigation.MealsModel
import com.example.meals_cloudstore.ui.topbar.MealsAppTopBar

@Composable
fun RecipeScreen(
    dataBase:DataBase,
    meal:String,
    recipeSelected:String
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = { MealsAppTopBar(screenTittle = "Recipe for "+recipeSelected) }
        ) {innerPadding->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                val mealsList = remember { mutableStateListOf<String>() }
                val isLoading = remember { mutableStateOf(true) }

                LaunchedEffect(key1 = true) {
                    try {
                        print("At Recipe Screen (third screen")
                        println("meal selected: $meal")
                        println("recipe selected: $recipeSelected")
                        mealsList.addAll(dataBase.getMealRecipe(meal,recipeSelected))
                    } catch (e: Exception) {
                        println("Error retrieving meals: ${e.message}")
                    } finally {
                        isLoading.value = false
                    }
                }

                if (isLoading.value) {
                    CircularProgressIndicator()
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(mealsList) { meal ->
                            Text(
                                modifier = Modifier.clickable {

                                },
                                text = meal
                            )
                        }
                    }
                }
            }
        }
    }
}