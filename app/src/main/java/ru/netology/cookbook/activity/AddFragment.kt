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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.cookbook.Category
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.adapter.StepsAdapter
import ru.netology.cookbook.databinding.FragmentAdd2Binding
import ru.netology.cookbook.util.StringArg
import ru.netology.cookbook.viewModel.SingleRecipeViewModel

class AddFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdd2Binding.inflate(inflater, container, false)
        val viewModelSingle: SingleRecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val adapterSteps = StepsAdapter(viewModelSingle)
        binding.stepsRecyclerView.adapter = adapterSteps
        binding.newRecipeName.requestFocus()
        viewModelSingle.clearSteps(-1L)

        val activityPhotoLauncher = registerForActivityResult(PictureResultContract) { result ->
            viewModelSingle.addPhotoClicked.value = result
        }
        binding.buttonPicture.setOnClickListener {
            activityPhotoLauncher.launch()
        }

        viewModelSingle.newRecipeSteps.observe(viewLifecycleOwner) { steps ->
            var onlyNewSteps = steps.filter { it.recipeId == -1L }
                .sortedBy { it.stepOrder }
            adapterSteps.submitList(onlyNewSteps)
        }

        binding.addStep.setOnClickListener {
            val text = binding.newStep.text.toString()
            viewModelSingle.onAddStepClicked(StepsAdapter.stepNumber, text)
            StepsAdapter.stepNumber += 1
            binding.newStep.setText("")
        }


        binding.buttonSave.setOnClickListener {
            // определяем категорию
            val recipeCategory = when {
                binding.radioButton1.isChecked -> Category.Russian
                binding.radioButton2.isChecked -> Category.European
                binding.radioButton3.isChecked -> Category.Asian
                binding.radioButton4.isChecked -> Category.PanAsian
                binding.radioButton5.isChecked -> Category.Oriental
                binding.radioButton6.isChecked -> Category.American
                binding.radioButton7.isChecked -> Category.Mediterranean
                else -> Category.Russian
            }
            // получаем список шагов
            val newRecipeSteps =
                viewModelSingle.newRecipeSteps.value?.filter { it.recipeId == -1L && it.stepText != "" }

            val recipe = Recipe(
                id = 0L,
                name = binding.newRecipeName.text.toString(),
                author = binding.newAuthor.text.toString(),
                components = binding.newComponents.text.toString(),
                category = recipeCategory,
                isLiked = binding.like.isChecked,
                picture = viewModelSingle.addPhotoClicked.value ?: "no",
                cooking = newRecipeSteps ?: emptyList()
            )
            viewModelSingle.onSaveButtonClicked(recipe)

            findNavController().navigateUp()
        }
        return binding.root
    }


    object PictureResultContract : ActivityResultContract<Unit, String>() {
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
