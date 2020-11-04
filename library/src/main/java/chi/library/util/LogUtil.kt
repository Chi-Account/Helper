package chi.library.util

import android.util.Log

object LogUtil {

    private var tag = "LogUtil"

    private var isShowLog = false

    fun init(isShowLog: Boolean) {
        LogUtil.isShowLog = isShowLog
    }

    fun init(isShowLog: Boolean, tag: String) {
        init(isShowLog)
        LogUtil.tag = tag
    }

    fun v(tag: String, msg: String) {
        if (isShowLog) {
            Log.v(tag, msg)
        }
    }

    fun v(msg: String) {
        v(tag, msg)
    }

    fun d(tag: String, msg: String) {
        if (isShowLog) {
            Log.d(tag, msg)
        }
    }

    fun d(msg: String) {
        d(tag, msg)
    }

    fun i(tag: String, msg: String) {
        if (isShowLog) {
            Log.i(tag, msg)
        }
    }

    fun i(msg: String) {
        i(tag, msg)
    }

    fun w(tag: String, msg: String) {
        if (isShowLog) {
            Log.w(tag, msg)
        }
    }

    fun w(msg: String) {
        w(tag, msg)
    }

    fun e(tag: String, msg: String) {
        if (isShowLog) {
            Log.e(tag, msg)
        }
    }

    fun e(msg: String) {
        e(tag, msg)
    }
}