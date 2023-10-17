package com.example.expenseapp.Entity

import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import java.util.Date

data class Transactions(
    var title: String = "",
    var category: Category = Category.OTHERS,
    var type: TransactionType = TransactionType.INCOME,
    var account: String  = "",
    var note: String = "",
    var date: Date = Date(),
    var amount: Double = 0.0
) {
}