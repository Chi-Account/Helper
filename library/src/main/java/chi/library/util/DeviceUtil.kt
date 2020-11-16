package chi.library.util

import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import chi.library.base.BaseApplication

private const val TAG = "DeviceUtil"

private val displayMetrics = BaseApplication.context.resources.displayMetrics

fun dp2px(dpValue: Float) = (dpValue * displayMetrics.density + 0.5).toInt()

fun px2dp(pxValue: Float) = (pxValue / displayMetrics.density + 0.5).toInt()

fun showDisplayInfo() {
    val windowManager = BaseApplication.context.getSystemService(Context.WINDOW_SERVICE)
            as WindowManager
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    LogUtil.i(TAG, "像素密度：${displayMetrics.density}")
    LogUtil.i(TAG, "宽（px）：${size.x}")
    LogUtil.i(TAG, "高（px）：${size.y}")
    LogUtil.i(TAG, "宽（dp）：${px2dp(size.x.toFloat())}")
    LogUtil.i(TAG, "高（dp）：${px2dp(size.y.toFloat())}")
}