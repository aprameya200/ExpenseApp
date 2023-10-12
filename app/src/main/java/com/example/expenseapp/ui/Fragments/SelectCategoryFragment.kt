package com.example.expenseapp.ui.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.expenseapp.databinding.CategoryFragmentBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SelectCategoryFragment : DialogFragment() {

    lateinit var binding: CategoryFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoryFragmentBinding.inflate(layoutInflater)

        var categorySelection = "Select Category"


        val listOfCategories = listOf(
            binding.cashCategory,
            binding.businessCategory,
            binding.investmentCategory,
            binding.loanCategory,
            binding.othersCategory,
            binding.rentCategory
        )

        for (category in listOfCategories) {
            category.setOnClickListener {
                categorySelection = when (category) { //this is the selected category
                    binding.cashCategory -> "Cash"
                    binding.businessCategory -> "Business"
                    binding.investmentCategory -> "Investment"
                    binding.loanCategory -> "Loan"
                    binding.othersCategory -> "Others"
                    binding.rentCategory -> "Rent"
                    else -> "None"
                }

                EventBus.getDefault().register(this)
                EventBus.getDefault().post(categorySelection)
                onStop()

            }

        }


        return binding.root
    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this) // Unregister when the activity is no longer visible
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCategorySelected(category: String) {}

}

