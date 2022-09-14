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
import ru.netology.cookbook.Steps
import ru.netology.cookbook.activity.RecipeFragment.Companion.intArg
import ru.netology.cookbook.activity.RecipeFragment.Companion.longArg
import ru.netology.cookbook.adapter.StepsEditAdapter
import ru.netology.cookbook.databinding.FragmentEditBinding
import ru.netology.cookbook.viewModel.SingleRecipeViewModel

class EditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditBinding.inflate(inflater, container, false)
        val viewModelSingle: SingleRecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val adapterSteps = StepsEditAdapter(viewModelSingle)
        binding.fragmentEdit.stepsRecyclerView.adapter = adapterSteps
        binding.fragmentEdit.newRecipeName.requestFocus()

        viewModelSingle.data.observe(viewLifecycleOwner) { recipes ->
            val recipe = recipes.find { it.id == viewModelSingle.editRecipe.value } ?: run {
                findNavController().navigateUp()
                return@observe
            }
            with(binding.fragmentEdit) {
                if (recipe.picture != "no") {
                    newPhoto.setImageURI(recipe.picture.toUri())
                } else {
                    newPhoto.setImageResource(R.drawable.ic_launcher_foreground)
                }
                newRecipeName.setText(recipe.name)
                newComponents.setText(recipe.components)
                newAuthor.setText(recipe.author)
                like.isChecked = recipe.isLiked
                radioButton1.isChecked = recipe.category == Category.Russian
                radioButton2.isChecked = recipe.category == Category.European
                radioButton3.isChecked = recipe.category == Category.Asian
                radioButton4.isChecked = recipe.category == Category.PanAsian
                radioButton5.isChecked = recipe.category == Category.Oriental
                radioButton6.isChecked = recipe.category == Category.American
                radioButton7.isChecked = recipe.category == Category.Mediterranean

                viewModelSingle.newRecipeSteps.observe(viewLifecycleOwner) { steps ->
                    var steps = steps.filter { it.recipeId == recipe.id }
                        .sortedBy { it.stepOrder }
                    adapterSteps.submitList(steps)
                }
            }
        }
        val activityPhotoLauncher = registerForActivityResult(EditPictureResultContract) { result ->
            viewModelSingle.editPhotoClicked.value = result
        }
        binding.fragmentEdit.buttonPicture.setOnClickListener {
            activityPhotoLauncher.launch()
        }

        binding.fragmentEdit.addStep.setOnClickListener {
            val text = binding.fragmentEdit.newStep.text.toString()
            val recipeId = arguments?.longArg ?: 0L
            val numberOfStep = viewModelSingle.getLastStepOrder(recipeId)
            val newStep = Steps(0L, (numberOfStep + 1), text, recipeId)
            viewModelSingle.onAddStepEditClicked(newStep)
            binding.fragmentEdit.newStep.setText("")
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
                id = arguments?.longArg ?: 0L,
                name = binding.fragmentEdit.newRecipeName.text.toString(),
                author = binding.fragmentEdit.newAuthor.text.toString(),
                components = binding.fragmentEdit.newComponents.text.toString(),
                category = recipeCategory,
                cooking = emptyList<Steps>(),
                isLiked = binding.fragmentEdit.like.isChecked,
                picture = viewModelSingle.addPhotoClicked.value ?: "no"
            )
            viewModelSingle.onUpdateButtonClicked(recipe)

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
