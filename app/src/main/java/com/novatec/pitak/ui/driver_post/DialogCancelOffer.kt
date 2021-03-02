package com.novatec.pitak.ui.driver_post

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.novatec.pitak.R
import com.novatec.pitak.viewobjects.OfferViewObj
import kotlinx.android.synthetic.main.dialog_cancel_offer.*


class DialogCancelOffer : DialogFragment() {


    private lateinit var offer: OfferViewObj

    override fun onAttach(context: Context) {
        super.onAttach(context)
        offer = requireArguments().getParcelable(ARG_OFFER)!!
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_cancel_offer, container)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnNo.setOnClickListener {
            dismiss()
        }

        btnYes.setOnClickListener {
            (requireActivity() as DriverPostActivity).cancelOffer(OfferViewObj.offerToDTO(offer))
            dismiss()
        }

    }


    override fun getTheme() = R.style.Theme_Dialog

}