package com.badcompany.pitak.ui.addpost

//import com.badcompany.pitak.di.viewmodels.AddPostViewModelFactory
//import com.badcompany.pitak.fragments.AddPostNavHostFragment
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.badcompany.core.Constants
import com.badcompany.domain.domainmodel.*
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.viewobjects.DriverPostViewObj
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
//        inject()
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_post)
        setupActionBar()
//        onRestoreInstanceState()

        subscribeObservers()
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        checkIfEditing(intent.getParcelableExtra(Constants.TXT_DRIVER_POST))

    }

    private fun checkIfEditing(driverPostViewObj: DriverPostViewObj?) {
        if (driverPostViewObj != null) {
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

            viewmodel.car = CarDetails(driverPostViewObj.carId,
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

            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.previewFragment, true)
                .build()

            nav_host_fragment.findNavController()
                .navigate(R.id.action_destinationsFragment_to_previewFragment,
                          null,
                          navOptions)

            title = getString(R.string.edit_post)
        }

    }


    private fun setupListeners() {


    }

    private fun subscribeObservers() {


    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
