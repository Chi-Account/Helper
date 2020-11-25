package chi.library.view.recyclerview

import android.view.View

interface OnItemLongClickListener {
    fun onItemLongClick(view: View, position: Int): Boolean
}