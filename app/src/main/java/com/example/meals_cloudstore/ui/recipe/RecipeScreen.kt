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
    val recipeText = remember { mutableStateOf<String?>(null) }

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

                // The key1 parameter should be your meal and recipeSelected so it re-triggers when they change
                LaunchedEffect(key1 = meal, key2 = recipeSelected) {
                    isLoading.value = true
                    recipeText.value = dataBase.getRecipe(meal, recipeSelected) // Fetch the recipe text
                    isLoading.value = false
                }

                if (isLoading.value) {
                    CircularProgressIndicator()
                } else {
                    recipeText.value?.let { recipe ->
                        Text(text = recipe) // Display the recipe text
                    } ?: Text(text = "Recipe not found.")
                }
            }
        }
    }
}