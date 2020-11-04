package chi.helper.activity

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import chi.helper.R
import chi.library.base.BaseActivity
import chi.library.extension.getProcessName
import chi.library.extension.startActivity
import chi.library.util.*
import chi.library.util.network.FormDataPart
import chi.library.util.network.MEDIA_TYPE_PNG
import chi.library.util.network.NetworkCallback
import chi.library.util.network.NetworkRequest
import java.io.File
import kotlin.concurrent.thread

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClick(view: View) {
        startActivity<TestActivity2>()
    }

    private fun testToastUtil() {
//        showToast(R.string.toast_text)
        /*thread {
            showToast(R.string.toast_text)
        }*/
        showToast("Toast in main thread.")
        thread {
            showToast("Toast in sub thread.")
        }
    }

    private fun testThreadUtil() {
        LogUtil.i(TAG, "outer isMainThread: ${isMainThread()}")
        thread {
            LogUtil.i(TAG, "inner isMainThread: ${isMainThread()}")
        }
    }

    private fun testSharedPreferencesUtil() {
        SharedPreferencesUtil.putString("String", "张智琦")
        LogUtil.i(TAG, "String: ${SharedPreferencesUtil.getString("String", "")}")
        SharedPreferencesUtil.putStringSet("StringSet", setOf("张", "智", "琦"))
        SharedPreferencesUtil.getStringSet("StringSet", setOf())?.let {
            LogUtil.i(TAG, "StringSet size: ${it.size}")
            for (item in it) {
                LogUtil.i(TAG, "StringSet item: $item")
            }
        }
        SharedPreferencesUtil.putInt("Int", 7)
        LogUtil.i(TAG, "Int: ${SharedPreferencesUtil.getInt("Int", 0)}")
        SharedPreferencesUtil.putLong("Long", 77)
        LogUtil.i(TAG, "Long: ${SharedPreferencesUtil.getLong("Long", 0)}")
        SharedPreferencesUtil.putFloat("Float", 7.7F)
        LogUtil.i(TAG, "Float: ${SharedPreferencesUtil.getFloat("Float", 0F)}")
        SharedPreferencesUtil.putBoolean("Boolean", true)
        LogUtil.i(TAG, "Boolean: ${SharedPreferencesUtil.getBoolean("Boolean", false)}")
        SharedPreferencesUtil.getAll().run {
            LogUtil.i(TAG, "All size: $size")
            for (key in keys) {
                LogUtil.i(TAG, "All key: $key")
            }
        }
        LogUtil.i(TAG, "contains String: ${SharedPreferencesUtil.contains("String")}")
        LogUtil.i(TAG, "remove String: ${SharedPreferencesUtil.remove("String")}")
        LogUtil.i(TAG, "contains String: ${SharedPreferencesUtil.contains("String")}")
        LogUtil.i(TAG, "clear: ${SharedPreferencesUtil.clear()}")
        SharedPreferencesUtil.getAll().run {
            LogUtil.i(TAG, "All size: $size")
            for (key in keys) {
                LogUtil.i(TAG, "All key: $key")
            }
        }
    }

    private fun testResourceUtil() {
        LogUtil.i(TAG, "String: ${getResourceString(R.string.app_name)}")
        window?.setBackgroundDrawable(ColorDrawable(getResourceColor(R.color.colorPrimary)))
    }

    private fun testLogUtil() {
        LogUtil.init(true, "Log")
        LogUtil.v("verbose")
        LogUtil.v(TAG, "verbose")
        LogUtil.d("debug")
        LogUtil.d(TAG, "debug")
        LogUtil.i("info")
        LogUtil.i(TAG, "info")
        LogUtil.w("warn")
        LogUtil.w(TAG, "warn")
        LogUtil.e("error")
        LogUtil.e(TAG, "error")
    }

    private fun testFileUtil() {
//        LogUtil.i(TAG, "${getDownloadsDirectory()}")
//        LogUtil.i(TAG, "${getDownloadsPath()}")
//        LogUtil.i(TAG, "${deleteFile(File(getDownloadsDirectory(), "directory"))}")
        LogUtil.i(TAG, "${clearDirectory(File(getDownloadsDirectory(), "directory"))}")
    }

    private fun testContext() {
//        startActivity<TestActivity>()
        /*startActivity<TestActivity> {
            putExtra("key", "value ${System.currentTimeMillis()}")
        }*/
//        startDialActivity("18336852645")
//        startViewActivity("https://www.baidu.com/")
        LogUtil.i(TAG, getProcessName() ?: "get process name failure")
    }

    private fun testNetworkUtil() {
        NetworkRequest("https://api.imgur.com/3/image")
            .header("Authorization", "Client-ID 9199fdef135c122")
            .postMultipartBody(
                listOf(
                    FormDataPart(
                        "image",
                        "logo-square.png",
                        File(getExternalFilesDir(null), "logo-square.png"),
                        MEDIA_TYPE_PNG
                    )
                )
            )
            .callback(object : NetworkCallback {

                override fun onLoading(visible: Boolean) {
                    LogUtil.i("onLoading: $visible")
                }

                override fun onSuccess(response: String) {
                    LogUtil.i("onSuccess: $response")
                }

                override fun onFailure(exception: Exception) {
                    Toast.makeText(this@TestActivity, "${exception.message}", Toast.LENGTH_SHORT)
                        .show()
                    LogUtil.i("onFailure: ${exception.message}")
                }
            })
            .execute()
    }
}
