package ru.netology.cookbook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.cookbook.Steps
import ru.netology.cookbook.databinding.StepsListEditBinding

class StepsEditAdapter(
    private val interactionListener: SingleRecipeInteractionListener
) : ListAdapter<Steps, StepsEditAdapter.StepsViewHolder>(DiffCallback) {

    class StepsViewHolder(
        private val binding: StepsListEditBinding,
        listener: SingleRecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var steps: Steps

        init {
            binding.deleteStep.setOnClickListener {
                listener.onStepDeleteClicked(steps)
            }
        }

        fun bind(steps: Steps) {
            this.steps = steps
            with(binding) {
                stepNumber.text = steps.stepOrder.toString()
                editStepText.setText(steps.stepText)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        Log.d("ViewHolder", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        //создаем view
        val binding = StepsListEditBinding.inflate(
            inflater, parent, false
        )
        return StepsViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
//        метод перезаполняет неиспользуемую вью и подставляет ее
        Log.d("ViewHolder", "onBindViewHolder")
        val step = getItem(position)
        holder.bind(step)

    }


    private object DiffCallback : DiffUtil.ItemCallback<Steps>() {
        override fun areItemsTheSame(oldItem: Steps, newItem: Steps) =
            oldItem.stepOrder == newItem.stepOrder


        override fun areContentsTheSame(oldItem: Steps, newItem: Steps) =
            oldItem == newItem

    }

}
