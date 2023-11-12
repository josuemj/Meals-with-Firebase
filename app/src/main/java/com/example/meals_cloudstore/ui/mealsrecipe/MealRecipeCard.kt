package com.example.meals_cloudstore.ui.mealsrecipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.meals_cloudstore.navigation.MealsModel

@Composable
fun MealRecipeCard(
    navController: NavController,
    meal:String,
    mealRecipe:String
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(50.dp)
            .clickable {
                navController.navigate(MealsModel.RecipeScreen.withArgs(
                    meal,mealRecipe
                ))
            },
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, Color.Black)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF20C8DD), Color.White),
                    ),
                    shape = RoundedCornerShape(15.dp) // Set rounded corners
                ),

            contentAlignment = Alignment.CenterStart,



            ){
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = mealRecipe,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}