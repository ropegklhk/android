package com.koding.web.utils

import android.app.Dialog
import android.content.Context
import com.koding.web.R
import java.text.SimpleDateFormat
import java.util.*

private lateinit var dialog: Dialog

fun showLoading(context: Context) {
    dialog = Dialog(context)
    dialog.setContentView(R.layout.layout_loading)
    dialog.setCancelable(false)
    dialog.show()
}

fun closeLoading() {
    dialog.dismiss()
}

fun String.withDateFormat(): String {
    val expectFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault()).parse(this) as Date
    return expectFormat.format(oldFormat)
}