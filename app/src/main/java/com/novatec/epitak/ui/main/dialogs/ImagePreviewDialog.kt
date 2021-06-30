package com.novatec.epitak.ui.main.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.novatec.epitak.R
import com.novatec.epitak.util.load
import kotlinx.android.synthetic.main.dialog_image_preview.*


const val ARG_IMG = "IMG"

class ImagePreviewDialog : DialogFragment(R.layout.dialog_image_preview) {

    private lateinit var imageUrl: String

    override fun onAttach(context: Context) {
        super.onAttach(context)

        imageUrl = requireArguments().getString(ARG_IMG)!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image.load(imageUrl)

        ivClose.setOnClickListener {
            dismiss()
        }
    }
}