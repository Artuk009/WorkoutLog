package com.example.workoutlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workoutlog.WorkoutLogApplication
import com.example.workoutlog.adapter.ExerciseListAdapter
import com.example.workoutlog.databinding.FragmentMuscleGroupBinding
import com.example.workoutlog.model.WorkoutLogViewModel
import com.example.workoutlog.model.WorkoutLogViewModelFactory


class MuscleGroupFragment : Fragment() {
    private val viewModel: WorkoutLogViewModel by activityViewModels {
        WorkoutLogViewModelFactory(
            (activity?.application as WorkoutLogApplication).database.exerciseDao()
        )
    }

    private val muscleGroupNavArgs: MuscleGroupFragmentArgs by navArgs()

    private var _binding: FragmentMuscleGroupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMuscleGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ExerciseListAdapter {
            val action =
                MuscleGroupFragmentDirections
                    .actionMuscleGroupFragmentToExerciseDetailFragment(it.id)
            this.findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerView.adapter = adapter

        when (muscleGroupNavArgs.muscleGroup) {
            "Back Day" -> viewModel.backExercises.observe(this.viewLifecycleOwner)
            { exercises ->
                exercises.let {
                    adapter.submitList(it)
                }
            }
            "Chest Day" -> viewModel.chestExercises.observe(this.viewLifecycleOwner) {
                    exercises ->
                exercises.let {
                    adapter.submitList(it)
                }
            }
            "Shoulder Day" -> viewModel.shoulderExercises.observe(this.viewLifecycleOwner) {
                    exercises ->
                exercises.let {
                    adapter.submitList(it)
                }
            }
            "Leg Day" -> viewModel.legExercises.observe(this.viewLifecycleOwner) {
                    exercises ->
                exercises.let {
                    adapter.submitList(it)
                }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val action = MuscleGroupFragmentDirections
                .actionMuscleGroupFragmentToAddExerciseFragment("Add Exercise",
                    muscleGroupNavArgs.muscleGroup)
            this.findNavController().navigate(action)
        }
    }
}


