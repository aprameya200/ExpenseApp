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


class AddTransactionFragment : BottomSheetDialogFragment() {

    lateinit var binding: BottomDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomDialogBinding.inflate(layoutInflater)

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

        binding.selectCategory.setOnClickListener{
            var cat = SelectCategoryFragment()
            cat.show(childFragmentManager,"hi") //shows the dialog framgnet
        }

        binding.incomeToggle.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.grey) }
                binding.expenseToggle.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.white) }
            } else {
                button.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.white) }
                binding.expenseToggle.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.grey) }
            }
        }

        binding.expenseToggle.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                button.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.grey) }
                binding.incomeToggle.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.white) }
            } else {
                button.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.white) }
                binding.incomeToggle.backgroundTintList =
                    context?.let { ContextCompat.getColorStateList(it, R.color.grey) }
            }
        }


        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateDate(myCalander : Calendar) {
        binding.selectDateButton.text =
            myCalander.get(Calendar.YEAR).toString() + "/" + myCalander.get(Calendar.MONTH)
                .toString() + "/" + myCalander.get(Calendar.DAY_OF_MONTH).toString()    }
}