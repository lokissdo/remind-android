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
import android.net.Uri
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.remind.adapter.ImageUploadAdapter
import com.example.remind.model.Day
import com.example.remind.model.TodoItem
import com.google.firebase.Timestamp

@BindingAdapter("formattedDate")
fun bindFormattedDate(view: TextView, date: Date?) {
    date?.let {
        val formatter = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
        view.text = formatter.format(date)
    }
}

@BindingAdapter("statusImage")
fun bindStatusImage(view: ImageView, status: Boolean) {
    val imageResource = if (status) {
        R.drawable.public_status // Replace with your actual drawable resource for public status
    } else {
        R.drawable.private_status // Replace with your actual drawable resource for private status
    }
    view.setImageResource(imageResource)
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

@BindingAdapter("todoStatusImage")
fun bindTodoStatusImage(view: ImageView, status: String?) {
    status?.let {
        val imageResource = when (status) {
            "Planned" -> R.drawable.blue_event_note
            "Completed" -> R.drawable.check_circle
            else -> {
                R.drawable.blue_event_note
            }
        }
        view.setImageResource(imageResource)
    }
}

@BindingAdapter("categoryImage")
fun bindCategoryImage(view: ImageView, category: String) {
    category?.let {
        val imageResource = when (category) {
            "FOOD" -> R.drawable.food
            "HEALTH" -> R.drawable.running
            else -> {
                R.drawable.food
            }
        }
        view.setImageResource(imageResource)
    }
}

@BindingAdapter("todoStatusBackground")
fun setTodoStatusBackground(layout: ConstraintLayout,  status: String) {
    val backgroundRes = if (status == "Planned") R.color.light_blue else R.color.light_green
    layout.setBackgroundResource(backgroundRes)
}

@BindingAdapter("app:todoStatusTextColor")
fun setTodoStatusTextStyle(textView: TextView, status: String) {
    val textColor =  if (status == "Planned") ContextCompat.getColor(textView.context,R.color.bold_blue )  else ContextCompat.getColor(textView.context,R.color.green )
    textView.setTextColor(textColor)
}

@BindingAdapter("app:renderTodoTime")
fun setTodoTime(textView: TextView, todoItem: TodoItem) {

    val time = "${todoItem.startTime} - ${todoItem.endTime}"
    textView.text = time
}

@BindingAdapter("formattedDate")
fun bindFormattedDate(view: TextView, timestamp: Timestamp?) {
    timestamp?.let {
        val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val formattedDate = sdf.format(it.toDate())
        view.text = formattedDate
    }
}

@BindingAdapter("imageList")
fun bindImageList(recyclerView: RecyclerView, imageList: List<Uri>?) {
    val adapter = recyclerView.adapter as? ImageUploadAdapter
    adapter?.submitList(imageList)
}

