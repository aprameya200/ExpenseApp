package com.example.expenseapp.ui.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.R
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Create instance of TransactionDiffCallback as constructor in this class
class TransactionListAdapter : ListAdapter<Transactions, TransactionListAdapter.TransactionViewHolder>(TransactionDiffCallback()) {
    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logo: ImageView = itemView.findViewById(R.id.transactionLogo)
        val title: TextView = itemView.findViewById(R.id.transactionTitle)
        val tag: TextView = itemView.findViewById(R.id.tag)
        val date: TextView = itemView.findViewById(R.id.transactionDate)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val arrow: ImageView = itemView.findViewById(R.id.typeArrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.transactions, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = getItem(position)
        holder.logo.setImageResource(setImageBasedOnCategory(item.category))

        holder.logo.backgroundTintList = holder.itemView.context?.let {
            ContextCompat.getColorStateList(
                it,
                setColorBasedOnExpenseIncome(item.type)
            )
        }

        holder.title.text = item.title
        holder.tag.text = Category.getCategoryName(item.category)
        holder.tag.backgroundTintList =
            holder.itemView.context?.let {
                ContextCompat.getColorStateList(
                    it,
                    setColotBasedOnTag(item.category)
                )
            }

        holder.date.text = formatDate(item.date)


        //amount color
        holder.amount.text = item.amount.toString()
        holder.amount.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                setColorBasedOnExpenseIncome(item.type)
            )
        )

        holder.arrow.setImageResource(setArrowImage(item.type))

        if (item.amount.toString() == ""){
            Log.d("Empty", "Empty")
        }


    }


    fun setArrowImage(transaction: TransactionType): Int {
        return when (transaction) {
            TransactionType.EXPENSE -> R.drawable.baseline_arrow_drop_down_24
            TransactionType.INCOME -> R.drawable.baseline_arrow_drop_up_24
        }
    }


    fun setImageBasedOnCategory(category: Category): Int {
        return when (category) {
            Category.CASH -> R.drawable.baseline_attach_money_24
            Category.RENT -> R.drawable.baseline_house_24
            Category.BUSINESS -> R.drawable.baseline_business_center_24
            Category.INVESTMENT -> R.drawable.baseline_bar_chart_24
            Category.LOAN -> R.drawable.baseline_handshake_24
            else -> R.drawable.baseline_account_balance_wallet_24
        }
    }

    fun setColotBasedOnTag  (category: Category): Int {
        return when (category) {
            Category.CASH -> R.color.cash_color
            Category.RENT -> R.color.rent_color
            Category.BUSINESS -> R.color.business_color
            Category.INVESTMENT -> R.color.investment_color
            Category.LOAN -> R.color.loan_color
            else -> R.color.others_color
        }
    }

    fun setColorBasedOnExpenseIncome(transaction: TransactionType): Int {
        return when (transaction) {
            TransactionType.EXPENSE -> R.color.red
            TransactionType.INCOME -> R.color.green
        }
    }

    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }
}
