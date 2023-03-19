package com.example.workoutlog.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.workoutlog.databinding.FragmentStartBinding
import com.example.workoutlog.model.WorkoutLogViewModel

// First screen of the app where the user decides which muscle group to view.
class StartFragment : Fragment() {

    // Binding object instance to the corresponding fragment_start.xml layout
    private var binding: FragmentStartBinding? = null

    // view model declaration
    private val sharedViewModel: WorkoutLogViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentStartBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.startFragment = this
    }

    fun goToMuscleGroup(muscleGroup: String) {
        val action = StartFragmentDirections
            .actionStartFragmentToMuscleGroupFragment(muscleGroup)
        this.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}