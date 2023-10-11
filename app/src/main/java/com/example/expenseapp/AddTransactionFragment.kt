package com.example.expenseapp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.expenseapp.databinding.BottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import androidx.fragment.app.FragmentManager
import com.example.expenseapp.databinding.CategoryFragmentBinding


class AddTransactionFragment : BottomSheetDialogFragment() {

    lateinit var binding: BottomDialogBinding
    lateinit var categoryBinding: CategoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomDialogBinding.inflate(layoutInflater)
        categoryBinding = CategoryFragmentBinding.inflate(layoutInflater)

        var incomeButton = binding.incomeToggle
        var expenseButton = binding.expenseToggle


        val myCalander = Calendar.getInstance()


        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalander.set(Calendar.YEAR, year)
            myCalander.set(Calendar.MONTH, month)
            myCalander.set(Calendar.DAY_OF_MONTH, day)

            updateDate(myCalander)

        }

        binding.selectDateButton.setOnClickListener {

            DatePickerDialog(
                requireContext(),
                datePicker, //values selected here will be saved under myCalander instance.......
                myCalander.get(Calendar.YEAR),
                myCalander.get(Calendar.MONTH),
                myCalander.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

        binding.selectCategory.setOnClickListener {
            var cat = SelectCategoryFragment()
            cat.show(childFragmentManager, "hi") //shows the dialog framgnet
        }


        incomeButton.isSelected = true

        incomeButton.setOnClickListener {
            incomeButton.backgroundTintList =
                context?.let { ContextCompat.getColorStateList(it, R.color.green) }
            incomeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.expenseToggle.backgroundTintList =
                context?.let { ContextCompat.getColorStateList(it, R.color.white) }
            expenseButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))

        }

        expenseButton.setOnClickListener {

            expenseButton.backgroundTintList =
                context?.let { ContextCompat.getColorStateList(it, R.color.red) }
            expenseButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.incomeToggle.backgroundTintList =
                context?.let { ContextCompat.getColorStateList(it, R.color.white) }
            incomeButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))

        }


        val listOfCategories = listOf(
            categoryBinding.cashCategory,
            categoryBinding.businessCategory,
            categoryBinding.investmentCategory,
            categoryBinding.loanCategory,
            categoryBinding.othersCategory,
            categoryBinding.rentCategory
        )

        for (category in listOfCategories) {
            category.setOnClickListener {
                val categorySelection = when (category) { //this is the selected category
                    categoryBinding.cashCategory -> "Cash"
                    categoryBinding.businessCategory -> "Business"
                    categoryBinding.investmentCategory -> "Investment"
                    categoryBinding.loanCategory -> "Loan"
                    categoryBinding.othersCategory -> "Others"
                    categoryBinding.rentCategory -> "Rent"
                    else -> "None"
                }
            }
        }


        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateDate(myCalander: Calendar) {
        binding.selectDateButton.text =
            myCalander.get(Calendar.YEAR).toString() + "/" + myCalander.get(Calendar.MONTH)
                .toString() + "/" + myCalander.get(Calendar.DAY_OF_MONTH).toString()
    }
}