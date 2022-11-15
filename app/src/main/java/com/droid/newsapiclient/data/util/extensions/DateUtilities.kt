package com.droid.newsapiclient.data.util.extensions

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

internal fun convertToDateAndTime(currentTime: Long): String {
    Timber.d("convertTimeStamp: $currentTime")

    val dateFormat = SimpleDateFormat("yyyy-MM-dd 'at' hh:mm aa", Locale.getDefault())

    return dateFormat.format(Date(currentTime))
}

fun convertTimeStamp(currentTime: Long): String {
//    2020-05-27
    Timber.d("convertTimeStamp: $currentTime")

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    return dateFormat.format(Date(currentTime))
}

fun utcTODate(time: String): String {
    // 2021-04-03T19:15:29.3266667
    val epoch: Long

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    epoch = dateFormat.parse(time)!!.time / 1000
    val simpleDateFormat = SimpleDateFormat("MMM, dd yyyy", Locale.getDefault())
    val date = Date(epoch * 1000)
    return simpleDateFormat.format(date)
}

fun utcToDateTime(time: String): String {
    // 2021-04-03T19:15:29.3266667
    val epoch: Long?
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    epoch = (dateFormat.parse(time)).time / 1000
    val simpleDateFormat = SimpleDateFormat("dd MMM, yyyy 'at' HH:mm aa", Locale.getDefault())
    val date = Date(epoch * 1000)
    return simpleDateFormat.format(date)
}

fun nowToHrsMins(): String {
    val now = System.currentTimeMillis()

    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

    return dateFormat.format(Date(now))
}

fun timePublished(time: String): Int {
    val re = Regex("[^A-Za-z0-9 ]")

    val now = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val currentTime = re.replace(dateFormat.format(Date(now)), "")[1].code + 12
    Timber.e("currentTime: $currentTime")
    val apiTime = re.replace(convertToHoursMins(time), "")[1].code + 12
    Timber.e("apiTime: $apiTime")
    val timeDifference = apiTime - currentTime
    Timber.e("timeDifference:$timeDifference")

    return timeDifference
}

fun utcToDateOnly(time: String): String {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = dateFormatter.parse(time) as Date

    val timeFormatter = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
    return timeFormatter.format(date)
}

fun convertToHoursMins(time: String): String {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = dateFormatter.parse(time)
    val timeFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return timeFormatter.format(date)
}
