package com.example.workoutlog.model

import androidx.lifecycle.*
import com.example.workoutlog.data.Exercise
import com.example.workoutlog.data.ExerciseDao
import kotlinx.coroutines.launch

// Keeps reference an up-to-date list of all exercises.
class WorkoutLogViewModel(private val exerciseDao: ExerciseDao) : ViewModel() {

    private val _muscleGroup = MutableLiveData<String>()
    val muscleGroup: LiveData<String> = _muscleGroup

    val backExercises: LiveData<List<Exercise>> = exerciseDao
        .getBackExercises().asLiveData()
    val chestExercises: LiveData<List<Exercise>> = exerciseDao
        .getChestExercises().asLiveData()
    val shoulderExercises: LiveData<List<Exercise>> = exerciseDao
        .getShoulderExercises().asLiveData()
    val legExercises: LiveData<List<Exercise>> = exerciseDao
        .getLegExercises().asLiveData()

    fun setMuscleGroup(muscleGroup: String) {
        _muscleGroup.value = muscleGroup
    }

    //update an existing exercise in the database.
    fun updateExercise(
        id: Int,
        muscleGroup: String,
        name: String,
        weight: String,
        reps: String
    ) {
        val updatedExercise = getUpdatedExerciseEntry(id, muscleGroup, name, weight, reps)
        updateExercise(updatedExercise)
    }

    // Launching a new coroutine to update an exercise in a non-blocking way.
    private fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseDao.update(exercise)
        }
    }

    // Inserts the new exercise into the database.
    fun addNewExercise(name: String, muscleGroup: String, weight: String, reps: String) {
        val newExercise = getNewExerciseEntry(name, muscleGroup, weight, reps)
        insertExercise(newExercise)
    }

    // Launching a new coroutine to insert an exercise in a non-blocking way.
    private fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseDao.insert(exercise)
        }
    }

    // Launching a new coroutine to delete an exercise in a non-blocking way.
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            exerciseDao.delete(exercise)
        }
    }

    // Retrieve an exercise from the repository.
    fun retrieveExercise(id: Int): LiveData<Exercise> {
        return exerciseDao.getExercise(id).asLiveData()
    }

    // Returns true if the EditTexts are not empty.
    fun isEntryValid(name: String, weight: String, reps: String): Boolean {
        if (name.isBlank() || weight.isBlank() || reps.isBlank()) {
            return false
        }
        return true
    }

    //Returns the instance of the Exercise entity class with user entered info.
    private fun getNewExerciseEntry(
        name: String,
        muscleGroup: String,
        weight: String,
        reps: String
    ): Exercise {
        return Exercise(
            name = name,
            muscleGroup = muscleGroup,
            weight = weight.toInt(),
            reps = reps.toInt()
        )
    }

    // Returns the instance of the Exercise entity class with user updated info.
    private fun getUpdatedExerciseEntry(
        id: Int,
        muscleGroup: String,
        name: String,
        weight: String,
        reps: String
    ): Exercise {
        return Exercise(
            id = id,
            muscleGroup = muscleGroup,
            name = name,
            weight = weight.toInt(),
            reps = reps.toInt()
        )
    }
}

// Factory ot instantiate the viewModel instance.
class WorkoutLogViewModelFactory(private val exerciseDao: ExerciseDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutLogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutLogViewModel(exerciseDao) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}