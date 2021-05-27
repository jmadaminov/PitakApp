package com.novatec.epitak.ui.addpost

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.CalendarView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.android.material.snackbar.Snackbar
import com.novatec.core.*
import com.novatec.domain.domainmodel.*
import com.novatec.epitak.R
import com.novatec.epitak.ui.BaseActivity
import com.novatec.epitak.ui.addcar.MyItemClickListener
import com.novatec.epitak.ui.bsd_destination.ARG_IS_FROM
import com.novatec.epitak.ui.bsd_destination.DestinationBSD
import com.novatec.epitak.ui.bsd_destination.REQ_DESTINATION
import com.novatec.epitak.ui.bsd_destination.RESULT_PLACE
import com.novatec.epitak.ui.viewgroups.CarItemSelectionView
import com.novatec.epitak.ui.viewgroups.LoadingItem
import com.novatec.epitak.util.DateUtils
import com.novatec.epitak.util.hideKeyboard
import com.novatec.epitak.viewobjects.DriverPostViewObj
import com.novatec.epitak.viewobjects.PlaceViewObj
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi
import java.util.*


class AddPostActivity : BaseActivity() {

    private val carListAdr = GroupAdapter<GroupieViewHolder>()
    private lateinit var balloon: Balloon
    private val viewModel: AddPostViewModel by viewModels()

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_post)
        setup()
        subscribeObservers()
        attachListeners()
        viewModel.getCars()

    }

    override fun onResume() {
        super.onResume()
        checkIfEditing(intent.getParcelableExtra(Constants.TXT_DRIVER_POST))

    }

    private fun checkIfEditing(driverPostViewObj: DriverPostViewObj?) {
        if (driverPostViewObj != null) {
            viewModel.id = driverPostViewObj.id
            viewModel.isEditing = true
            viewModel.price = driverPostViewObj.price
            viewModel.seat = driverPostViewObj.seat
            viewModel.placeFrom = Place(driverPostViewObj.from.districtId,
                                        driverPostViewObj.from.regionId,
                                        driverPostViewObj.from.lat,
                                        driverPostViewObj.from.lon,
                                        driverPostViewObj.from.regionName,
                                        driverPostViewObj.from.name)

            viewModel.placeTo = Place(driverPostViewObj.to.districtId,
                                      driverPostViewObj.to.regionId,
                                      driverPostViewObj.to.lat,
                                      driverPostViewObj.to.lon,
                                      driverPostViewObj.from.regionName,
                                      driverPostViewObj.from.name)



            viewModel.timeFirstPart = driverPostViewObj.timeFirstPart
            viewModel.timeSecondPart = driverPostViewObj.timeSecondPart
            viewModel.timeThirdPart = driverPostViewObj.timeThirdPart
            viewModel.timeFourthPart = driverPostViewObj.timeFourthPart
            viewModel.departureDate = driverPostViewObj.departureDate
            viewModel.isPackage = driverPostViewObj.pkg ?: false

            val carImageList = arrayListOf<Image>()
            driverPostViewObj.car?.imageList?.forEach {
                carImageList.add(Image(it.id, it.link))
            }

            viewModel.car = CarDetails(driverPostViewObj.car?.id,
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

            noteInput.setText(driverPostViewObj.remark)
            priceInput.setText(driverPostViewObj.price)
            tvFrom.text = driverPostViewObj.from.name
            tvTo.text = driverPostViewObj.to.name
            tvDate.text = driverPostViewObj.departureDate

            title = getString(R.string.edit_post)
        }

    }


    private fun attachListeners() {

        priceInput.doOnTextChanged { text, start, before, count ->
            text?.let {
                viewModel.price = text.toString().toInt()
            }
            checkFields()
        }

        priceInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                priceInputLayout.clearFocus()
                priceInput.clearFocus()
                priceInput.hideKeyboard()
            }
            true
        }

        checkFirstPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkSecondPartDay.isChecked && !checkThirdPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkFirstPartDay.isChecked = true
            }
            viewModel.timeFirstPart = checkFirstPartDay.isChecked
        }
        checkSecondPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkThirdPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkSecondPartDay.isChecked = true
            }
            viewModel.timeSecondPart = checkSecondPartDay.isChecked
        }
        checkThirdPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkSecondPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkThirdPartDay.isChecked = true
            }
            viewModel.timeThirdPart = checkThirdPartDay.isChecked
        }
        checkFourthPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkSecondPartDay.isChecked && !checkThirdPartDay.isChecked) {
                checkFourthPartDay.isChecked = true
            }
            viewModel.timeFourthPart = checkFourthPartDay.isChecked
        }


        tvDate.setOnClickListener {
            balloon.show(tvDate)
        }

        tvFrom.setOnClickListener {
            DestinationBSD().also {
                it.arguments = Bundle().apply { putBoolean(ARG_IS_FROM, true) }
                this.supportFragmentManager.setFragmentResultListener(REQ_DESTINATION,
                                                                      this) { key, bundle ->
                    viewModel.placeFrom =
                        bundle.getParcelable<PlaceViewObj>(RESULT_PLACE)!!.toPlace()
                    tvFrom.text = viewModel.placeFrom!!.name
                    checkFields()
                }
            }.show(supportFragmentManager, "")
        }

        tvTo.setOnClickListener {
            DestinationBSD().also {
                it.arguments = Bundle().apply { putBoolean(ARG_IS_FROM, false) }
                this.supportFragmentManager.setFragmentResultListener(REQ_DESTINATION,
                                                                      this) { key, bundle ->
                    viewModel.placeTo =
                        bundle.getParcelable<PlaceViewObj>(RESULT_PLACE)!!.toPlace()
                    tvTo.text = viewModel.placeTo!!.name
                    checkFields()
                }
            }.show(supportFragmentManager, "")
        }

        postCreate.setOnClickListener {
            viewModel.createDriverPost(DriverPost(viewModel.id ?: 0,
                                                  viewModel.placeFrom!!,
                                                  viewModel.placeTo!!,
                                                  viewModel.price!!,
                                                  viewModel.departureDate,
                                                  null,
                                                  viewModel.timeFirstPart,
                                                  viewModel.timeSecondPart,
                                                  viewModel.timeThirdPart,
                                                  viewModel.timeFourthPart,
                                                  viewModel.car!!.id!!,
                                                  null,
                                                  if (noteInput.text.isNullOrBlank()) null else noteInput.text.toString(),
                                                  viewModel.seat,
                                                  0,
                                                  null,
                                                  viewModel.seat,
                                                  EPostStatus.CREATED,
                                                  viewModel.isPackage,
                                                  0,
                                                  null,
                                                  null,
                                                  EPostType.DRIVER_SM))

        }

    }

    private fun subscribeObservers() {

        viewModel.carsResponse.observe(this) {
            val response = it ?: return@observe

            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(scrollView,
                                  response.message ?: getString(R.string.system_error),
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ErrorWrapper.SystemError -> {

                    Snackbar.make(scrollView,
                                  response.err.localizedMessage!!,
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    populateCarsList(response.value)
                }
                ResultWrapper.InProgress -> {
                    carListAdr.clear()
                    carListAdr.add(LoadingItem())
                }
            }.exhaustive

        }


        viewModel.createResponse.observe(this) {
            val response = it ?: return@observe
            when (response) {
                is ErrorWrapper.RespError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(scrollView, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    postCreate.revertAnimation()
                    Snackbar.make(scrollView,
                                  response.err.localizedMessage!!,
                                  Snackbar.LENGTH_SHORT).show()
                }
                is ResultWrapper.Success -> {
                    postCreate.stopAnimation()
                    setResult(RESULT_OK)
                    finish()
                }
                ResultWrapper.InProgress -> {
                    postCreate.startAnimation()
                }
            }.exhaustive

        }
    }

    private fun populateCarsList(cars: List<CarDetails>) {
        carListAdr.clear()
        cars.forEach { car ->
            if (car.def) {
                viewModel.car = car
            }
            carListAdr.add(CarItemSelectionView(car, object : MyItemClickListener {
                override fun onClick(pos: Int, view: View) {
                    super.onClick(pos, view)
                    for (i in 0 until carListAdr.itemCount) {
                        (carListAdr.getItem(i) as CarItemSelectionView).car.def = false
                    }
                    car.def = true
                    viewModel.car = car
                    carListAdr.notifyDataSetChanged()
                }
            }))
        }
        checkFields()
    }

    private fun setup() {
        carsList.adapter = carListAdr

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.add_post)

        balloon = Balloon.Builder(this)
            .setLayout(R.layout.layout_calendar)
            .setArrowSize(10)
            .setArrowOrientation(ArrowOrientation.LEFT)
            .setArrowPosition(0.5f)
            .setWidthRatio(0.7f)
            .setHeight(360)
            .setCornerRadius(4f)
            .setBackgroundColor(ContextCompat.getColor(this, R.color.white))
            .setBalloonAnimation(BalloonAnimation.CIRCULAR)
            .setLifecycleOwner(this)
            .build()

        val calendar = balloon.getContentView().findViewById<CalendarView>(R.id.calendar)
        val cal = Calendar.getInstance()

        calendar.minDate = cal.timeInMillis

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            viewModel.setDate(dayOfMonth, month, year)
            val calTemp = Calendar.getInstance()
            calTemp.set(year, month, dayOfMonth)
            tvDate.text = DateUtils.getFormattedDate(calTemp.timeInMillis, this)
            balloon.dismiss()
            checkFields()
        }
        tvDate.text = DateUtils.getFormattedDate(viewModel.calendar.timeInMillis, this)


    }

    private fun checkFields() {
        postCreate.isEnabled = viewModel.car != null &&
                viewModel.departureDate.isNotBlank() && viewModel.price != null && viewModel.placeFrom != null && viewModel.placeTo != null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}