package com.example.expenseapp.enums

enum class TransactionType {
    INCOME,EXPENSE;

    companion object{
        fun setTypeFromString(type: String): TransactionType{
            return when(type){
                "INCOME" -> TransactionType.INCOME
                "EXPENSE" -> TransactionType.EXPENSE
                else -> INCOME
            }
        }
    }


}