package com.badcompany.pitak.util

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.badcompany.pitak.R

class HorizontalNumberPicker(context: Context?, attrs: AttributeSet?) :
    LinearLayout(context, attrs) {
    private val et_number: TextView?
    var min = 1
    var max = 8

    private var onCountChangeListener: ((count: Int) -> Unit)? = null

    fun addOnSeatCountChangeListener(onCountChange: (count: Int) -> Unit) {
        onCountChangeListener = onCountChange
    }


    fun setText(text: String) {
        et_number?.text = text
    }

    fun resetText() {
        et_number?.text = min.toString()
    }

    /***
     * HANDLERS
     */
    private inner class AddHandler(val diff: Int) : OnClickListener {
        override fun onClick(v: View) {
            var newValue = value + diff
            if (newValue < min) {
                newValue = min
            } else if (newValue > max) {
                newValue = max
            } else {
                if (onCountChangeListener != null) onCountChangeListener!!(newValue)
            }
            et_number!!.text = newValue.toString()
        }
    }

    /***
     * GETTERS & SETTERS
     */
    var value: Int
        get() {
            if (et_number != null) {
                try {
                    val value = et_number.text.toString()
                    return value.toInt()
                } catch (ex: NumberFormatException) {
                    Log.e("HorizontalNumberPicker", ex.toString())
                }
            }
            return 0
        }
        set(value) {
            if (et_number != null) {
                et_number.text = value.toString()
            }
        }

    init {
        inflate(context, R.layout.number_picker_horizontal, this)
        et_number = findViewById(R.id.et_number)
        val btn_less = findViewById<TextView>(R.id.btn_less)
        btn_less.setOnClickListener(AddHandler(-1))
        val btn_more = findViewById<TextView>(R.id.btn_more)
        btn_more.setOnClickListener(AddHandler(1))
    }
}