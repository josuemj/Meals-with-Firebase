package com.example.meals_cloudstore.database

import com.google.firebase.Firebase
import com.google.firebase.database.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DataBase {
    private val database = Firebase.database

    suspend fun getMeals(): List<String> = withContext(Dispatchers.IO) {
        val mealsRef = database.getReference("meals")
        val meals = mutableListOf<String>()
        val tasks = CompletableDeferred<List<String>>()

        mealsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { childSnapshot ->
                    childSnapshot.key?.let { key ->
                        meals.add(key)
                    }
                }
                tasks.complete(meals)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                tasks.completeExceptionally(databaseError.toException())
            }
        })

        return@withContext tasks.await()
    }

    suspend fun getMealCategories(mealSelected:String): List<String> = withContext(Dispatchers.IO) {
        val mealsRef = database.getReference("meals").child(mealSelected)

        val meals = mutableListOf<String>()
        val tasks = CompletableDeferred<List<String>>()

        mealsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { childSnapshot ->
                    childSnapshot.key?.let { key ->
                        meals.add(key)
                    }
                }
                tasks.complete(meals)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                tasks.completeExceptionally(databaseError.toException())
            }
        })

        return@withContext tasks.await()
    }

    suspend fun getMealRecipe(mealSelected:String,recipeSelected:String): List<String> = withContext(Dispatchers.IO) {
        val mealsRef = database.getReference("meals").child(mealSelected).child(recipeSelected)
        println("AT SUSPEND FUNC FROM DATABASE")
        println("meal selected: $mealSelected")
        println("recipe selected: $recipeSelected")

        val meals = mutableListOf<String>()
        val tasks = CompletableDeferred<List<String>>()

        mealsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach { childSnapshot ->
                    childSnapshot.key?.let { key ->
                        meals.add(key)
                        println(key)
                    }
                }
                tasks.complete(meals)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                tasks.completeExceptionally(databaseError.toException())
            }
        })

        return@withContext tasks.await()
    }

    suspend fun getRecipe(mealSelected: String, recipeSelected: String): String? = withContext(Dispatchers.IO) {
        val recipeRef = database.getReference("meals").child(mealSelected).child(recipeSelected)
        val task = CompletableDeferred<String?>()

        recipeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val recipeText = dataSnapshot.getValue<String>()
                task.complete(recipeText)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                task.completeExceptionally(databaseError.toException())
            }
        })

        return@withContext task.await() // This will suspend the coroutine until the value is returned
    }

}