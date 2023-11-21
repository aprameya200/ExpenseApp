package com.example.expenseapp.ui.Adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.expenseapp.Entity.Transactions

class TransactionDiffCallback : DiffUtil.ItemCallback<Transactions>() {

    override fun areItemsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Transactions, newItem: Transactions): Boolean {
        return oldItem == newItem
    }
}