package chi.library.view.recyclerview.listener

import android.view.View

interface OnItemLongClickListener {
    fun onItemLongClick(view: View, position: Int): Boolean
}