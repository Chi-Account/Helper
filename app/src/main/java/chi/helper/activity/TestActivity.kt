package chi.helper.activity

import android.os.Bundle
import android.view.View
import chi.helper.R
import chi.library.base.BaseActivity
import chi.library.util.LogUtil
import chi.library.util.getVersionCode
import chi.library.util.getVersionName

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClick(view: View) {
        testPackageUtil()
    }

    private fun testPackageUtil() {
        LogUtil.i("Version code: ${getVersionCode()}")
        LogUtil.i("Version name: ${getVersionName()}")
    }
}