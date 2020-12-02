package chi.helper.activity

import android.os.Bundle
import android.view.View
import chi.helper.R
import chi.library.base.BaseActivity

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClick(view: View) {
    }
}