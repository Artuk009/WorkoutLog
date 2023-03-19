package com.example.workoutlog.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.workoutlog.WorkoutLogApplication
import com.example.workoutlog.data.Exercise
import com.example.workoutlog.databinding.FragmentExerciseDetailBinding
import com.example.workoutlog.model.WorkoutLogViewModel
import com.example.workoutlog.model.WorkoutLogViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

// displays details of selected exercise
class ExerciseDetailFragment : Fragment() {
    private val navigationArgs: ExerciseDetailFragmentArgs by navArgs()
    lateinit var exercise: Exercise

    private val viewModel: WorkoutLogViewModel by activityViewModels {
        WorkoutLogViewModelFactory(
            (activity?.application as WorkoutLogApplication).database.exerciseDao()
        )
    }

    private var _binding: FragmentExerciseDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    // binds views with the past exercise data
    private fun bind(exercise: Exercise) {
        binding.apply {
            exerciseName.text = exercise.name
            weight.text = exercise.weight.toString()
            repsCount.text = exercise.reps.toString()
            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem()}
        }
    }

    // Navigate to the edit exercise screen.
    private fun editItem() {
        val action = ExerciseDetailFragmentDirections
            .actionExerciseDetailFragmentToAddExerciseFragment(
                "Edit Exercise",
                exercise.muscleGroup,
                exercise.id)
        this.findNavController().navigate(action)
    }

    // Displays an alert dialog to get user's confirmation before deleting an exercise.
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Attention:")
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ -> deleteExercise()}
            .show()
    }

    // Deletes the current item and navigates up.
    private fun deleteExercise() {
        viewModel.deleteExercise(exercise)
        findNavController().navigateUp()
        Snackbar.make(binding.root, "Exercise Deleted", Snackbar.LENGTH_SHORT).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.id
        viewModel.retrieveExercise(id)
            .observe(this.viewLifecycleOwner) { selectedExercise ->
                exercise = selectedExercise
                bind(exercise)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}