package chi.library.base.pageturner

import android.app.Activity

data class Page(
    val text: String,
    val activityClass: Class<out Activity>
)