package com.novatec.epitak.ui.driver_post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.novatec.epitak.R
import com.novatec.epitak.ui.interfaces.IOnParcelDelete
import kotlinx.android.synthetic.main.dialog_delete_passenger.*


const val ARG_PARCEL_ID = "PARCEL_ID"

class DialogDeleteParcel : DialogFragment() {

    private var parcelId = -1L
    private lateinit var listener: IOnParcelDelete

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (requireActivity() is IOnParcelDelete) {
            listener = requireActivity() as IOnParcelDelete
            parcelId = requireArguments().getLong(ARG_PARCEL_ID, -1)
        } else throw ClassCastException("$context must implement IOnParcelDelete")
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.dialog_delete_parcel, container, false)

    override fun getTheme() = R.style.Theme_Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnYes.setOnClickListener {
            listener.onParcelDelete(parcelId)
            dismiss()
        }

        btnNo.setOnClickListener {
            dismiss()
        }

    }
}