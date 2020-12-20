package com.badcompany.pitak.ui.main.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.interfaces.IOnSignOut
import kotlinx.android.synthetic.main.dialog_sign_out.*

class DialogSignOut : DialogFragment() {

    private lateinit var listener: IOnSignOut

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (targetFragment is IOnSignOut) listener = targetFragment as IOnSignOut
        else throw ClassCastException("$context must implement IOnSignOut")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.dialog_sign_out, container, false)

    override fun getTheme() = R.style.Theme_Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnYes.setOnClickListener {
            listener.onSignOut()
            dismiss()
        }

        btnNo.setOnClickListener {
            dismiss()
        }

    }
}
