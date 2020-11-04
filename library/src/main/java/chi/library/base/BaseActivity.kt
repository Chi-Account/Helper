package chi.library.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import chi.library.util.LogUtil

open class BaseActivity : AppCompatActivity() {

    protected val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(javaClass.simpleName)
    }
}