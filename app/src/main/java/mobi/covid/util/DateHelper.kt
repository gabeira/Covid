package mobi.covid.util

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {

    fun getLastDateFormatted(date: String): String {
        val sdf = SimpleDateFormat(LONG_FORMAT, Locale.getDefault())
        val output = SimpleDateFormat.getDateTimeInstance()
        return output.format(sdf.parse(date) ?: "")
    }

    fun getYesterdayDate(): String {
        val formatter = SimpleDateFormat(DAY_FORMAT, Locale.getDefault())
        val calendarInstance = Calendar.getInstance()
        calendarInstance.add(Calendar.DATE, -1)
        return formatter.format(calendarInstance.time)
    }

    companion object {
        private const val DAY_FORMAT = "MM-dd-yyyy"
        private const val LONG_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }
}