package com.novatec.epitak.ui.driver_post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.novatec.epitak.R
import com.novatec.epitak.ui.interfaces.IOnPassengerDelete
import com.novatec.epitak.ui.interfaces.IOnSignOut
import kotlinx.android.synthetic.main.dialog_delete_passenger.*


const val ARG_PASSENGER_ID = "PASSENGER_ID"

class DialogDeletePassenger : DialogFragment() {

    private var passengerId = -1L
    private lateinit var listener: IOnPassengerDelete

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (requireActivity() is IOnPassengerDelete) {
            listener = requireActivity() as IOnPassengerDelete
            passengerId = requireArguments().getLong(ARG_PASSENGER_ID, -1)
        } else throw ClassCastException("$context must implement IOnPassengerDelete")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.dialog_delete_passenger, container, false)

    override fun getTheme() = R.style.Theme_Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnYes.setOnClickListener {
            listener.onPassengerDelete(passengerId)
            dismiss()
        }

        btnNo.setOnClickListener {
            dismiss()
        }

    }
}