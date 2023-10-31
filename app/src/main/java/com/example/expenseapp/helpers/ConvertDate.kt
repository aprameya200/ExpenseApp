package com.example.expenseapp.helpers

import com.google.firebase.Timestamp
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

class ConvertDate {

    companion object {
        fun convertStringToDate(dateString: String): Date? {
            val format = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
            return format.parse(dateString)
        }


        fun convertFirebaseTimestampToDate(firebaseTimestamp: Timestamp): Date {
            val milliseconds = firebaseTimestamp.toDate().time
            return Date(milliseconds)
        }

        fun subtractDate(date: String): String? {
            try {
                val dateAsDate =
                    LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
                val oneDayBefore = dateAsDate.minusDays(1)
                return oneDayBefore.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
            } catch (e: Exception) {
                return null // Handle parsing errors if necessary
            }
        }

        fun subtractMonth(inputMonthString: String): String {
            val inputMonth = Month.valueOf(inputMonthString.toUpperCase()) // Convert the input string to Month enum
            val previousMonth = if (inputMonth == Month.JANUARY) Month.DECEMBER else inputMonth.minus(1) // Handle January as a special case
            return previousMonth.getDisplayName(TextStyle.FULL, Locale.getDefault())
        }

        fun addMonth(inputMonthString: String): String {
            val inputMonth = Month.valueOf(inputMonthString.toUpperCase()) // Convert the input string to Month enum
            val previousMonth = if (inputMonth == Month.JANUARY) Month.DECEMBER else inputMonth.plus(1) // Handle January as a special case
            return previousMonth.getDisplayName(TextStyle.FULL, Locale.getDefault())
        }

        fun addDate(date: String): String? {
            try {
                val dateAsDate =
                    LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
                val oneDayBefore = dateAsDate.plusDays(1)
                return oneDayBefore.format(DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.US))
            } catch (e: Exception) {
                return null // Handle parsing errors if necessary
            }
        }

        fun formatDate(date: LocalDate): String {
            val formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.getDefault())
            return date.format(formatter)
        }

        fun getMonthFromDate(dateString: String): String {

            val inputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            // If you want the full month name, use "MMMM". If you want the abbreviated month name, use "MMM".
            val outputFormat = SimpleDateFormat("MMM", Locale.getDefault())
            return outputFormat.format(date)
        }

        fun getShortMonthFromMonth(dateString: String): String {

            val inputFormat = SimpleDateFormat("MMMM", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            // If you want the full month name, use "MMMM". If you want the abbreviated month name, use "MMM".
            val outputFormat = SimpleDateFormat("MMM", Locale.getDefault())
            return outputFormat.format(date)
        }

        fun getMonthName(dateString: String): String {

            val inputFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)

            // If you want the full month name, use "MMMM". If you want the abbreviated month name, use "MMM".
            val outputFormat = SimpleDateFormat("MMMM", Locale.getDefault())

            return outputFormat.format(date)
        }

    }
}