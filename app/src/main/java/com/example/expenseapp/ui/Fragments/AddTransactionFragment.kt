package com.example.expenseapp.ui.Fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.expenseapp.AccountsAdapter
import com.example.expenseapp.databinding.BottomDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar
import com.example.expenseapp.R
import com.example.expenseapp.databinding.CategoryFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


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

        EventBus.builder().installDefaultEventBus()

        // Post the message event to EventBus
//        val messageEvent = MessageEvent("Hello, EventBus!")
        EventBus.getDefault().register(this)

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

        binding.accountCategory.setOnClickListener {
            var acc = SelectAccountFragment()
            acc.show(childFragmentManager, "a")

        }





        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateDate(myCalander: Calendar) {
        binding.selectDateButton.text =
            myCalander.get(Calendar.YEAR).toString() + "/" + myCalander.get(Calendar.MONTH)
                .toString() + "/" + myCalander.get(Calendar.DAY_OF_MONTH).toString()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCategorySelected(category: String) {
        Log.d("SecondActivity", "Received message:" + category + " \"")
        binding.selectCategory.text = category
    }


}