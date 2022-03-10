package chi.library.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes

private var toast: Toast? = null

private val handler = Handler(Looper.getMainLooper())

@SuppressLint("ShowToast")
private fun Context.showToastActually(text: CharSequence) {
    toast?.cancel()
    Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).let {
        it.show()
        toast = it
    }
}

fun Context.showToast(text: CharSequence) {
    if (isMainThread()) {
        showToastActually(text)
    } else {
        handler.post { showToastActually(text) }
    }
}

fun Context.showToast(@StringRes resId: Int) {
    val text = applicationContext.getString(resId)
    if (isMainThread()) {
        showToastActually(text)
    } else {
        handler.post { showToastActually(text) }
    }
}