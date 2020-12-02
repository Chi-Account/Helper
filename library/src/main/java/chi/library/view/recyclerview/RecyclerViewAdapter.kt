package chi.library.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter<E>(
    private val dataList: List<E>,
    private val onItemClickListener: OnItemClickListener? = null,
    private val onItemLongClickListener: OnItemLongClickListener? = null
) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int = dataList.size

    open fun getItem(position: Int): E = dataList[position]

    @LayoutRes
    abstract fun getLayoutResource(itemViewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                getLayoutResource(viewType),
                parent,
                false
            )
        )
        onItemClickListener?.run {
            viewHolder.itemView.setOnClickListener {
                val adapterPosition = viewHolder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(it, adapterPosition)
                }
            }
        }
        onItemLongClickListener?.run {
            viewHolder.itemView.setOnLongClickListener {
                val adapterPosition = viewHolder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    onItemLongClick(it, adapterPosition)
                } else {
                    false
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        onBindViewHolder(holder, position, item)
    }

    abstract fun onBindViewHolder(holder: ViewHolder, position: Int, item: E)
}