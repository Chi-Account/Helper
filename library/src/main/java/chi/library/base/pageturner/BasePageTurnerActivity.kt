package chi.library.base.pageturner

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import chi.library.R
import chi.library.base.BaseActivity
import kotlinx.android.synthetic.main.activity_base_page_turner_activity.*

abstract class BasePageTurnerActivity : BaseActivity(), PageTurnerAdapter.OnItemClickListener {

    private lateinit var adapter: PageTurnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_page_turner_activity)
        adapter = PageTurnerAdapter(getPageList(), this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(position: Int) {
        val item = adapter.getItem(position)
        startActivity(item.activityClass)
    }

    protected abstract fun getPageList(): List<Page>
}