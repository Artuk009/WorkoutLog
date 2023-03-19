package com.example.workoutlog.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Database class with singleton
@Database(entities = [Exercise::class], version = 1, exportSchema = false)
abstract class ExerciseRoomDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    companion object{
        @Volatile
        private var INSTANCE: ExerciseRoomDatabase? = null

        fun getDatabase(context: Context): ExerciseRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExerciseRoomDatabase::class.java,
                    "exercise_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}