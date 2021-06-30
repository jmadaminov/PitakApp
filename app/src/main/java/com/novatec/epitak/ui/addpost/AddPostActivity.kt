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
import com.novatec.epitak.viewobjects.CarViewObj
import com.novatec.epitak.viewobjects.DriverPostViewObj
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
            viewModel.setEditingPost(driverPostViewObj)
            supportActionBar?.title = getString(R.string.edit_post)
            if (driverPostViewObj.postType == EPostType.DRIVER_PARCEL) {
                number_picker.max = 0
                cbTakeParcel.isEnabled = false
            } else {
                cbTakeParcel.isEnabled = true
                number_picker.min = 1
            }
        }

    }


    private fun attachListeners() {
        number_picker.addOnSeatCountChangeListener { seatCount ->
            if (seatCount == 0) {
                viewModel.postToBeAdded.value!!.postType = EPostType.DRIVER_PARCEL
                cbTakeParcel.isChecked = true
            } else viewModel.postToBeAdded.value!!.postType = EPostType.DRIVER_SM
            seatCount?.let { viewModel.setSeatCound(it) }
            checkFields()
        }

        priceInput.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) return@doOnTextChanged
            viewModel.setPrice(text.toString().toInt())
            checkFields()
        }
        noteInput.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) return@doOnTextChanged
            viewModel.setPostRemark(text.toString())
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
            viewModel.postToBeAdded.value!!.timeFirstPart = checkFirstPartDay.isChecked
        }
        checkSecondPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkThirdPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkSecondPartDay.isChecked = true
            }
            viewModel.postToBeAdded.value!!.timeSecondPart = checkSecondPartDay.isChecked
        }
        checkThirdPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkSecondPartDay.isChecked && !checkFourthPartDay.isChecked) {
                checkThirdPartDay.isChecked = true
            }
            viewModel.postToBeAdded.value!!.timeThirdPart = checkThirdPartDay.isChecked
        }
        checkFourthPartDay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked && !checkFirstPartDay.isChecked && !checkSecondPartDay.isChecked && !checkThirdPartDay.isChecked) {
                checkFourthPartDay.isChecked = true
            }
            viewModel.postToBeAdded.value!!.timeFourthPart = checkFourthPartDay.isChecked
        }

        tvDate.setOnClickListener { balloon.show(tvDate) }

        cbTakeParcel.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.canTakeParcel(isChecked)
            number_picker.value = viewModel.postToBeAdded.value!!.seat!!
            checkFields()
        }

        tvFrom.setOnClickListener {
            DestinationBSD().also {
                it.arguments = Bundle().apply { putBoolean(ARG_IS_FROM, true) }
                this.supportFragmentManager.setFragmentResultListener(REQ_DESTINATION,
                                                                      this) { key, bundle ->
                    viewModel.postToBeAdded.value!!.from = bundle.getParcelable(RESULT_PLACE)!!
                    tvFrom.text = viewModel.postToBeAdded.value!!.from!!.name

                }
            }.show(supportFragmentManager, "")
        }

        tvTo.setOnClickListener {
            DestinationBSD().also {
                it.arguments = Bundle().apply { putBoolean(ARG_IS_FROM, false) }
                this.supportFragmentManager.setFragmentResultListener(REQ_DESTINATION,
                                                                      this) { key, bundle ->
                    viewModel.postToBeAdded.value!!.to = bundle.getParcelable(RESULT_PLACE)!!
                    tvTo.text = viewModel.postToBeAdded.value!!.to!!.name

                }
            }.show(supportFragmentManager, "")
        }

        postCreate.setOnClickListener {
            viewModel.createDriverPost(viewModel.postToBeAdded.value!!.toDriverPost())
        }

    }

    private fun subscribeObservers() {
        viewModel.postToBeAdded.observe(this) { post ->
            post.remark?.let { noteInput.setText(it) }
            post.price?.let { priceInput.setText(it.toString()) }
            post.from?.let { tvFrom.text = it.name ?: it.districtName ?: it.regionName }
            post.to?.let { tvTo.text = it.name ?: it.districtName ?: it.regionName }
            post.departureDate?.let {
                tvDate.text = DateUtils.getFormattedDate(viewModel.calendar.timeInMillis,
                                                         this@AddPostActivity)
            }
            post.seat?.let { number_picker.value = it }
            post.timeFirstPart?.let { checkFirstPartDay.isChecked = it }
            post.timeSecondPart?.let { checkSecondPartDay.isChecked = it }
            post.timeThirdPart?.let { checkThirdPartDay.isChecked = it }
            post.timeFourthPart?.let { checkFourthPartDay.isChecked = it }
            post.price?.let { priceInput.setText(it.toString()) }
            post.pkg?.let { cbTakeParcel.isChecked = it }

            if (post.seat == 0) {
                priceInputLayout.hint = getString(R.string.minimal_price)
            } else {
                priceInputLayout.hint = getString(R.string.price_for_one)
            }
            checkFields()
        }

        viewModel.carsResponse.observe(this) {
            val response = it ?: return@observe

            when (response) {
                is ErrorWrapper.RespError -> {
                    Snackbar.make(scrollView,
                                  response.message,
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
                    Snackbar.make(scrollView, response.message, Snackbar.LENGTH_SHORT).show()
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
                viewModel.postToBeAdded.value!!.car = CarViewObj.fromCarModel(car)
            }
            carListAdr.add(CarItemSelectionView(car, object : MyItemClickListener {
                override fun onClick(pos: Int, view: View) {
                    super.onClick(pos, view)
                    for (i in 0 until carListAdr.itemCount) {
                        (carListAdr.getItem(i) as CarItemSelectionView).car.def = false
                    }
                    car.def = true
                    viewModel.postToBeAdded.value!!.car = CarViewObj.fromCarModel(car)
                    carListAdr.notifyDataSetChanged()
                }
            }))
        }

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

        }
        tvDate.text = DateUtils.getFormattedDate(viewModel.calendar.timeInMillis, this)


    }

    private fun checkFields() {
        postCreate.isEnabled =
            viewModel.postToBeAdded.value!!.car != null
                    && !viewModel.postToBeAdded.value!!.departureDate.isNullOrBlank()
                    && viewModel.postToBeAdded.value!!.price != null
                    && viewModel.postToBeAdded.value!!.from != null
                    && viewModel.postToBeAdded.value!!.to != null
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