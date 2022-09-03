package ru.netology.cookbook.activity

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import ru.netology.cookbook.R
import ru.netology.cookbook.activity.RecipeFragment.Companion.intArg
import ru.netology.cookbook.adapter.RecipeListAdapter
import ru.netology.cookbook.databinding.FragmentFeedBinding
import ru.netology.cookbook.viewModel.RecipeViewModel


class FeedFragment : Fragment () {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)
        binding.recipeRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { recipe ->
            adapter.submitList(recipe)
            if (recipe.isNotEmpty()) binding.backgroundFoto.visibility = View.GONE
        }
        viewModel.showRecipe.observe(viewLifecycleOwner) {
            findNavController().navigate(
                R.id.action_fragment_feed_to_recipeFragment2,
                Bundle().apply { intArg = it }
            )
        }
        return binding.root
    }
    val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
    val adapter by lazy { RecipeListAdapter(viewModel) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_app_bar, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter -> {
                        //сделать фильтр по категориям
                        true
                    }
                    R.id.search -> {
                        val searchView: SearchView = menuItem.actionView as SearchView
                        searchView.isSubmitButtonEnabled = true
                        searchView.setOnQueryTextListener(object :
                            SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                if (query != null) {
                                    searchDatabase(query)
                                }
                                return true
                            }
                            override fun onQueryTextChange(query: String?): Boolean {
                                if (query != null) {
                                    searchDatabase(query)
                                }
                                return true
                            }
                            private fun searchDatabase(query: String) {
                                val searchQuery = "%$query%"
                                viewModel.searchDataBase(searchQuery).observe(viewLifecycleOwner) {list->
                                    adapter.submitList(list)
                                }
                            }
                        }
                        )
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
