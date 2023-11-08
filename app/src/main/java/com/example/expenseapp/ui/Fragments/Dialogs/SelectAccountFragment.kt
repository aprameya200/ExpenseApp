package com.example.expenseapp.ui.Fragments.Dialogs

import android.accounts.Account
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.AccountsAdapter
import com.example.expenseapp.Entity.UserAccounts
import com.example.expenseapp.databinding.AccountFragmentBinding
import com.example.expenseapp.databinding.AccountListBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SelectAccountFragment : DialogFragment(),AccountsAdapter.OnItemClickListener {

    lateinit var binding: AccountFragmentBinding
    lateinit var recyclerView: RecyclerView
    lateinit var accountListBinding: AccountListBinding


    override fun onItemClick(itemData: String) {

        val itemName = itemData
        dismiss() // Close the DialogFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountFragmentBinding.inflate(layoutInflater)
        accountListBinding = AccountListBinding.inflate(layoutInflater)

        val listOfAccounts = listOf<UserAccounts>(
            UserAccounts("Cash", 12000.0),
            UserAccounts("FonePay", 5000.0),
            UserAccounts("Savings", 152000.0),
            UserAccounts("Salary", 20000.0),
            UserAccounts("Reward", 4000.0),
        )

        recyclerView = binding.recyclerView

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        val adapter = AccountsAdapter(requireContext(), listOfAccounts)

        adapter.listener = this // Set the listener

        recyclerView.adapter = adapter



        recyclerView.setOnClickListener{
            Log.d("Aa",AccountsAdapter.accountSelection.clickData.toString())

        }


        return binding.root
    }



}