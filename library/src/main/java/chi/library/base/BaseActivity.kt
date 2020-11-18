package chi.library.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chi.library.util.LogUtil

open class BaseActivity : AppCompatActivity() {

    protected val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(javaClass.simpleName)
    }

    protected fun getContext(): Context = this

    protected fun startActivity(activityClass: Class<out Activity>) {
        startActivity(Intent(this, activityClass))
    }
}