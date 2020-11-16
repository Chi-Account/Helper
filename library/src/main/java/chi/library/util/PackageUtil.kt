package chi.library.util

import chi.library.base.BaseApplication

private val context = BaseApplication.context

fun getVersionCode(): Long =
    try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            packageInfo.longVersionCode
        } else {
            packageInfo.versionCode.toLong()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }

fun getVersionName(): String =
    try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName
    } catch (e: Exception) {
        e.printStackTrace()
        ""
    }