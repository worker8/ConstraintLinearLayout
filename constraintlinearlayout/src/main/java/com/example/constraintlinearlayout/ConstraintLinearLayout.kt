package com.example.constraintlinearlayout

import android.content.Context
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class ConstraintLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    override fun addView(newView: View?) {
        newView?.also { _newView ->
            super.addView(newView)
            _newView.id = View.generateViewId()
            val set = ConstraintSet()
            set.clone(this@ConstraintLinearLayout)
            if (childCount == 1) {
                set.connect(_newView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else {
                val lastChild = getChildAt(childCount - 2)
                set.connect(_newView.id, ConstraintSet.TOP, lastChild.id, ConstraintSet.BOTTOM)
            }
            set.connect(_newView.id, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
            set.connect(_newView.id, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)

            set.applyTo(this@ConstraintLinearLayout)
            TransitionManager.beginDelayedTransition(this@ConstraintLinearLayout)
        }
    }

    override fun removeView(toBeRemovedView: View?) {
        toBeRemovedView?.also { _toBeRemovedView ->
            val set = ConstraintSet()
            set.clone(this@ConstraintLinearLayout)
            val tobeRemovedIndex = indexOfChild(_toBeRemovedView)
            set.clear(_toBeRemovedView.id, ConstraintSet.TOP)
            set.clear(_toBeRemovedView.id, ConstraintSet.BOTTOM)

            if (tobeRemovedIndex == 0 && childCount > 1) { // first item
                val nextChild = getChildAt(1)
                set.clear(nextChild.id, ConstraintSet.TOP)
                set.connect(nextChild.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
            } else if (tobeRemovedIndex > 0 && tobeRemovedIndex < childCount - 1) { // in between
                val previousChild = getChildAt(tobeRemovedIndex - 1)
                val nextChild = getChildAt(tobeRemovedIndex + 1)
                set.clear(nextChild.id, ConstraintSet.TOP)
                set.connect(nextChild.id, ConstraintSet.TOP, previousChild.id, ConstraintSet.BOTTOM)
            }
            super.removeView(_toBeRemovedView)
            set.applyTo(this@ConstraintLinearLayout)
            TransitionManager.beginDelayedTransition(this@ConstraintLinearLayout)
        }
    }
}