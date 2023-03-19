package com.example.workoutlog.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Represents a single exercise in the database.
@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "muscleGroup")
    val muscleGroup: String = "",
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "weight")
    val weight: Int,
    @ColumnInfo(name = "reps")
    val reps: Int
)