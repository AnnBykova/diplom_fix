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
import ru.netology.cookbook.databinding.FragmentRecipeBinding
import ru.netology.cookbook.util.IntArg
import ru.netology.cookbook.viewModel.RecipeViewModel

class RecipeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.find { it.id == viewModel.showRecipe.value } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            with (binding.recipeLayout){
                textViewRecipeName.text = recipe.name
                textViewCategory.text = recipe.category.key
                textViewIngredients.text = recipe.components
                textViewAuthor.text = recipe.author
                textViewCook.text = recipe.cooking
                like.isChecked = recipe.isLiked
                if (recipe.picture != "no") {
                    mainPhoto.setImageURI(recipe.picture.toUri())
                }
                else {
                    mainPhoto.setImageResource(R.drawable.ic_launcher_foreground)
                }
            }
            binding.recipeLayout.like.setOnClickListener{
                viewModel.onLikeClicked(recipe)
            }

            val popupMenu =
                 PopupMenu(this.context, binding.recipeLayout.options).apply {
                    inflate(R.menu.menu_options)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                viewModel.onRemoveClicked(recipe.id)
                                true
                            }
                            R.id.edit -> {
                                viewModel.onEditClicked(recipe.id)
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

        viewModel.editRecipe.observe(viewLifecycleOwner){
            findNavController().navigate(
                R.id.action_recipeFragment2_to_editFragment,
                Bundle().apply { intArg=it }
            )
        }

        return binding.root


    }

    companion object {
        var Bundle.intArg: Int by IntArg
    }
}

