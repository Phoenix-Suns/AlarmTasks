package com.windyroad.nghia.common.view

import android.content.Context
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Lock Dragging BottomSheet
 * using:
 * 1. app:layout_behavior="com.windyroad.nghia.common.view.LockBottomSheetBehavior"
 * 2. behavior = LockBottomSheetBehavior.from<RelativeLayout>(bottomSheetView)
 * 3. findViewById(R.id.videoView).setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    behavior.setAllowUserDragging(false);
                    break;
                case MotionEvent.ACTION_UP:
                    behavior.setAllowUserDragging(true);
                    break;
            }
            return true;
        }
    });

 * https://stackoverflow.com/questions/52443138/restrict-bottom-sheet-swipe-down
 */
class LockBottomSheetBehavior<V : View> : BottomSheetBehavior<V> {
    var allowDragging = true

    constructor() : super()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)


    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: V, event: MotionEvent): Boolean {
        return if (!allowDragging) {
            false
        } else super.onInterceptTouchEvent(parent, child, event)
    }


    companion object {

        fun <V : View> from(view: V): LockBottomSheetBehavior<V> {
            val params = view.layoutParams
            if (params !is CoordinatorLayout.LayoutParams) {
                throw IllegalArgumentException("The view is not a child of CoordinatorLayout")
            } else {
                val behavior = params.behavior
                return behavior as? LockBottomSheetBehavior<V>
                        ?: throw IllegalArgumentException("The view is not associated with BottomSheetBehavior")
            }
        }
    }
}