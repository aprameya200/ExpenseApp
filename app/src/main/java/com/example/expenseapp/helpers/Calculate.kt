package com.example.expenseapp.helpers

import com.example.expenseapp.Entity.Money
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.enums.TransactionType
import java.text.DecimalFormat

class Calculate {

    companion object {
        fun calculateTotal(transactions: List<Transactions>): Money {

            var totalIncome = 0.0
            var totalExpense = 0.0
            var totalMoney = 0.0

            for (transaction in transactions) {

                when (transaction.type) {
                    TransactionType.INCOME -> totalIncome += transaction.amount
                    TransactionType.EXPENSE -> totalExpense += transaction.amount
                }
                totalMoney = totalIncome - totalExpense
            }

            return Money(totalIncome, totalExpense, totalMoney)
        }

        fun roundDoubleUsingStringFormat(value: Double): Double {
            val format = "%.${2}f"
            return format.format(value).toDouble()
        }
    }


}