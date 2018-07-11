package com.windyroad.nghia.alarmtasks.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*

import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.adapters.AlarmAdapter
import com.windyroad.nghia.alarmtasks.data.HistoryData
import com.windyroad.nghia.alarmtasks.data.MyAlarmData
import com.windyroad.nghia.alarmtasks.helpers.AlarmHelper
import com.windyroad.nghia.alarmtasks.helpers.SwipeHelper
import com.windyroad.nghia.alarmtasks.models.MyAlarm
import com.windyroad.nghia.common.fragment.YesNoDialogFragment

class ListAlarmFragment : Fragment() {

    companion object {
        fun newInstance(): ListAlarmFragment {
            return ListAlarmFragment()
        }
    }

    var REQUEST_CODE_EDIT_ALARM = 1

    private var mButton_AddAlarm : FloatingActionButton? = null
    private var mRecyclerView_ListAlarm : RecyclerView? = null
    private lateinit var mListAlarm: ArrayList<MyAlarm>
    private lateinit var mAlarmAdapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var rootView = inflater!!.inflate(R.layout.fragment_list_alarm, container, false)

        findViews(rootView)
        initVars()
        setEvents()

        return rootView
    }

    private fun findViews(rootView: View) {
        mButton_AddAlarm = rootView.findViewById(R.id.fab_AddAlarm)
        mRecyclerView_ListAlarm = rootView.findViewById<RecyclerView>(R.id.recyclerView_ListAlarm)
    }

    private fun initVars() {
        // Init RecyclerView
        mListAlarm = MyAlarmData.getAll(activity!!)
        mAlarmAdapter = AlarmAdapter(activity!!, mListAlarm)
        mRecyclerView_ListAlarm?.hasFixedSize()
        mRecyclerView_ListAlarm?.layoutManager = LinearLayoutManager(activity)
        mRecyclerView_ListAlarm?.adapter = mAlarmAdapter
    }

    private fun setEvents() {

        mAlarmAdapter.itemListener = object : AlarmAdapter.IListener {

            override fun onClick(view: View?, position: Int) {
                // Edit MyAlarm
                var alarmId = mListAlarm[position].id
                var i = Intent(activity, EditAlarmActivity::class.java)
                i.putExtra(EditAlarmActivity.EXTRA_ALARM_ID, alarmId)
                startActivityForResult(i, REQUEST_CODE_EDIT_ALARM)
            }
            override fun onLongClick(view: View?, position: Int) {
                // Delete MyAlarm
                var alarmId = mListAlarm[position].id
                var yesDialog = YesNoDialogFragment.newInstance("Delete", "Do you want delete it?", "Yes", "No")
                yesDialog.setListener(object : YesNoDialogFragment.IDialogListener {
                    override fun onDialogPositiveClick(dialog: DialogFragment) {
                        handleDeleteAlarm(alarmId)
                    }

                    override fun onDialogNegativeClick(dialog: DialogFragment) {

                    }
                })
                yesDialog.show(fragmentManager, "tag")
            }

            override fun onAlarmEnable(view: View?, position: Int, alarmId: Long, checked: Boolean) {
                AlarmHelper.cancelAlarm(context!!, alarmId)

                var row = MyAlarmData.setEnable(context!!, alarmId, checked)
                if (row > 0) {
                    // Success
                    AlarmHelper.setAllAlarm(context!!)
                }
            }
        }

        mButton_AddAlarm?.setOnClickListener{view ->
            gotoAddAlarm()
        }

        // Swipe to delete
        val itemTouchHelper = SwipeHelper(0, ItemTouchHelper.LEFT, object : SwipeHelper.RecyclerItemTouchHelperListener{
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
                handleDeleteAlarm(mListAlarm[position].id)
            }
        }, R.id.view_foreground)
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mRecyclerView_ListAlarm)
    }

    private fun handleDeleteAlarm(alarmId: Long) {
        val alarm = MyAlarmData.getById(context!!, alarmId)
        MyAlarmData.delete(context!!, alarmId)
        AlarmHelper.cancelAlarm(context!!, alarm.id)
        AlarmHelper.setAllAlarm(context!!)
        refreshListAlarm()
    }

    private fun refreshListAlarm() {
        mListAlarm.clear()
        mListAlarm.addAll(MyAlarmData.getAll(activity!!))
        mAlarmAdapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.list_alarm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_add_alarm -> {
                gotoAddAlarm()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun gotoAddAlarm() {
        var i = Intent(activity, EditAlarmActivity::class.java)
        startActivityForResult(i, REQUEST_CODE_EDIT_ALARM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_EDIT_ALARM){
            if (resultCode == Activity.RESULT_OK) {
                // Update List
                refreshListAlarm()
            }
        }
    }

}
