package chi.library.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import chi.library.util.LogUtil

open class BaseFragment : Fragment() {

    protected val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(javaClass.simpleName)
    }
}