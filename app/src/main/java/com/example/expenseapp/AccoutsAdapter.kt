package com.example.expenseapp

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class AccountsAdapter(val context: Context,val listItem: List<String>): RecyclerView.Adapter<AccountsAdapter.AccountsHolder>(){


    class AccountsHolder(itemView: View) : ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: AccountsHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}