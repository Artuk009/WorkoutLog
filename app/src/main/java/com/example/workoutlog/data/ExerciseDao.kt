package com.example.workoutlog.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Database access object for accessing exercises
@Dao
interface ExerciseDao {

    @Query("SELECT * from exercise WHERE muscleGroup = 'Back Day'")
    fun getBackExercises(): Flow<List<Exercise>>

    @Query("SELECT * from exercise WHERE muscleGroup = 'Chest Day'")
    fun getChestExercises(): Flow<List<Exercise>>

    @Query("SELECT * from exercise WHERE muscleGroup = 'Shoulder Day'")
    fun getShoulderExercises(): Flow<List<Exercise>>

    @Query("SELECT * from exercise WHERE muscleGroup = 'Leg Day'")
    fun getLegExercises(): Flow<List<Exercise>>

    // get one specific exercise by id
    @Query("SELECT * from exercise WHERE id = :id")
    fun getExercise(id: Int): Flow<Exercise>

    // add new exercise and ignore repeat entries
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exercise: Exercise)

    // update exercise
    @Update()
    suspend fun update(exercise: Exercise)

    // delete exercise
    @Delete
    suspend fun delete(exercise: Exercise)
}