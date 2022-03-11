package chi.library.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

fun Context.getResourceColor(@ColorRes id: Int) =
    ContextCompat.getColor(applicationContext, id)

fun Context.getResourceString(@StringRes resId: Int) =
    applicationContext.getString(resId)