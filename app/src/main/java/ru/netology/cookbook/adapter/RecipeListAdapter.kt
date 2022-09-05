package ru.netology.cookbook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.cookbook.R
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.databinding.RecieptListBinding

class RecipeListAdapter(
    private val interactionListener: RecipeListInteractionListener
) : ListAdapter<Recipe, RecipeListAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val binding: RecieptListBinding,
        listener: RecipeListInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        init {
            binding.mainPhoto.setOnClickListener {
                listener.onRecipeClicked(recipe)
            }

            binding.textViewAuthor.setOnClickListener {
                listener.onRecipeClicked(recipe)
            }

            binding.textViewCategory.setOnClickListener {
                listener.onRecipeClicked(recipe)
            }


        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe
            with(binding) {
                textViewAuthor.text = recipe.author
                textViewCategory.text = recipe.category.key
                textViewRecipeName.text = recipe.name
                if (recipe.picture != "no") {
                    mainPhoto.setImageURI(recipe.picture.toUri())
                } else {
                    mainPhoto.setImageResource(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //создаем view
        val binding = RecieptListBinding.inflate(
            inflater, parent, false
        )
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        метод перезаполняет неиспользуемую вью и подставляет ее
        val recipe = getItem(position)
        holder.bind(recipe)

    }


    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem

    }

}
