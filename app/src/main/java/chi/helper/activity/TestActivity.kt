package chi.helper.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import chi.helper.R
import chi.helper.adapter.RecyclerViewAdapter
import chi.helper.bean.Item
import chi.library.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun onClick(view: View) {
        val dataList = List(100) {
            Item(it.toString())
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerViewAdapter(dataList)
    }
}