package com.badcompany.pitak.ui.addcar

import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asksira.bsimagepicker.BSImagePicker
import com.badcompany.core.ErrorWrapper
import com.badcompany.core.ResultWrapper
import com.badcompany.core.exhaustive
import com.badcompany.pitak.App
import com.badcompany.pitak.R
import com.badcompany.pitak.di.viewmodels.AddCarViewModelFactory
import com.badcompany.pitak.ui.BaseActivity
import com.badcompany.pitak.ui.viewgroups.ItemAddPhoto
import com.badcompany.pitak.util.AppPreferences
import com.badcompany.pitak.util.loadCircleImageUrl
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import com.xwray.groupie.OnItemClickListener
import kotlinx.android.synthetic.main.activity_add_car.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi
import java.io.File
import javax.inject.Inject


class AddCarActivity : BaseActivity(), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate,
    OnItemClickListener {


    @Inject
    lateinit var viewModelFactory: AddCarViewModelFactory


    private val viewmodel: AddCarViewModel by viewModels {
        viewModelFactory
    }

    override fun inject() {
        (application as App).addCarComponent()
            .inject(this)
    }

    @InternalCoroutinesApi
    @ExperimentalCoroutinesApi
    @ExperimentalSplittiesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        subscribeObservers()
        setupActionBar()
        setupOnClickListeners()
        viewmodel.getCarColorsAndModels(AppPreferences.token)
        setupCarPhotoGrid()
    }


    val adapter = GroupAdapter<GroupieViewHolder>()
    private fun setupCarPhotoGrid() {
        rv_photo_grid.isNestedScrollingEnabled = false
        rv_photo_grid.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        rv_photo_grid.setHasFixedSize(true)
        rv_photo_grid.adapter = adapter
        adapter.add(ItemAddPhoto(this))
        adapter.notifyDataSetChanged()
    }

    @InternalCoroutinesApi
    @ExperimentalSplittiesApi
    private fun setupOnClickListeners() {
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
            viewmodel.getCarColorsAndModels(AppPreferences.token)
        }

    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }


    private fun subscribeObservers() {
        viewmodel.carAvatarResponse.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    progressImageAdding.visibility = View.INVISIBLE
                    carImage.visibility = View.VISIBLE
                }
                is ErrorWrapper.SystemError -> {
                    progressImageAdding.visibility = View.INVISIBLE
                    carImage.visibility = View.VISIBLE
                }
                is ResultWrapper.Success -> {
                    progressImageAdding.visibility = View.INVISIBLE
                    carImage.visibility = View.VISIBLE
                    carImage.loadCircleImageUrl(response.value.link!!)
                }
                ResultWrapper.InProgress -> {
                    progressImageAdding.visibility = View.VISIBLE
                    carImage.visibility = View.GONE
                }
            }.exhaustive

        })
        viewmodel.colorsAndModels.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {
                    progress.visibility = View.INVISIBLE
                    scrollView.visibility = View.INVISIBLE
                    infoLayout.visibility = View.VISIBLE
                    errorMessage.text = response.message
                }
                is ErrorWrapper.SystemError -> {
                    progress.visibility = View.INVISIBLE
                    scrollView.visibility = View.INVISIBLE
                    infoLayout.visibility = View.VISIBLE
                    errorMessage.text = response.err.localizedMessage
                }
                is ResultWrapper.Success -> {
                    progress.visibility = View.INVISIBLE
                    scrollView.visibility = View.VISIBLE
                    infoLayout.visibility = View.INVISIBLE
                }
                ResultWrapper.InProgress -> {
                    progress.visibility = View.VISIBLE
                    scrollView.visibility = View.INVISIBLE
                    infoLayout.visibility = View.INVISIBLE
                }
            }.exhaustive

        })
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSingleImageSelected(uri: Uri, tag: String) {
        if (tag.equals("IS_AVATAR")) {
            viewmodel.uploadCarPhoto(File(uri.path), true)
        } else {
            viewmodel.uploadCarPhoto(File(uri.path))
        }

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

    override fun loadImage(imageUri: Uri?, ivImage: ImageView?) {
        Glide.with(this).load(File(imageUri!!.path)).into(ivImage!!);
    }


}
