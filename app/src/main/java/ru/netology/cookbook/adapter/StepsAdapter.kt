package ru.netology.cookbook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.Steps
import ru.netology.cookbook.databinding.RecieptListBinding
import ru.netology.cookbook.databinding.StepsListBinding

class StepsAdapter (
    private val interactionListener: RecipeInteractionListener
        ) : ListAdapter<Steps, StepsAdapter.StepsViewHolder>(DiffCallback) {

    class StepsViewHolder(
        private val binding: StepsListBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var steps: Steps

        init {
            binding.addStep.setOnClickListener {
                stepNumber += 1
                    // TODO сделать следующий элемент видимым
            }


        }

        fun bind(steps: Steps) {
            this.steps = steps
            with(binding) {
                stepNumber.text = steps.stepOrder.toString()
                newStep.setText(steps.stepText)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        Log.d("ViewHolder", "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        //создаем view
        val binding = StepsListBinding.inflate(
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
            oldItem.stepId == newItem.stepId


        override fun areContentsTheSame(oldItem: Steps, newItem: Steps) =
            oldItem == newItem

    }

    companion object{
        var stepNumber: Int = 0
    }
}
