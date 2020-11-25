package chi.helper.activity

import android.os.Bundle
import android.view.View
import chi.helper.R
import chi.library.base.BaseActivity
import chi.library.util.network.NetworkCallback
import chi.library.util.network.NetworkRequest

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClick(view: View) {
        NetworkRequest.url("https://www.baidu.com/")
            .get()
            .callback(object : NetworkCallback {

                override fun onSuccess(response: String) {
                    throw Exception("")
                }
            })
            .execute()
    }
}