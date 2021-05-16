package com.novatec.epitak.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.novatec.epitak.R
import kotlinx.android.synthetic.main.dialog_force_update.*

class DialogForceUpdate : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_force_update, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnOk.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,
                                 Uri.parse("https://play.google.com/store/apps/details?id=com.axonlogic.uzrailway")))
            dismiss()
            (context as AppCompatActivity).finish()
        }

    }

    override fun getTheme() = R.style.Theme_Dialog

}
