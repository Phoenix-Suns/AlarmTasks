package com.windyroad.nghia.alarmtasks.views


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.support.v7.widget.RecyclerView

import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.adapters.TaskAdapter
import com.windyroad.nghia.alarmtasks.data.TaskData
import com.windyroad.nghia.common.fragment.EditTextDialogFragment
import com.windyroad.nghia.common.sqlite.DatabaseUtil


/**
 * A simple [Fragment] subclass.
 */
class ListTaskFragment : Fragment() {

    var mIsAdd = true
    lateinit var mListTask : ArrayList<String>
    lateinit var mTasksAdapter : TaskAdapter
    lateinit var mRecyclerTasks : RecyclerView
    internal var mActionMode: ActionMode? = null


    companion object {
        fun newInstance() : ListTaskFragment {
            return ListTaskFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var rootView = inflater!!.inflate(R.layout.fragment_list_task, container, false)

        mRecyclerTasks = rootView.findViewById(R.id.recyclerView_ListTask)
        initRecyclerTask()

        setEvents()

        return rootView
    }


    private fun setEvents() {


    }

    private fun showEditDialog(position: Int, isAdd: Boolean) {
        var dialog: EditTextDialogFragment

        if (isAdd) {

            dialog = EditTextDialogFragment.newInstance(getString(R.string.title_add_task), "")
            dialog.setListener { value -> editTextReturn(value, 0) }

        } else {

            // Edit
            dialog = EditTextDialogFragment.newInstance(getString(R.string.title_edit_task), mListTask[position])
            dialog.setListener { value -> editTextReturn(value, position) }
        }

        dialog.show(activity?.fragmentManager, "tag")
        mIsAdd = isAdd
    }

    private fun deleteTasks() {
        // Xóa, tìm list item còn lại để save
        val len = mTasksAdapter.selectedItems.size()
        val checked = mTasksAdapter.selectedItems
        for (i in 0..len)
            if (checked.get(i)) {
                mListTask.removeAt(i)

            }

        TaskData.update(activity!!, mListTask)
        initRecyclerTask()
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.list_task, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_add_task -> {
                // Show Add
                showEditDialog(0, true)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun editTextReturn(value: String, posision: Int) {
        var result = DatabaseUtil.QUERY_FAIL

        if (mIsAdd) {
            // Add new Task
             result = TaskData.add(activity!!, value)
        } else {
            // Update Task
            mListTask[posision] = value
            result = TaskData.update(activity!!, mListTask)
        }

        // Edit False
        if (result == DatabaseUtil.QUERY_FAIL) return

        // Refresh List
        initRecyclerTask()
    }

    private fun initRecyclerTask() {
        mRecyclerTasks.hasFixedSize()
        mRecyclerTasks.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mListTask = TaskData.getAll(activity!!)
        mTasksAdapter = TaskAdapter(activity!!, mListTask)
        mRecyclerTasks.adapter = mTasksAdapter


        // Event
        mTasksAdapter.listener = object : TaskAdapter.IListener {

            override fun onClick(view: View?, position: Int) {
                showEditDialog(position, false)
            }

            override fun onLongClick(view: View?, position: Int): Boolean? {

                //Đang hiện Menu thì ko thực hiện
                if (mActionMode != null) {
                    return true
                }

                // Bắt đầu CAB bằng cách dùng ActionMode.Callback
                mActionMode = activity?.startActionMode(mActionModeCallback)

                // Chuyển sang chọn Multiple
                mTasksAdapter.initMultiSelect(mActionMode)
                mTasksAdapter.toggleSelection(position)
                return true
            }
        }
    }

    // Button Click => hiện menu
    private val mActionModeCallback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Gọi khi tạo ActionMode, startActionMode() gọi

            //Gọi MenuResource thông qua Inflater
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.edit_task, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Gọi khi ActionMode shown, sau onCreateActionMode
            // Có thể gọi nhiều lần nếu Mode = Invalidated
            return false // Return false if nothing is done
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            // Gọi khi chọn Menu Item
            when (item.itemId) {
                R.id.action_delete -> {
                    deleteTasks()
                    mode.finish() //close CAB
                    return true
                }
                else -> return false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            // Gọi khi Thoát ActionMode
            mActionMode = null
            mTasksAdapter.multiSelect = false
            mTasksAdapter.clearSelections()
        }
    }
}

