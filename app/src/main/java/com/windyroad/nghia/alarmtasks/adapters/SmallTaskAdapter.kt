package com.windyroad.nghia.alarmtasks.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ActionMode
import android.view.ViewGroup
import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import com.windyroad.nghia.alarmtasks.R
import kotlinx.android.synthetic.main.list_item_task.view.*


class SmallTaskAdapter : RecyclerView.Adapter<SmallTaskAdapter.TaskViewHolder> {

    private lateinit var mContext: Context
    private lateinit var mListTask: ArrayList<String>
    public var listener : IListener? = null

    public var multiSelect: Boolean = false
    public val selectedItems: SparseBooleanArray = SparseBooleanArray() //List item chọn (key=position, value = boolean)
    private var mActionMode: ActionMode? = null


    constructor(context: Context, listTask: ArrayList<String>) {
        this.mContext = context
        this.mListTask = listTask
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // Khởi tạo giao diện
        var inflater = LayoutInflater.from(parent?.context)
        var itemView = inflater.inflate(R.layout.list_item_small_task, parent, false)

        return TaskViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mListTask.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        var task = mListTask[position]
        holder.bind(task)


        // Event
        holder.itemView.setOnClickListener(View.OnClickListener { view ->
            if (multiSelect) {
                toggleSelection(holder.layoutPosition) //khi chọn nhiều đổi dòng lựa chọn
                mActionMode?.title = selectedItems.size().toString()
            }
            else
                listener?.onClick(view, holder.layoutPosition) //Click bình thường, send listener
        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener { view ->
                listener?.onLongClick(view, holder.layoutPosition)
                true
        })
    }


    fun toggleSelection(position: Int) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position)
        } else {
            selectedItems.put(position, true)
        }
        notifyItemChanged(position)
    }

    fun clearSelections() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun initMultiSelect(actionMode: ActionMode?) {
        multiSelect = true
        mActionMode = actionMode
    }


    inner class TaskViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: String) {
            itemView.textView_Task.text = task
            itemView.container.isActivated = selectedItems[adapterPosition, false] //Set color selected
        }

    }

    interface IListener {
        fun onClick(view: View?, position: Int)
        fun onLongClick(view: View?, position: Int)
    }

}
