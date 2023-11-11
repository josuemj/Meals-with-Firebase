package com.example.meals_cloudstore.database

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataBase {
    val database = Firebase.database

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

}