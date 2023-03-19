package com.example.workoutlog.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.workoutlog.databinding.FragmentSplashBinding

// Starting fragment to display the name of the application.
class SplashFragment : Fragment() {

    // Binding object instance to the corresponding fragment_splash.xml layout.
    private var binding: FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentSplashBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // call the method to move to teh start fragment.
        goToStart()
    }

    // Method to move to the ne next fragment with a delay.
    private fun goToStart() {
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed(
            {
                val action = SplashFragmentDirections.actionSplashFragmentToStartFragment()
                this.findNavController().navigate(action)
            }, 3000)
    }
}