package com.novatec.epitak.ui.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.novatec.epitak.R
import com.novatec.epitak.ui.addcar.AddCarActivity
import kotlinx.android.synthetic.main.dialog_add_car_first.*

class DialogAddCarFirst : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_car_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnOk.setOnClickListener {
            startActivity(Intent(requireActivity(), AddCarActivity::class.java))
            dismiss()
        }
        btnCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun getTheme() = R.style.Theme_Dialog


}