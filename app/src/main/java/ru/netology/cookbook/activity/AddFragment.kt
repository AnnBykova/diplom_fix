package ru.netology.cookbook.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity.apply
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.view.GravityCompat.apply
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.cookbook.Category
import ru.netology.cookbook.Recipe
import ru.netology.cookbook.databinding.FragmentAddBinding
import ru.netology.cookbook.util.IntArg
import ru.netology.cookbook.util.StringArg
import ru.netology.cookbook.viewModel.RecipeViewModel

class AddFragment : Fragment () {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddBinding.inflate(inflater, container, false)
        val viewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)
        binding.newRecipeName.requestFocus()
        val activityPhotoLauncher = registerForActivityResult(PictureResultContract) { result ->
            viewModel.addPhotoClicked.value=result
        }
        binding.buttonPicture.setOnClickListener {
            activityPhotoLauncher.launch()
        }

        binding.buttonSave.setOnClickListener {
            val recipeCategory = when {
                binding.radioButton1.isChecked -> Category.Russian
                binding.radioButton2.isChecked-> Category.European
                binding.radioButton3.isChecked-> Category.Asian
                binding.radioButton4.isChecked-> Category.PanAsian
                binding.radioButton5.isChecked -> Category.Oriental
                binding.radioButton6.isChecked-> Category.American
                binding.radioButton7.isChecked-> Category.Mediterranean
                else -> Category.Russian
            }
            val recipe = Recipe(
                id = 0,
                name = binding.newRecipeName.text.toString(),
                author = binding.newAuthor.text.toString(),
                components = binding.newComponents.text.toString(),
                cooking = binding.newCook.text.toString(),
                category = recipeCategory,
                isLiked = binding.like.isChecked,
                picture = viewModel.addPhotoClicked.value?: "no")
                viewModel.onSaveButtonClicked(recipe)

            findNavController().navigateUp()
        }
    return binding.root
    }
    companion object {
        var Bundle.stringArg: String by StringArg
        const val MAIN_PHOTO ="mainPhoto"
    }


        object PictureResultContract : ActivityResultContract <Unit, String> () {
        override fun createIntent(context: Context, input: Unit) : Intent {
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