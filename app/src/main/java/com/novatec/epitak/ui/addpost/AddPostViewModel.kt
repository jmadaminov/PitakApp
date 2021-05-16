package com.novatec.epitak.ui.addpost

import javax.inject.Inject
import com.novatec.domain.domainmodel.CarDetails
import com.novatec.domain.domainmodel.Place
import com.novatec.epitak.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * Created by jahon on 28-Apr-20
 */
@HiltViewModel
class AddPostViewModel @Inject constructor() :
    BaseViewModel() {

    val isPackage: Boolean = false
    var isEditing: Boolean = false
    var id: Long? = null
    var placeFrom: Place? = null
    var placeTo: Place? = null
    var departureDate: String? = null
    var timeFirstPart = false
    var timeSecondPart = false
    var timeThirdPart = false
    var timeFourthPart = false
    var car: CarDetails? = null
    var price: Int? = null
    var seat: Int? = null
    var note: String? = null


}