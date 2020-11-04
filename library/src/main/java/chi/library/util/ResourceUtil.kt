package chi.library.util

import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import chi.library.base.BaseApplication

fun getResourceColor(@ColorRes id: Int) =
    ContextCompat.getColor(BaseApplication.context, id)

fun getResourceString(@StringRes resId: Int) =
    BaseApplication.context.getString(resId)