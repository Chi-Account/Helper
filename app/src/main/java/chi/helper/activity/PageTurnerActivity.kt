package chi.helper.activity

import chi.library.base.pageturner.BasePageTurnerActivity
import chi.library.base.pageturner.Page

class PageTurnerActivity : BasePageTurnerActivity() {

    override fun getPageList(): List<Page> = listOf(
        Page("TestActivity", TestActivity::class.java),
        Page("PageTurnerActivity", PageTurnerActivity::class.java)
    )
}