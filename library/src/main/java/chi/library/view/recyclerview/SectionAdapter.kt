package chi.library.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class SectionAdapter<E, T>(
    private val dataList: List<E>,
    private val onItemClickListener: OnItemClickListener? = null,
    private val onItemLongClickListener: OnItemLongClickListener? = null
) : RecyclerView.Adapter<ViewHolder>() {

    private val sectionMap = LinkedHashMap<Int, T>()

    init {
        refreshSectionMap()
    }

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            refreshSectionMap()
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        registerAdapterDataObserver(adapterDataObserver)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        unregisterAdapterDataObserver(adapterDataObserver)
    }

    private fun refreshSectionMap() {
        sectionMap.clear()
        var sectionCount = 0
        val count = dataList.size
        for (i in 0 until count) {
            val sectionHeader = getSectionHeader(i, getItem(i))
            if (!sectionMap.containsValue(sectionHeader)) {
                sectionMap[i + sectionCount] = sectionHeader
                sectionCount++
            }
        }
    }

    override fun getItemCount(): Int = dataList.size + sectionMap.size

    private fun getRawPosition(position: Int): Int =
        if (sectionMap.containsKey(position)) {
            -1
        } else {
            var sectionCount = 0
            for (entry in sectionMap.entries) {
                if (entry.key < position) {
                    sectionCount++
                } else {
                    break
                }
            }
            position - sectionCount
        }

    abstract fun getSectionHeader(rawPosition: Int, item: E): T

    private fun getSectionHeader(position: Int): T? = sectionMap[position]

    open fun getItem(rawPosition: Int): E = dataList[rawPosition]

    override fun getItemViewType(position: Int): Int =
        if (sectionMap.containsKey(position)) {
            ITEM_VIEW_TYPE_SECTION_HEADER
        } else {
            val rawPosition = getRawPosition(position)
            getItemViewType(rawPosition, getItem(rawPosition))
        }

    open fun getItemViewType(rawPosition: Int, item: E): Int = ITEM_VIEW_TYPE_SECTION_ITEM

    @LayoutRes
    private fun getLayoutResource(itemViewType: Int): Int =
        if (ITEM_VIEW_TYPE_SECTION_HEADER == itemViewType) {
            getSectionHeaderLayoutResource()
        } else {
            getItemLayoutResource(itemViewType)
        }

    @LayoutRes
    abstract fun getSectionHeaderLayoutResource(): Int

    @LayoutRes
    abstract fun getItemLayoutResource(itemViewType: Int): Int

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
                if (adapterPosition != RecyclerView.NO_POSITION
                    && ITEM_VIEW_TYPE_SECTION_HEADER != getItemViewType(adapterPosition)
                ) {
                    onItemClick(it, getRawPosition(adapterPosition))
                }
            }
        }
        onItemLongClickListener?.run {
            viewHolder.itemView.setOnLongClickListener {
                val adapterPosition = viewHolder.adapterPosition
                if (adapterPosition != RecyclerView.NO_POSITION
                    && ITEM_VIEW_TYPE_SECTION_HEADER != getItemViewType(adapterPosition)
                ) {
                    onItemLongClick(it, getRawPosition(adapterPosition))
                } else {
                    false
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (ITEM_VIEW_TYPE_SECTION_HEADER == getItemViewType(position)) {
            sectionMap[position]?.let {
                onBindSectionHeaderViewHolder(holder, it)
            }
        } else {
            val rawPosition = getRawPosition(position)
            onBindItemViewHolder(holder, rawPosition, getItem(rawPosition))
        }
    }

    abstract fun onBindSectionHeaderViewHolder(holder: ViewHolder, item: T)

    abstract fun onBindItemViewHolder(holder: ViewHolder, position: Int, item: E)
}