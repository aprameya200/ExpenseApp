package com.example.expenseapp.ui.Adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.Entity.Transactions
import com.example.expenseapp.R
import com.example.expenseapp.enums.Category
import com.example.expenseapp.enums.TransactionType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class ShowEmptyAdapter(val context: Context) :
    RecyclerView.Adapter<ShowEmptyAdapter.EmptyHolder>() {


    class EmptyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image : ImageView = itemView.findViewById(R.id.empty_logo)
        val text : TextView = itemView.findViewById(R.id.no_transactions)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.no_transactions_display, parent, false)

        return EmptyHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }


    override fun onBindViewHolder(holder: EmptyHolder, position: Int) {


    }





}
