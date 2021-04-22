package com.novatec.pitak.ui.addpost

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.novatec.core.Constants
import com.novatec.domain.domainmodel.*
import com.novatec.pitak.R
import com.novatec.pitak.ui.BaseActivity
import com.novatec.pitak.ui.addpost.destinations.DestinationsFragmentDirections
import com.novatec.pitak.viewobjects.DriverPostViewObj
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi


class AddPostActivity : BaseActivity() {

    private val viewmodel: AddPostViewModel by viewModels()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_post)
        setupActionBar()
        subscribeObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        checkIfEditing(intent.getParcelableExtra(Constants.TXT_DRIVER_POST))

    }

    private fun checkIfEditing(driverPostViewObj: DriverPostViewObj?) {
        if (driverPostViewObj != null) {
            viewmodel.id = driverPostViewObj.id
            viewmodel.isEditing = true
            viewmodel.price = driverPostViewObj.price
            viewmodel.seat = driverPostViewObj.seat
            viewmodel.placeFrom = Place(driverPostViewObj.from.districtId,
                                        driverPostViewObj.from.regionId,
                                        driverPostViewObj.from.lat,
                                        driverPostViewObj.from.lon,
                                        driverPostViewObj.from.regionName,
                                        driverPostViewObj.from.name)

            viewmodel.placeTo = Place(driverPostViewObj.to.districtId,
                                      driverPostViewObj.to.regionId,
                                      driverPostViewObj.to.lat,
                                      driverPostViewObj.to.lon,
                                      driverPostViewObj.from.regionName,
                                      driverPostViewObj.from.name)



            viewmodel.timeFirstPart = driverPostViewObj.timeFirstPart
            viewmodel.timeSecondPart = driverPostViewObj.timeSecondPart
            viewmodel.timeThirdPart = driverPostViewObj.timeThirdPart
            viewmodel.timeFourthPart = driverPostViewObj.timeFourthPart
            viewmodel.departureDate = driverPostViewObj.departureDate
            viewmodel.note = driverPostViewObj.remark
            val carImageList = arrayListOf<Image>()
            driverPostViewObj.car?.imageList?.forEach {
                carImageList.add(Image(it.id, it.link))
            }

            viewmodel.car = CarDetails(driverPostViewObj.car?.id,
                                       IdName(driverPostViewObj.car!!.carModel!!.id,
                                              driverPostViewObj.car.carModel!!.name),
                                       Image(driverPostViewObj.car.image?.id,
                                             driverPostViewObj.car.image?.link),
                                       driverPostViewObj.car.fuelType,
                                       CarColorBody(driverPostViewObj.car.carColor?.id,
                                                    driverPostViewObj.car.carColor?.hex,
                                                    driverPostViewObj.car.carColor?.name),
                                       driverPostViewObj.car.carNumber,
                                       driverPostViewObj.car.carYear,
                                       driverPostViewObj.car.airConditioner,
                                       driverPostViewObj.car.def,
                                       carImageList
            )

            nav_host_fragment.findNavController()
                .navigate(DestinationsFragmentDirections.actionDestinationsFragmentToPreviewFragmentClearBackstack())

            title = getString(R.string.edit_post)
        }

    }


    private fun setupListeners() {
        tvClose.setOnClickListener {
            finish()
        }
        ivBack.setOnClickListener {
            if (!findNavController(R.id.nav_host_fragment).popBackStack()) {
                finish()
            }
        }

    }

    private fun subscribeObservers() {


    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar)
    }


}
