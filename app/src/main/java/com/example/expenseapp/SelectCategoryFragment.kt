package com.example.expenseapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.expenseapp.databinding.CategoryFragmentBinding

class SelectCategoryFragment: DialogFragment() {

    lateinit var binding: CategoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CategoryFragmentBinding.inflate(layoutInflater)
        return binding.root
    }
}

