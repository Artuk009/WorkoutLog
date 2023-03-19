package com.example.workoutlog.fragments

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.workoutlog.WorkoutLogApplication
import com.example.workoutlog.data.Exercise
import com.example.workoutlog.databinding.FragmentAddExerciseBinding
import com.example.workoutlog.model.WorkoutLogViewModel
import com.example.workoutlog.model.WorkoutLogViewModelFactory
import com.google.android.material.snackbar.Snackbar

// Fragment to add or update an exercise in the database.
class AddExerciseFragment : Fragment() {
    private val viewModel: WorkoutLogViewModel by activityViewModels {
        WorkoutLogViewModelFactory(
            (activity?.application as WorkoutLogApplication).database.exerciseDao()
        )
    }

    // Make the nav args from the nav_graph accessible.
    private val detailNavigationArgs: ExerciseDetailFragmentArgs by navArgs()
    private val addExerciseNavArgs: AddExerciseFragmentArgs by navArgs()

    lateinit var exercise: Exercise

    private var _binding: FragmentAddExerciseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Returns true if EditTexts not empty
    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            binding.exerciseName.text.toString(),
            binding.weight.text.toString(),
            binding.reps.text.toString(),
        )
    }

    // Binds views with the passed in exercise information.
    private fun bind(exercise: Exercise) {
        binding.apply {
            exerciseName.setText(exercise.name, TextView.BufferType.SPANNABLE)
            weight.setText(exercise.weight.toString(), TextView.BufferType.SPANNABLE)
            reps.setText(exercise.reps.toString(), TextView.BufferType.SPANNABLE)
            saveAction.setOnClickListener { updateExercise() }
        }
    }

    // Updates the existing exercise in the database and navigates up.
    private fun updateExercise() {
        if (isEntryValid()) {
            viewModel.updateExercise(
                this.detailNavigationArgs.id,
                this.addExerciseNavArgs.muscleGroup,
                this.binding.exerciseName.text.toString(),
                this.binding.weight.text.toString(),
                this.binding.reps.text.toString()
            )
            Snackbar.make(binding.root, "Exercise Updated", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    // Inserts the new exercise into the database and navigates up.
    private fun addNewExercise() {
        if (isEntryValid()) {
            viewModel.addNewExercise(
                binding.exerciseName.text.toString(),
                addExerciseNavArgs.muscleGroup,
                binding.weight.text.toString(),
                binding.reps.text.toString()
            )
            Snackbar.make(binding.root, "Exercise Added", Snackbar.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
    }

    // Id navigation argument determines whether to edit exercise or add new exercise.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = detailNavigationArgs.id
        if (id > 0) {
            viewModel.retrieveExercise(id)
                .observe(this.viewLifecycleOwner) { selectedExercise ->
                    exercise = selectedExercise
                    bind(exercise)
                }
        }else {
                binding.saveAction.setOnClickListener {
                    addNewExercise()
                }
            }
    }
    // Hide keyboard
    override fun onDestroyView() {
        super.onDestroyView()

        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            requireActivity().currentFocus?.windowToken, 0
        )
        _binding = null
    }
}