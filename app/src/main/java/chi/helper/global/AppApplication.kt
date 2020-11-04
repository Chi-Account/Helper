package chi.helper.global

import chi.library.base.BaseApplication
import chi.library.util.LogUtil

class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        LogUtil.init(true)
    }
}