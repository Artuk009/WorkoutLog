package com.example.workoutlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutlog.data.Exercise
import com.example.workoutlog.databinding.ExerciseListItemBinding


// adapter for the recyclerview
class ExerciseListAdapter(private val onExerciseClicked: (Exercise) -> Unit) :
    ListAdapter<Exercise, ExerciseListAdapter.ExerciseViewHolder>(DiffCallBack){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        return ExerciseViewHolder(
            ExerciseListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onExerciseClicked(current)
        }
        holder.bind(current)
    }

    class ExerciseViewHolder(private var binding: ExerciseListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(exercise: Exercise) {
                    binding.exerciseName.text = exercise.name
                    binding.weight.text = exercise.weight.toString()
                    binding.reps.text = exercise.reps.toString()
                }
            }

    companion object {
        private val DiffCallBack = object : DiffUtil.ItemCallback<Exercise>() {
            override fun areItemsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Exercise, newItem: Exercise): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}