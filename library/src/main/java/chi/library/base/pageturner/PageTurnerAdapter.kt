package chi.library.base.pageturner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class PageTurnerAdapter(
    private val dataList: List<Page>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<PageTurnerAdapter.ViewHolder>() {

    override fun getItemCount(): Int = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            android.R.layout.simple_list_item_1,
            parent,
            false
        )
    ).apply {
        itemView.setOnClickListener { onItemClickListener.onItemClick(adapterPosition) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = getItem(position).text
    }

    fun getItem(position: Int): Page = dataList[position]

    internal class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(android.R.id.text1)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}