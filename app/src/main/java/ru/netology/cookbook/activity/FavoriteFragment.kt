package ru.netology.cookbook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.cookbook.R
import ru.netology.cookbook.activity.RecipeFragment.Companion.longArg
import ru.netology.cookbook.adapter.RecipeListAdapter
import ru.netology.cookbook.databinding.FragmentFeedBinding
import ru.netology.cookbook.viewModel.RecipeListViewModel

class FavoriteFragment : Fragment () {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        val viewModel: RecipeListViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val adapter = RecipeListAdapter(viewModel)
        binding.recipeRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipe ->
            val favorites = recipe.filter { it.isLiked }
            adapter.submitList(favorites)
            if (favorites.isNotEmpty()) binding.backgroundFoto.visibility= View.GONE
        }

        viewModel.showRecipe.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_fragment_favorite_to_recipeFragment2,
                Bundle().apply { longArg=it }
            )
        }
        return binding.root
    }

    }

