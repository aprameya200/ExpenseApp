package com.example.expenseapp.ui.Fragments

import android.accounts.Account
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.AccountsAdapter
import com.example.expenseapp.Entity.UserAccounts
import com.example.expenseapp.databinding.AccountFragmentBinding

class SelectAccountFragment: DialogFragment() {

    lateinit var binding : AccountFragmentBinding
    lateinit var recyclerView: RecyclerView

    interface AccountListener{
        fun onClickAccount(account: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(layoutInflater)

        val listOfAccounts = listOf<String>("Cash","FonePay","Savings","Salary","Rewards")

        recyclerView = binding.recyclerView

        recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
        recyclerView.adapter = AccountsAdapter(requireContext(),listOfAccounts)

        return binding.root
    }
}