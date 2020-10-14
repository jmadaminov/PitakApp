package com.badcompany.pitak.ui.addcar

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asksira.bsimagepicker.BSImagePicker
import com.badcompany.core.Constants
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.domain.domainmodel.Car
import com.badcompany.domain.domainmodel.ColorsAndModels
import com.badcompany.domain.domainmodel.PhotoBody
import com.badcompany.pitak.App
import com.badcompany.pitak.R
//import com.badcompany.pitak.di.viewmodels.AddCarViewModelFactory
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.viewgroups.ItemAddPhoto
import com.badcompany.pitak.ui.viewgroups.ItemCarPhoto
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.getFileName
import com.badcompany.pitak.util.loadImageUrl
import com.badcompany.pitak.viewobjects.CarColorViewObj
import com.badcompany.pitak.viewobjects.CarViewObj
import com.badcompany.pitak.viewobjects.IdNameViewObj
import com.badcompany.pitak.viewobjects.ImageViewObj
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.activity_add_car.*
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject


class AddCarActivity : BaseActivity(), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate,
    OnItemClickListener {


    private var carModelsAdapter: ModelsArrayAdapter? = null
    private var carColorsAdapter: ColorsArrayAdapter? = null
    private lateinit var car: CarViewObj
    val adapter = GroupAdapter<GroupieViewHolder>()

//    @Inject
//    lateinit var viewModelFactory: AddCarViewModelFactory

    private val viewmodel: AddCarViewModel by viewModels()

//    override fun inject() {
//        (application as App).addCarComponent()
//            .inject(this)
//    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        car = if (intent.extras == null) CarViewObj()
        else intent.getParcelableExtra(Constants.TXT_CAR) as CarViewObj

//        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        subscribeObservers()
        setupActionBar()
        setupListeners()
        setupCarPhotoGrid()
        viewmodel.getCarColorsAndModels()
    }

    private fun setupCarPhotoGrid() {
        rv_photo_grid.isNestedScrollingEnabled = false
        rv_photo_grid.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        rv_photo_grid.setHasFixedSize(true)
        rv_photo_grid.adapter = adapter

    }

    @InternalCoroutinesApi
    @ExperimentalSplittiesApi
    private fun setupListeners() {
        carImage.setOnClickListener {
            val singleSelectionPicker: BSImagePicker =
                BSImagePicker.Builder("com.badcompany.fileprovider")
//                    .setMaximumDisplayingImages(24) //Default: Integer.MAX_VALUE. Don't worry about performance :)
                    .setSpanCount(3) //Default: 3. This is the number of columns
//                    .setGridSpacing(Utils.dp2px(2)) //Default: 2dp. Remember to pass in a value in pixel.
//                    .setPeekHeight(Utils.dp2px(360)) //Default: 360dp. This is the initial height of the dialog.
                    .hideCameraTile() //Default: show. Set this if you don't want user to take photo.
                    .hideGalleryTile() //Default: show. Set this if you don't want to further let user select from a gallery app. In such case, I suggest you to set maximum displaying images to Integer.MAX_VALUE.
                    .setTag("IS_AVATAR") //Default: null. Set this if you need to identify which picker is calling back your fragment / activity.
//                    .dismissOnSelect(true) //Default: true. Set this if you do not want the picker to dismiss right after selection. But then you will have to dismiss by yourself.
//                    .useFrontCamera(true) //Default: false. Launching camera by intent has no reliable way to open front camera so this does not always work.
                    .build()
            singleSelectionPicker.show(supportFragmentManager, "picker")
        }

        retry.setOnClickListener {
            viewmodel.getCarColorsAndModels()
        }

        saveCar.setOnClickListener {
            viewmodel.saveCar(AppPreferences.token, makeCarFromCarViewObj(car))
        }

        carPlateNumberInput.doAfterTextChanged {
            if (!it.isNullOrBlank()) car.carNumber = it.toString()
            updateSaveButtonState()
        }

        carYearInput.doAfterTextChanged {
            if (!it.isNullOrBlank()) car.carYear = it.toString().toInt()
            updateSaveButtonState()
        }

        carModelSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long) {
                car.carModel = IdNameViewObj(carModelsAdapter!!.models[position].id)
                updateSaveButtonState()
            }

        }
        carColorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?,
                                        position: Int,
                                        id: Long) {
                car.carColor = CarColorViewObj(carColorsAdapter!!.colors[position].id)
                updateSaveButtonState()
            }

        }

        fuelTypeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            car.fuelType =
                if (radioPetrol.isChecked) Constants.FUEL_TYPE_PETROL else if (radioMethane.isChecked) Constants.FUEL_TYPE_PETROL else Constants.FUEL_TYPE_PETROL
        }

        checkboxAC.setOnCheckedChangeListener { buttonView, isChecked ->
            car.airConditioner = isChecked
            updateSaveButtonState()
        }
    }

    private fun setupActionBar() {

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun subscribeObservers() {
        viewmodel.carSaveReponse.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    saveCar.revertAnimation()
                    Snackbar.make(parentLayout, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    saveCar.revertAnimation()
                    Snackbar.make(parentLayout,
                                  getString(R.string.system_error),
                                  Snackbar.LENGTH_SHORT)
                        .show()

                }
                is ResultWrapper.Success -> {
                    saveCar.stopAnimation()
                    setResult(Activity.RESULT_OK)
                    finish()
//                    (application as App).releaseAddCarComponent()
                }
                ResultWrapper.InProgress -> {
                    saveCar.startAnimation()
                }
            }.exhaustive

        })
        viewmodel.carAvatarResponse.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    stopLoadingAvatar()
                    Snackbar.make(parentLayout, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ErrorWrapper.SystemError -> {
                    stopLoadingAvatar()
                    Snackbar.make(parentLayout,
                                  getString(R.string.system_error),
                                  Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultWrapper.Success -> {
                    stopLoadingAvatar()
                    car.image.id = response.value.id!!
                    carImage.loadImageUrl(response.value.link!!)
                }
                ResultWrapper.InProgress -> {
                    startLoadingAvatar()
                }
            }.exhaustive

        })
        viewmodel.carImgResponse.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                    showCarImageUploadError(response.message.toString())
                }
                is ErrorWrapper.SystemError -> {
                    showCarImageUploadError(response.err.toString())
                }
                is ResultWrapper.Success -> {
                    stopCarImageItemLoading()
                    if (adapter.itemCount < 3) {
                        adapter.add(0, makeCarItem(response.value))
                        adapter.notifyItemChanged(0)
                    } else {
                        adapter.remove(adapter.getItem(adapter.itemCount - 1))
                        adapter.add(makeCarItem(response.value))
                    }
                    addCarImageToRequestList(response.value)
                }
                ResultWrapper.InProgress -> {
                    startCarImageItemLoading()
                }
            }.exhaustive
        })

        viewmodel.colorsAndModels.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    showColorsModelsGetError(response.message)
                }
                is ErrorWrapper.SystemError -> {
                    showColorsModelsGetError(response.err.localizedMessage)
                }
                is ResultWrapper.Success -> {
                    hideColorsModelsLoading()
                    setupColorsModelsSpinners(response.value)
                }
                ResultWrapper.InProgress -> {
                    showColorsModelsLoading()
                }
            }.exhaustive

        })
    }

    private fun addCarImageToRequestList(photoBody: PhotoBody) {
        (car.imageList as ArrayList).add(ImageViewObj(photoBody.id))
        updateSaveButtonState()
    }

    private fun setupColorsModelsSpinners(value: ColorsAndModels) {
        carColorsAdapter = ColorsArrayAdapter(this, value.colors)
        carModelsAdapter = ModelsArrayAdapter(this, value.models)
        carModelSpinner.adapter = carModelsAdapter
        carColorSpinner.adapter = carColorsAdapter
        setupCarValues(value)
    }

    private fun showColorsModelsLoading() {
        progress.visibility = View.VISIBLE
        scrollView.visibility = View.INVISIBLE
        infoLayout.visibility = View.INVISIBLE
    }

    private fun hideColorsModelsLoading() {
        progress.visibility = View.INVISIBLE
        scrollView.visibility = View.VISIBLE
        infoLayout.visibility = View.INVISIBLE
    }

    private fun showColorsModelsGetError(message: String?) {
        progress.visibility = View.INVISIBLE
        scrollView.visibility = View.INVISIBLE
        infoLayout.visibility = View.VISIBLE
        errorMessage.text = message
    }

    private fun startLoadingAvatar() {
        progressImageAdding.visibility = View.VISIBLE
        carImage.visibility = View.INVISIBLE
        labelAddCarAvatar.visibility = View.INVISIBLE
        carPlaceholderImage.visibility = View.INVISIBLE
    }

    private fun stopLoadingAvatar() {
        progressImageAdding.visibility = View.INVISIBLE
        labelAddCarAvatar.visibility = View.VISIBLE
        carPlaceholderImage.visibility = View.VISIBLE
        carImage.visibility = View.VISIBLE
    }

    private fun stopCarImageItemLoading() {
        (adapter.getItem(adapter.itemCount - 1) as ItemAddPhoto).isLoading = false
        adapter.notifyItemChanged(adapter.itemCount - 1)
    }

    private fun startCarImageItemLoading() {
        (adapter.getItem(adapter.itemCount - 1) as ItemAddPhoto).isLoading = true
        adapter.notifyItemChanged(adapter.itemCount - 1)
    }

    private fun showCarImageUploadError(message: String) {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_SHORT).show()
        (adapter.getItem(adapter.itemCount - 1) as ItemAddPhoto).isLoading = false
        adapter.notifyItemChanged(adapter.itemCount - 1)
    }

    private fun makeCarItem(photoBody: PhotoBody) =
        ItemCarPhoto(photoBody, deleteClickListener)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSingleImageSelected(uri: Uri, tag: String) {
        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(uri, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(uri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        viewmodel.uploadCarPhoto(file, tag == "IS_AVATAR")
    }

    override fun onItemClick(item: Item<*>, view: View) {
        val singleSelectionPicker: BSImagePicker =
            BSImagePicker.Builder("com.badcompany.fileprovider")
                .setSpanCount(3) //Default: 3. This is the number of columns
                .hideCameraTile() //Default: show. Set this if you don't want user to take photo.
                .hideGalleryTile() //Default: show. Set this if you don't want to further let user select from a gallery app. In such case, I suggest you to set maximum displaying images to Integer.MAX_VALUE.
                .setTag(item.getPosition(item).toString())
                //Default: null. Set this if you need to identify which picker is calling back your fragment / activity.
                .build()

        singleSelectionPicker.show(supportFragmentManager, "picker")
    }

    override fun loadImage(imageUri: Uri, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }

    private fun makeCarFromCarViewObj(carViewObj: CarViewObj): Car {
        val imgs = arrayListOf<PhotoBody>()
        carViewObj.imageList?.forEach { imgs.add(PhotoBody(it.id)) }
        return Car(carViewObj.id,
                   carViewObj.carModel!!.id,
                   carViewObj.image!!.id,
                   carViewObj.fuelType,
                   carViewObj.carColor!!.id,
                   carViewObj.carNumber,
                   carViewObj.carYear,
                   carViewObj.airConditioner!!,
                   imgs)
    }

    fun updateSaveButtonState() {
        saveCar.isEnabled = !car.carNumber.isNullOrBlank() &&
                car.carModel!!.id != null &&
                car.carColor!!.id != null &&
                car.carYear != null &&
                car.fuelType != null &&
                car.airConditioner != null &&
                car.image!!.id != null

        if (saveCar.isEnabled) {
            val bg = saveCar.background
            bg.setColorFilter(ContextCompat.getColor(this, R.color.orange),
                              PorterDuff.Mode.SRC_ATOP)
            saveCar.background = bg
        } else {
            val bg = saveCar.background
            bg.setColorFilter(ContextCompat.getColor(this, R.color.line_textColorGrey),
                              PorterDuff.Mode.SRC_ATOP)
            saveCar.background = bg
        }

    }

    private fun setupCarValues(value: ColorsAndModels) {
        if (car.fuelType == null) {
            car.fuelType = Constants.FUEL_TYPE_PETROL
        }
        if ( car.image.link != null) {
            carImage.loadImageUrl(car.image!!.link!!)
        }
        if (!car.imageList.isNullOrEmpty()) {
            car.imageList?.forEach {
                adapter.add(ItemCarPhoto(PhotoBody(it.id, link = it.link), deleteClickListener))
            }
        }
        if (!car.carNumber.isNullOrBlank()) {
            carPlateNumberInput.setText(car.carNumber!!)
        }
        if (car.carYear != null) {
            carYearInput.text = Editable.Factory.getInstance().newEditable(car.carYear!!.toString())
        }
        if (car.fuelType != null) {
            when (car.fuelType) {
                Constants.FUEL_TYPE_PETROL -> radioPetrol.isChecked = true
                Constants.FUEL_TYPE_METHANE -> radioMethane.isChecked = true
                else -> radioPropane.isChecked = true
            }
        }
        if (car.carModel != null && car.carModel!!.id != null) {
            for (i in value.models.indices) {
                if (value.models[i].id == car.carModel!!.id) {
                    carModelSpinner.setSelection(i)
                    break
                }
            }
        }
        if (car.carColor != null && car.carColor!!.id != null) {
            for (i in value.colors.indices) {
                if (value.colors[i].id == car.carColor!!.id) {
                    carColorSpinner.setSelection(i)
                    break
                }
            }
        }
        if (adapter.itemCount < 3) {
            adapter.add(adapter.itemCount,                        ItemAddPhoto(this))
            adapter.notifyDataSetChanged()
        }
    }

    private val deleteClickListener = OnItemClickListener { item, view ->
        if (adapter.itemCount == 3 && adapter.getItem(adapter.itemCount - 1) !is ItemAddPhoto) {
            adapter.add(ItemAddPhoto(this))
        }
        adapter.remove(item)
        adapter.notifyItemChanged(item.getPosition(item))
        (car.imageList as ArrayList).removeAt(item.getPosition(item))
    }

}
