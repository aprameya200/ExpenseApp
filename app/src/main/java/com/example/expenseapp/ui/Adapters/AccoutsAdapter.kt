package com.example.expenseapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.expenseapp.Entity.UserAccounts
import com.example.expenseapp.ui.Fragments.SelectAccountFragment

class AccountsAdapter(val context: Context, val listItem: List<UserAccounts>) :
    RecyclerView.Adapter<AccountsAdapter.AccountsHolder>() {

    object accountSelection {
        var clickData: MutableLiveData<String> = MutableLiveData()
    }

    interface OnItemClickListener {
        fun onItemClick(itemData: String)
    }

    var listener: OnItemClickListener? = null


    class AccountsHolder(itemView: View) : ViewHolder(itemView) {
        val textValue: TextView = itemView.findViewById(R.id.account_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.account_list, parent, false)
        return AccountsHolder(view)
    }

    override fun onBindViewHolder(holder: AccountsHolder, position: Int) {
        holder.textValue.text = listItem[position].accountName

        holder.textValue.setOnClickListener {
            AccountsAdapter.accountSelection.clickData.value = listItem[position].accountName
            listener?.onItemClick(listItem[position].accountName)
            Log.d("HEHHO", accountSelection.clickData.toString())
        }

    }


    override fun getItemCount(): Int {
        return listItem.size
    }

}