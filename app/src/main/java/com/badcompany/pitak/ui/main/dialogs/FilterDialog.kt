package com.badcompany.pitak.ui.main.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.badcompany.pitak.R
import com.badcompany.pitak.ui.main.dialogs.SwipeDismissTouchListener.DismissCallbacks
import com.wunderlist.slidinglayer.SlidingLayer
import kotlinx.android.synthetic.main.dialog_filter.*


/**
 * Created by jahon on 20-Jul-20
 */
class FilterDialog : DialogFragment() {


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.dialog_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
//        slidingLayer.setShadowSizeRes(R.dimen.shadow_size);
//        slidingLayer.setOffsetDistanceRes(R.dimen.offset_distance);
//        slidingLayer.setPreviewOffsetDistanceRes(R.dimen.preview_offset_distance);
//        slidingLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT)
//        slidingLayer.setChangeStateOnTap(false)
//
//        slidingLayer.addView( Button(requireContext()))

    }

//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val window = dialog!!.window
//        window!!.decorView.setOnTouchListener(SwipeDismissTouchListener(window!!.decorView,
//                                                                        null,
//                                                                        object :
//                                                                            DismissCallbacks {
//                                                                            override fun canDismiss(
//                                                                                token: Any?): Boolean {
//                                                                                return true
//                                                                            }
//
//                                                                            override fun onDismiss(
//                                                                                view: View?,
//                                                                                token: Any?) {
//                                                                                dismiss()
//                                                                            }
//                                                                        }))
//
//        return super.onCreateDialog(savedInstanceState)
//    }

//    override fun onResume() {
//        super.onResume()
//        val window = dialog!!.window
//        window!!.decorView.setOnTouchListener(SwipeDismissTouchListener(window.decorView,
//                                                                        null,
//                                                                        object :
//                                                                            DismissCallbacks {
//                                                                            override fun canDismiss(
//                                                                                token: Any): Boolean {
//                                                                                return true
//                                                                            }
//
//                                                                            override fun onDismiss(
//                                                                                view: View?,
//                                                                                token: Any?) {
//                                                                                dismiss()
//                                                                            }
//                                                                        }))
//    }


}