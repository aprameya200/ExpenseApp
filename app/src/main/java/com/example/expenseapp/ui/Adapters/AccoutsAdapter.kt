package com.example.expenseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.expenseapp.ui.Fragments.SelectAccountFragment

class AccountsAdapter(val context: Context, val listItem: List<String>) :
    RecyclerView.Adapter<AccountsAdapter.AccountsHolder>() {

    class AccountsHolder(itemView: View) : ViewHolder(itemView) {
        val textValue: TextView = itemView.findViewById(R.id.account_name)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.account_list, parent, false)
        return AccountsHolder(view)
    }

    override fun onBindViewHolder(holder: AccountsHolder, position: Int) {
        holder.textValue.text = listItem[position]
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

}