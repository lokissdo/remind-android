package com.example.remind.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.remind.R
import com.example.remind.adapter.ImageAdapter
import android.graphics.Typeface
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.remind.model.Day
@BindingAdapter("formattedDate")
fun bindFormattedDate(view: TextView, date: Date?) {
    date?.let {
        val formatter = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        view.text = formatter.format(date)
    }
}

@BindingAdapter("statusImage")
fun bindStatusImage(view: ImageView, status: String?) {
    status?.let {
        val imageResource = if (status == "public") {
            R.drawable.public_status
        } else {
            R.drawable.private_status
        }
        view.setImageResource(imageResource)
    }
}


@BindingAdapter("app:dayIcon")
fun setDayIcon(imageView: ImageView, day: Day) {
    val iconRes = if (day.isChosen) R.drawable.calendar else R.drawable.calendar_notchosen
    imageView.setImageResource(iconRes)
}

@BindingAdapter("app:dayBackground")
fun setDayBackground(layout: ConstraintLayout, day: Day) {
    val backgroundRes = if (day.isChosen) R.drawable.rounded_rectangle else R.color.white
    layout.setBackgroundResource(backgroundRes)
}

@BindingAdapter("app:dayTextStyle")
fun setDayTextStyle(textView: TextView, day: Day) {
    textView.setTypeface(null, if (day.isChosen) Typeface.BOLD else Typeface.NORMAL)
    val textColor = if (day.isChosen) ContextCompat.getColor(textView.context, R.color.black) else ContextCompat.getColor(textView.context, R.color.grey)
    textView.setTextColor(textColor)
}




@BindingAdapter("imageList")
fun bindRecyclerViewWithImageList(recyclerView: RecyclerView, images: List<String>?) {
    if (images != null) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ImageAdapter(images)
    }
}
