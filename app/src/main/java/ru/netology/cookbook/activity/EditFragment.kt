package ru.netology.cookbook.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.launch
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.cookbook.Category
import ru.netology.cookbook.R
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.activity.RecipeFragment.Companion.intArg
import ru.netology.cookbook.databinding.FragmentAddBinding
import ru.netology.cookbook.databinding.FragmentEditBinding
import ru.netology.cookbook.viewModel.RecipeViewModel

class EditFragment : Fragment () {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.fragmentEdit.newRecipeName.requestFocus()

        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.find { it.id == viewModel.editRecipe.value } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            with(binding.fragmentEdit) {
                if (recipe.picture != "no") {
                    newPhoto.setImageURI(recipe.picture.toUri())
                }
                else {
                    newPhoto.setImageResource(R.drawable.ic_launcher_foreground)
                }
                newRecipeName.setText(recipe.name)
                newComponents.setText(recipe.components)
                newAuthor.setText(recipe.author)
                newCook.setText(recipe.cooking)
                like.isChecked = recipe.isLiked
                radioButton1.isChecked = recipe.category == Category.Russian
                radioButton2.isChecked = recipe.category == Category.European
                radioButton3.isChecked = recipe.category == Category.Asian
                radioButton4.isChecked = recipe.category == Category.PanAsian
                radioButton5.isChecked = recipe.category == Category.Oriental
                radioButton6.isChecked = recipe.category == Category.American
                radioButton7.isChecked = recipe.category == Category.Mediterranean
            }
        }
            val activityPhotoLauncher = registerForActivityResult(EditPictureResultContract) { result ->
                    viewModel.editPhotoClicked.value = result
                }
            binding.fragmentEdit.buttonPicture.setOnClickListener {
                activityPhotoLauncher.launch()
            }

            binding.fragmentEdit.buttonSave.setOnClickListener {
                val recipeCategory = when {
                    binding.fragmentEdit.radioButton1.isChecked -> Category.Russian
                    binding.fragmentEdit.radioButton2.isChecked -> Category.European
                    binding.fragmentEdit.radioButton3.isChecked -> Category.Asian
                    binding.fragmentEdit.radioButton4.isChecked -> Category.PanAsian
                    binding.fragmentEdit.radioButton5.isChecked -> Category.Oriental
                    binding.fragmentEdit.radioButton6.isChecked -> Category.American
                    binding.fragmentEdit.radioButton7.isChecked -> Category.Mediterranean
                    else -> Category.Russian
                }
                val recipe = Recipe(
                    id = arguments?.intArg ?: 0,
                    name = binding.fragmentEdit.newRecipeName.text.toString(),
                    author = binding.fragmentEdit.newAuthor.text.toString(),
                    components = binding.fragmentEdit.newComponents.text.toString(),
                    cooking = binding.fragmentEdit.newCook.text.toString(),
                    category = recipeCategory,
                    isLiked = binding.fragmentEdit.like.isChecked,
                    picture = viewModel.addPhotoClicked.value ?: "no"
                )
                viewModel.onUpdateButtonClicked(recipe)

                findNavController().navigate(
                    R.id.action_editFragment_to_fragment_feed
                )
            }
        return binding.root
    }

    object EditPictureResultContract : ActivityResultContract<Unit, String>() {
        override fun createIntent(context: Context, input: Unit): Intent {
            return Intent().apply {
                action = Intent.ACTION_PICK
                type = "image/*"
            }
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            return if (resultCode == Activity.RESULT_OK) {
                intent?.data.toString()
            } else "no"
        }
    }
}
