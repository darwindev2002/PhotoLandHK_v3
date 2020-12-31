package com.darwin.photolandhk.posts

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
fun wpAPIDateString2Date(dateStr: String, now: Long = System.currentTimeMillis()): String {
    //"2020-11-05T12:21:00"

    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateStr).time
    val diff = now - date
    val sec = TimeUnit.MILLISECONDS.toSeconds(diff)
    if (sec < 60) return "${sec}秒 前發佈"
    val min = TimeUnit.MILLISECONDS.toMinutes(diff)
    if (min < 60) return "${min}分鐘 前發佈"
    val hour = TimeUnit.MILLISECONDS.toHours(diff)
    if (hour < 24) return "${hour}小時 前發佈"
    val days = TimeUnit.MILLISECONDS.toDays(diff)
    if (days < 7) return "${days}天 前發佈"
    if (days / 7 < 4) return "${Math.ceil(days * 1.0 / 7).toInt()}週 前發佈"

    return SimpleDateFormat("dd/MM/yyyy").format(date)
}