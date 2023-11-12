package com.example.meals_cloudstore.ui.home

import android.annotation.SuppressLint
import android.provider.Settings.Global
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.meals_cloudstore.database.DataBase
import com.example.meals_cloudstore.navigation.MealsModel
import com.example.meals_cloudstore.ui.topbar.MealsAppTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    dataBase: DataBase,
    navController: NavController
){
    Scaffold(
        topBar = { MealsAppTopBar(screenTittle = "Meals") }
    ) {innerPadding->
        val mealsList = remember { mutableStateListOf<String>() }
        val isLoading = remember { mutableStateOf(true) }

        LaunchedEffect(key1 = true) {
            try {
                mealsList.addAll(dataBase.getMeals())
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
                    MealCard(navController = navController, meal = meal)
                }
            }
        }
    }


}