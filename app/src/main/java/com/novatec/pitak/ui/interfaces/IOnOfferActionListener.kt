package com.novatec.pitak.ui.interfaces

import android.view.View
import com.novatec.remote.model.OfferDTO

interface IOnOfferActionListener {
    fun onCancelClick(offer: OfferDTO)
    fun onAcceptClick(offer: OfferDTO)
    fun onPhoneCallClick(offer: OfferDTO)
}