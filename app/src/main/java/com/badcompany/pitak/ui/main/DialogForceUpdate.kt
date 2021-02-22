package com.badcompany.pitak.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.badcompany.pitak.R

class DialogForceUpdate : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_force_update, container, false)
    }


    override fun getTheme() = R.style.Theme_Dialog

}
