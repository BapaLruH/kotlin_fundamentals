package ru.movie.app.ui.views

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dp: Int): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), this.resources.displayMetrics)