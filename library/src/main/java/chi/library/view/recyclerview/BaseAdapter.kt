package chi.library.view.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<E, T : RecyclerView.ViewHolder>(
    private val dataList: List<E>,
    private val onItemClickListener: OnItemClickListener? = null,
    private val onItemLongClickListener: OnItemLongClickListener? = null
) : RecyclerView.Adapter<T>() {

    override fun getItemCount(): Int = dataList.size

    fun getItem(position: Int): E = dataList[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        val viewHolder = onCreateViewHolder(parent)
        if (onItemClickListener != null) {
            viewHolder.itemView.setOnClickListener {
                val adapterPosition = viewHolder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClickListener.onItemClick(it, adapterPosition)
                }
            }
        }
        if (onItemLongClickListener != null) {
            viewHolder.itemView.setOnLongClickListener {
                val adapterPosition = viewHolder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemLongClickListener.onItemLongClick(it, adapterPosition)
                } else {
                    false
                }
            }
        }
        return viewHolder
    }

    abstract fun onCreateViewHolder(parent: ViewGroup): T
}