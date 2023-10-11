package com.example.expenseapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.databinding.AccountFragmentBinding

class SelectAccountFragment: DialogFragment() {

    lateinit var binding : AccountFragmentBinding
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(layoutInflater)

        val listOfAccounts = listOf<String>("Hi","Ok","Bye")


        recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        recyclerView.adapter = AccountsAdapter(requireContext(),listOfAccounts)


        return binding.root
    }
}