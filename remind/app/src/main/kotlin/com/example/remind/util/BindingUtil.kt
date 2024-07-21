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



@BindingAdapter("imageList")
fun bindRecyclerViewWithImageList(recyclerView: RecyclerView, images: List<String>?) {
    if (images != null) {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ImageAdapter(images)
    }
}
