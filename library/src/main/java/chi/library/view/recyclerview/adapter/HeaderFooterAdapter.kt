package chi.library.view.recyclerview.adapter

import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.core.util.contains
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import chi.library.view.recyclerview.ITEM_VIEW_TYPE_FOOTER
import chi.library.view.recyclerview.ITEM_VIEW_TYPE_HEADER
import chi.library.view.recyclerview.ViewHolder

class HeaderFooterAdapter<T>(
    private val adapter: RecyclerViewAdapter<T>
) : RecyclerView.Adapter<ViewHolder>() {

    private val headers = SparseArray<View>()

    private val footers = SparseArray<View>()

    fun getHeaderCount(): Int = headers.size()

    fun getFooterCount(): Int = footers.size()

    private fun isHeader(position: Int): Boolean =
        position < getHeaderCount()

    private fun isFooter(position: Int): Boolean =
        position >= getHeaderCount() + adapter.itemCount

    private fun getPositionInRawAdapter(position: Int) =
        when {
            isHeader(position) || isFooter(position) -> -1
            else -> position - getHeaderCount()
        }

    override fun getItemCount(): Int = getHeaderCount() + getFooterCount() + adapter.itemCount

    fun getItemInRawAdapter(position: Int): T? {
        val positionInRawAdapter = getPositionInRawAdapter(position)
        return when {
            positionInRawAdapter < 0 -> null
            else -> adapter.getItem(positionInRawAdapter)
        }
    }

    override fun getItemViewType(position: Int): Int =
        when {
            isHeader(position) -> headers.keyAt(position)
            isFooter(position) -> footers.keyAt(position - getHeaderCount() - adapter.itemCount)
            else -> adapter.getItemViewType(getPositionInRawAdapter(position))
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        when {
            headers.contains(viewType) -> ViewHolder(headers.get(viewType))
            footers.contains(viewType) -> ViewHolder(footers.get(viewType))
            else -> adapter.onCreateViewHolder(parent, viewType)
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isHeader(position) && !isFooter(position)) {
            adapter.onBindViewHolder(holder, getPositionInRawAdapter(position))
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = when {
                    isHeader(position) || isFooter(position) -> layoutManager.spanCount
                    else -> 1
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        val position = holder.adapterPosition
        if (position != RecyclerView.NO_POSITION) {
            val layoutParams = holder.itemView.layoutParams
            if (
                (isHeader(position) || isFooter(position))
                && layoutParams != null
                && layoutParams is StaggeredGridLayoutManager.LayoutParams
            ) {
                layoutParams.isFullSpan = true
            }
        }
    }

    fun addHeader(view: View) {
        headers.put(ITEM_VIEW_TYPE_HEADER + getHeaderCount(), view)
        notifyItemInserted(getHeaderCount() - 1)
    }

    fun addFooter(view: View) {
        footers.put(ITEM_VIEW_TYPE_FOOTER + getFooterCount(), view)
        notifyItemInserted(getHeaderCount() + adapter.itemCount + getFooterCount() - 1)
    }
}