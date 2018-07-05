package com.windyroad.nghia.alarmtasks.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.models.History
import kotlinx.android.synthetic.main.list_item_history.view.*
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryVH> {

    private lateinit var mListItem: List<History>
    public var longClickListener : IListener? = null
    public var clickListener : IListener? = null


    constructor(listItem: List<History>){
        mListItem = listItem;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryVH {
        val inflater = LayoutInflater.from(parent?.context)
        val itemView = inflater.inflate(R.layout.list_item_history, parent, false)

        return HistoryVH(itemView)
    }

    override fun onBindViewHolder(holder: HistoryVH, position: Int) {
        // Hiện giá trị lên Views
        var item = mListItem[position]
        holder?.bind(item)
        holder?.itemClickListener = clickListener
        holder?.itemLongClickListener = longClickListener
    }


    override fun getItemCount(): Int {
        return mListItem.size
    }


    class HistoryVH(itemView: View?) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener, View.OnLongClickListener {

        var itemClickListener : IListener? = null
        var itemLongClickListener : IListener? = null

        init {
            itemView?.setOnClickListener(this)
            itemView?.setOnLongClickListener(this)
        }

        fun bind(item : History){
            itemView.textView_Task.text = item.taskName
            val time = item.createAt
            itemView.textView_Time.text =
                    String.format("%02d:%02d", time?.get(Calendar.HOUR_OF_DAY), time?.get(Calendar.MINUTE))
            itemView.textView_Date.text =
                    String.format("%04d-%02d-%02d", time?.get(Calendar.YEAR), time?.get(Calendar.MONTH), time?.get(Calendar.DAY_OF_MONTH))
        }

        override fun onClick(v: View?) {
            itemClickListener?.onClick(v, adapterPosition, false)
        }

        override fun onLongClick(v: View?): Boolean {
            itemLongClickListener?.onClick(v, adapterPosition, true)
            return true
        }
    }

    interface IListener {
        fun onClick(view: View?, position: Int, isLongClick: Boolean)
    }
}