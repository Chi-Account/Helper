package chi.library.util

import android.os.Looper

fun isMainThread() =
    Looper.getMainLooper() == Looper.myLooper()