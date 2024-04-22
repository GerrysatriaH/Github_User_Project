package com.gerrysatria.githubuser.util

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

fun ProgressBar.show(state: Boolean) {
    visibility = if (state) View.VISIBLE else View.GONE
}

fun TextView.show(state: Boolean) {
    visibility = if (state) View.VISIBLE else View.GONE
}

fun RecyclerView.show(state: Boolean){
    visibility = if (state) View.VISIBLE else View.GONE
}