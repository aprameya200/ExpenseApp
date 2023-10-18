package com.example.expenseapp.enums

enum class Category {
    CASH,
    BUSINESS,
    INVESTMENT, LOAN, RENT, OTHERS;

    companion object{
        fun getCategoryName(category: Category): String {
            val stringCategory = when (category) {
                CASH -> "CASH"
                BUSINESS -> "BUSINESS"
                INVESTMENT -> "INVESTMENT"
                LOAN -> "LOAN"
                OTHERS -> "OTHERS"
                RENT -> "RENT"
                else -> "NONE"
            }
            return stringCategory
        }

        fun setEnumFromString(category: String): Category {
            val stringCategory = when (category) {
                "CASH" -> CASH
                "BUSINESS" -> BUSINESS
                "INVESTMENT" -> INVESTMENT
                "LOAN" -> LOAN
                "RENT" -> RENT
                else -> OTHERS
            }
            return stringCategory
        }
    }


}