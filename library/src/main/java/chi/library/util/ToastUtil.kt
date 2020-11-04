package chi.library.util

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import chi.library.base.BaseApplication

private var toast: Toast? = null

private val handler = Handler(Looper.getMainLooper())

private fun showToastActually(text: CharSequence) {
    toast?.cancel()
    Toast.makeText(BaseApplication.context, text, Toast.LENGTH_SHORT).let {
        it.show()
        toast = it
    }
}

fun showToast(text: CharSequence) {
    if (isMainThread()) {
        showToastActually(text)
    } else {
        handler.post { showToastActually(text) }
    }
}

fun showToast(@StringRes resId: Int) {
    val text = BaseApplication.context.getString(resId)
    if (isMainThread()) {
        showToastActually(text)
    } else {
        handler.post { showToastActually(text) }
    }
}