package foxphehe.com.meme

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by foxphehe on 2018/1/29.
 */
fun Date.toHourInt(): Int {
    val format = SimpleDateFormat("HH", Locale.SIMPLIFIED_CHINESE)
    val hours = format.format(this)
    return Integer.parseInt(hours)
}

fun Date.toMinInt(): Long {
    val format = SimpleDateFormat("mm", Locale.SIMPLIFIED_CHINESE)
    val hours = format.format(this)
    return hours.toLong()
}


// 获取当天0点时间戳 Created by Wenhui Huang 2019/02/28
fun Date.dayTimeInMillis(): Long {
    val calendar = Calendar.getInstance()// 获取当前日期
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    var time = calendar.timeInMillis
    time += this.toHourInt() * 60 * 60 * 1000
    time += this.toMinInt() * 60 * 1000
    return this.time - time
}

fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setGone() {
    visibility = View.GONE
}

