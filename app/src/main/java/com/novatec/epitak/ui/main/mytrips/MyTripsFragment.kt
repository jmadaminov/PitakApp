package com.novatec.epitak.ui.main.mytrips

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.novatec.epitak.R
import com.novatec.epitak.ui.main.MainActivity
import com.novatec.epitak.ui.main.mytrips.activetrips.ActiveTripsFragment
import com.novatec.epitak.ui.main.mytrips.historytrips.HistoryTripsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_my_trips.*


@AndroidEntryPoint
class MyTripsFragment : Fragment(R.layout.fragment_my_trips) {

    private var mediator: TabLayoutMediator? = null
    private val viewModel: MyTripsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.cancelActiveJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).showTabLayout()

        setupViewPager()
    }

    private fun setupViewPager() {
        pager.adapter = ScreenSlidePagerAdapter(childFragmentManager)
        mediator = TabLayoutMediator(requireActivity().findViewById(R.id.tab_layout),
                                     pager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.active)
                else -> getString(R.string.history)
            }
        }
        mediator?.attach()
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) :
        FragmentStateAdapter(this) {

        lateinit var currentFrag: Fragment
        var activeOrdersFrag = ActiveTripsFragment()
        var historyOrdersFrag = HistoryTripsFragment()

        override fun getItemCount() = 2

        override fun createFragment(position: Int): Fragment {
            currentFrag = when (position) {
                0 -> activeOrdersFrag
                else -> historyOrdersFrag
            }
            return currentFrag
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        pager.adapter = null
        mediator?.detach()
        mediator = null
    }
}