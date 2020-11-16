package chi.helper.activity

import android.os.Bundle
import android.view.View
import chi.helper.R
import chi.library.base.BaseActivity
import chi.library.util.LogUtil
import chi.library.util.dp2px
import chi.library.util.px2dp
import chi.library.util.showDisplayInfo

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClick(view: View) {
        testDeviceUtil()
    }

    private fun testDeviceUtil() {
        showDisplayInfo()
        val value = 100
        val px = dp2px(value.toFloat())
        val dp = px2dp(px.toFloat())
        LogUtil.i("value: $value")
        LogUtil.i("px: $px")
        LogUtil.i("dp: $dp")
    }
}
