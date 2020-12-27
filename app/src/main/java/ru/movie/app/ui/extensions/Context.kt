package ru.movie.app.ui.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPxFloat(dp: Int): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics)
fun Context.dpToPxInt(dp: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics).toInt()