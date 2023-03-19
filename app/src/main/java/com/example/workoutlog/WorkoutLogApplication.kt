package com.example.workoutlog

import android.app.Application
import com.example.workoutlog.data.ExerciseRoomDatabase

class WorkoutLogApplication : Application() {
    val database: ExerciseRoomDatabase by lazy { ExerciseRoomDatabase.getDatabase(this) }
}