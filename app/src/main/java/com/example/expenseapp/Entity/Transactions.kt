package com.example.expenseapp.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import java.util.Date

@Entity(tableName =  "transactions")
data class Transactions(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var title: String = "",
    var category: Category = Category.OTHERS,
    var type: TransactionType = TransactionType.INCOME,
    var account: String  = "",
    var note: String = "",
    var date: Date = Date(),
    var amount: Double = 0.0
) {
}