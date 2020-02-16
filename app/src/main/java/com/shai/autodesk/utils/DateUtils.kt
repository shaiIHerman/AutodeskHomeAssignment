package com.shai.autodesk.utils

import android.annotation.SuppressLint
import android.text.format.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object {
        @SuppressLint("SimpleDateFormat")
        @Throws(ParseException::class)
        fun convertToNewFormat(dateStr: String?): String? {
            val utc: TimeZone = TimeZone.getTimeZone("UTC")
            val sourceFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            sourceFormat.timeZone = utc
            val convertedDate: Date? = sourceFormat.parse(dateStr!!)
            val dayOfTheWeek = DateFormat.format("EEEE", convertedDate) as String
            val day = DateFormat.format("dd", convertedDate) as String
            val monthString = DateFormat.format("MMM", convertedDate) as String
            val year = DateFormat.format("yyyy", convertedDate) as String
            val hour = DateFormat.format("HH", convertedDate) as String
            val min = DateFormat.format("mm", convertedDate) as String
            return "$dayOfTheWeek, $day $monthString $year $hour:$min"
        }
    }
}