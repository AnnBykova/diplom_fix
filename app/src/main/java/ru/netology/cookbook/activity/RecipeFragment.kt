package ru.netology.cookbook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.cookbook.R
import ru.netology.cookbook.adapter.StepsAdapter
import ru.netology.cookbook.databinding.FragmentRecipeBinding
import ru.netology.cookbook.util.IntArg
import ru.netology.cookbook.util.LongArg
import ru.netology.cookbook.viewModel.SingleRecipeViewModel

class RecipeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val viewModelSingle: SingleRecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val adapterSteps = StepsAdapter(viewModelSingle)
        binding.recipeLayout.stepsRecyclerView.adapter = adapterSteps


        viewModelSingle.data.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.find { it.id == arguments?.longArg } ?: run {
                findNavController().navigateUp()
                return@observe
            }

            with(binding.recipeLayout) {
                textViewRecipeName.text = recipe.name
                textViewCategory.text = recipe.category.key
                textViewIngredients.text = recipe.components
                textViewAuthor.text = recipe.author
                like.isChecked = recipe.isLiked
                if (recipe.picture != "no") {
                    mainPhoto.setImageURI(recipe.picture.toUri())
                } else {
                    mainPhoto.setImageResource(R.drawable.ic_launcher_foreground)
                }
                val steps = viewModelSingle.getStepsByRecipeId(recipe.id).sortedBy { it.stepOrder }
                Bundle().apply { intArg = steps.size }
                adapterSteps.submitList(steps)
            }
            binding.recipeLayout.like.setOnClickListener {
                viewModelSingle.onLikeClicked(recipe)
            }

            val popupMenu =
                PopupMenu(this.context, binding.recipeLayout.options).apply {
                    inflate(R.menu.menu_options)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                viewModelSingle.onRemoveClicked(recipe.id)
                                true
                            }
                            R.id.edit -> {
                                viewModelSingle.onEditClicked(recipe.id)
                                true
                            }
                            else -> false
                        }
                    }
                }
            binding.recipeLayout.options.setOnClickListener {
                popupMenu.show()
            }

        }

        viewModelSingle.editRecipe.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_recipeFragment2_to_editFragment,
                Bundle().apply { longArg = it }
            )
        }

        return binding.root

    }

    companion object {
        var Bundle.longArg: Long by LongArg
        var Bundle.intArg: Int by IntArg
    }
}

