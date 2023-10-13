package com.example.expenseapp.Entity

import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import java.util.Date

data class Transactions(
    val id: Int,
    val title: String,
    val category: Category,
    val type: TransactionType,
    val account: String,
    val note: String,
    val date: Date,
    val amount: Double
) {
}