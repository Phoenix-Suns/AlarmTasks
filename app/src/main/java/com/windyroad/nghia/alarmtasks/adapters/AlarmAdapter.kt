package com.windyroad.nghia.alarmtasks.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.windyroad.nghia.alarmtasks.R
import com.windyroad.nghia.alarmtasks.helpers.AlarmHelper
import com.windyroad.nghia.alarmtasks.helpers.ThemeHelper
import com.windyroad.nghia.alarmtasks.helpers.TimeHelper
import com.windyroad.nghia.alarmtasks.models.MyAlarm
import kotlinx.android.synthetic.main.list_item_alarm.view.*

/**
 * Created by Nghia on 3/6/2018.
 */
class AlarmAdapter : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    lateinit var mContext: Context
    public var itemListener : IListener? = null
    private var mListItem: ArrayList<MyAlarm> = ArrayList<MyAlarm>()

    constructor(context : Context, listItem : ArrayList<MyAlarm>) {
        mContext = context
        mListItem = listItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        // Khởi tạo giao diện
        var inflater = LayoutInflater.from(parent?.context)
        var itemView = inflater.inflate(R.layout.list_item_alarm, parent, false)

        return AlarmViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        // Hiện giá trị lên Views
        var item : MyAlarm = mListItem[position]
        holder?.bind(item, itemListener)
    }

    override fun getItemCount(): Int {
        return mListItem.size
    }

    inner class AlarmViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        var mListener : IListener? = null

        init {
            itemView?.setOnClickListener(this)
            itemView?.setOnLongClickListener(this)
        }

        override fun onClick(view: View?) {
            mListener?.onClick(view, adapterPosition)
        }

        override fun onLongClick(view: View?): Boolean {
            mListener?.onLongClick(view, adapterPosition)
            return true
        }

        fun bind(item: MyAlarm, itemListener: IListener?){
            // Init View
            itemView.textView_Task.text = item.name
            itemView.textView_Time.text = TimeHelper.makeTime(item.timeHour, item.timeMinute)
            itemView.switch_Enable.isChecked = item.isEnabled

            itemView.toggle_RepeatMonday.isChecked = item.getRepeatingDay(MyAlarm.MONDAY)
            itemView.toggle_RepeatTuesday.isChecked = item.getRepeatingDay(MyAlarm.TUESDAY)
            itemView.toggle_RepeatWednesday.isChecked = item.getRepeatingDay(MyAlarm.WEDNESDAY)
            itemView.toggle_RepeatThursday.isChecked = item.getRepeatingDay(MyAlarm.THURSDAY)
            itemView.toggle_RepeatFriday.isChecked = item.getRepeatingDay(MyAlarm.FRDIAY)
            itemView.toggle_RepeatSaturday.isChecked = item.getRepeatingDay(MyAlarm.SATURDAY)
            itemView.toggle_RepeatSunday.isChecked = item.getRepeatingDay(MyAlarm.SUNDAY)

            // Day Period Background
            itemView.imageView_Period.setImageResource(ThemeHelper.getImageDrawable(item.timeHour))
            itemView.imageView_Period.setBackgroundResource(ThemeHelper.getBackgroundColor(item.timeHour))
            //Glide.with(itemView.imageView_Period.context).load("https://static.wamiz.fr/images/news/facebook/article/sans-titre-69-fb-5a3b86ea88b62.jpg").into(itemView.imageView_Period)

            updateRowUI(item)


            mListener = itemListener

            // Set Event
            itemView.switch_Enable.setOnCheckedChangeListener { compoundButton, checked ->
                mListener?.onAlarmEnable(compoundButton, adapterPosition, item.id, checked)

                val alarm = mListItem[adapterPosition]
                alarm.isEnabled = checked
                updateRowUI(alarm)
            }
        }

        private fun updateRowUI(item: MyAlarm) {
            if (item.isEnabled) {
                itemView.textView_Time.setTextColor(ContextCompat.getColor(mContext, R.color.common_textPrimary_light))

                // Cal Time Distance
                var sbDistance = AlarmHelper.calDistanceTime(mContext, item)
                itemView.textView_Distance.text = sbDistance.toString()

            } else {
                itemView.textView_Time.setTextColor(ContextCompat.getColor(mContext, R.color.common_textHint_light))
                itemView.textView_Distance.text = ""
            }
        }

    }

    interface IListener {
        fun onClick(view: View?, position: Int)
        fun onLongClick(view: View?, position: Int)
        fun onAlarmEnable(view: View?, position: Int, alarmId: Long, checked: Boolean)
    }
}

