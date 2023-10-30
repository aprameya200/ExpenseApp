package com.example.expenseapp.helpers

import com.google.firebase.Timestamp
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class ConvertDate {

    companion object{
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
    }
}