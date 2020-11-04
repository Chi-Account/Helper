package chi.library.extension

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Process

inline fun <reified T> Context.startActivity() =
    startActivity(Intent(this, T::class.java))

inline fun <reified T> Context.startActivity(block: Intent.() -> Unit) =
    startActivity(Intent(this, T::class.java).apply(block))

fun Context.startActivityWhenAvailable(intent: Intent) {
    val activities = packageManager?.queryIntentActivities(
        intent,
        PackageManager.MATCH_DEFAULT_ONLY
    )
    if (activities?.isNotEmpty() == true) {
        startActivity(intent)
    }
}

fun Context.startDialActivity(phoneNumber: String) =
    startActivityWhenAvailable(
        Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:$phoneNumber")
        )
    )

fun Context.startViewActivity(uriString: String) =
    startActivityWhenAvailable(
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uriString)
        )
    )

fun Context.getProcessName(pid: Int = Process.myPid()): String? {
    var processName: String? = null
    try {
        val activityManager =
            applicationContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.runningAppProcesses?.let {
            for (runningAppProcessInfo in it) {
                if (pid == runningAppProcessInfo.pid) {
                    processName = runningAppProcessInfo.processName
                    break
                }
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return processName
}