package com.example.expenseapp.enums

enum class Category {
    CASH,
    BUSINESS,
    INVESTMENT, LOAN, RENT, OTHERS;

    companion object{
        fun getCategoryName(category: Category): String {
            val stringCategory = when (category) {
                CASH -> "Cash"
                BUSINESS -> "Business"
                INVESTMENT -> "Investment"
                LOAN -> "Loan"
                OTHERS -> "Others"
                RENT -> "Rent"
                else -> "None"
            }
            return stringCategory
        }

        fun setEnumFromString(category: String): Category {
            val stringCategory = when (category) {
                "Cash" -> CASH
                "Business" -> BUSINESS
                "Investment" -> INVESTMENT
                "Loan" -> LOAN
                "Rent" -> RENT
                else -> OTHERS
            }
            return stringCategory
        }
    }


}