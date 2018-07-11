package com.windyroad.nghia.alarmtasks.views


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast

import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.adapters.HistoryAdapter
import com.windyroad.nghia.alarmtasks.data.HistoryData
import com.windyroad.nghia.alarmtasks.helpers.AppConfig
import com.windyroad.nghia.alarmtasks.models.History
import com.windyroad.nghia.common.file.CSVFileUtil
import android.support.v4.content.FileProvider
import android.support.v7.widget.helper.ItemTouchHelper
import com.windyroad.nghia.alarmtasks.BuildConfig
import com.windyroad.nghia.alarmtasks.helpers.SwipeHelper
import com.windyroad.nghia.alarmtasks.helpers.TimeHelper
import com.windyroad.nghia.alarmtasks.models.HistoryExport
import com.windyroad.nghia.common.IntentUtil
import com.windyroad.nghia.common.fragment.YesNoDialogFragment
import java.io.File
import java.util.*


class ListHistoryFragment : Fragment() {

    private lateinit var mRecyclerHistory: RecyclerView
    private lateinit var mListHistory: ArrayList<History>
    private lateinit var mHistoryAdapter: HistoryAdapter


    companion object {
        fun newInstance() : ListHistoryFragment{
            return ListHistoryFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater!!.inflate(R.layout.fragment_list_history, container, false)

        mRecyclerHistory = rootView.findViewById(R.id.recyclerView_History)
        initHistory()

        setEvent()

        return rootView
    }

    private fun initHistory() {
        mListHistory = HistoryData.getAll(activity!!)

        mRecyclerHistory.hasFixedSize()
        mRecyclerHistory.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mHistoryAdapter = HistoryAdapter(mListHistory)
        mRecyclerHistory.adapter = mHistoryAdapter
    }

    private fun refreshHistory() {
        mListHistory = HistoryData.getAll(activity!!)
        mHistoryAdapter.setListHistory(mListHistory)
        mHistoryAdapter.notifyDataSetChanged()
    }

    private fun setEvent() {
        mHistoryAdapter.longClickListener = object : HistoryAdapter.IListener {
            override fun onClick(view: View?, position: Int, isLongClick: Boolean) {
                // Delete History
                var historyId = mListHistory[position].id
                var yesDialog = YesNoDialogFragment.newInstance("Delete", "Do you want delete it?", "Yes", "No")
                yesDialog.setListener(object : YesNoDialogFragment.IDialogListener {
                    override fun onDialogPositiveClick(dialog: DialogFragment) {
                        HistoryData.delete(context!!, historyId)
                        refreshHistory()
                    }

                    override fun onDialogNegativeClick(dialog: DialogFragment) {

                    }
                })
                yesDialog.show(fragmentManager, "tag")
            }
        }

        // Swipe to delete
        val itemTouchHelper = SwipeHelper(0, ItemTouchHelper.LEFT, object : SwipeHelper.RecyclerItemTouchHelperListener{
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
                HistoryData.delete(context!!, mListHistory[position].id)
                refreshHistory()
            }
        }, R.id.view_foreground)
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerHistory)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.list_history, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_export_history -> {
                exportHistory()
            }
            R.id.action_clear_history -> {
                HistoryData.clearAll(activity!!)
                initHistory()
            }
        }
        return true
    }

    private fun exportHistory() {
        var listHistoryExport = ArrayList<HistoryExport>()
        var listHistory = HistoryData.getAll(context!!)
        listHistory.forEach { history ->
            val createAt = history.createAt
            var time = TimeHelper.makeTime(createAt?.get(Calendar.HOUR_OF_DAY)!!, createAt?.get(Calendar.MINUTE)!!)
            val date = TimeHelper.makeDate(createAt?.get(Calendar.YEAR), createAt?.get(Calendar.MONTH), createAt?.get(Calendar.DAY_OF_MONTH))
            listHistoryExport.add(HistoryExport(history.taskName, time, date))
        }

        var file = CSVFileUtil<HistoryExport>()
        val filePath = AppConfig.makeExportHistoryFilePath(activity!!)

        var result = file.exportToFile(filePath, listHistoryExport)
        if (result) {
            val message = getString(R.string.message_export_history) + filePath
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
            // Open File
            val uri = FileProvider.getUriForFile(activity!!, BuildConfig.APPLICATION_ID + ".fileprovider", File(filePath))
            activity?.startActivity(IntentUtil.createOpenFileIntent(activity, uri))
        } else {
            Toast.makeText(activity, getString(R.string.message_fail), Toast.LENGTH_LONG).show()
        }
    }

}

