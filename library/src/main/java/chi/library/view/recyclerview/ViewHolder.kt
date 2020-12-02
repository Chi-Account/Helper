package chi.library.view.recyclerview

import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val sparseArray = SparseArray<View>()

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(@IdRes id: Int): T {
        var view: View? = sparseArray.get(id)
        return if (view == null) {
            view = itemView.findViewById<T>(id)
            sparseArray.put(id, view)
            view
        } else {
            view as T
        }
    }
}