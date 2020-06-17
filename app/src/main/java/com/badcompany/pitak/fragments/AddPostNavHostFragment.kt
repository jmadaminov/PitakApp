package com.badcompany.pitak.fragments

import android.content.Context
import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.navigation.fragment.NavHostFragment
import com.badcompany.pitak.ui.addpost.AddPostActivity

/**
 * Created by jahon on 20-Apr-20
 */
class AddPostNavHostFragment : NavHostFragment() {

    override fun onAttach(context: Context) {
        childFragmentManager.fragmentFactory =
            (activity as AddPostActivity).fragmentFactory
        super.onAttach(context)
    }

    companion object {
        const val KEY_GRAPH_ID = "android-support-nav:fragment:graphId"

        @JvmStatic
        fun create(
            @NavigationRes graphId: Int = 0
        ): AddPostNavHostFragment {
            var bundle: Bundle? = null
            if (graphId != 0) {
                bundle = Bundle()
                bundle.putInt(KEY_GRAPH_ID, graphId)
            }
            val result =
                AddPostNavHostFragment()
            if (bundle != null) {
                result.arguments = bundle
            }
            return result
        }
    }

}