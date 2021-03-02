//package com.novatec.pitak.ui.viewgroups
//
//import android.view.View
//import com.novatec.domain.domainmodel.PassengerPost
//import com.novatec.pitak.R
//import com.novatec.pitak.ui.passenger_post.PassengerPostActivity
//import com.novatec.pitak.ui.passenger_post.offer_a_ride.ARG_PASSENGER_POST
//import com.novatec.pitak.viewobjects.PassengerPostViewObj
//import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
//import com.xwray.groupie.kotlinandroidextensions.Item
//import kotlinx.android.synthetic.main.item_passenger_post.view.*
//import splitties.activities.start
//
//class PassengerPostItem(val post: PassengerPost) :
//    Item() {
//    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//
//        viewHolder.itemView.apply {
//
//            date.text = post.departureDate
//            from.text = post.from.regionName
//            to.text = post.to.regionName
//            price.text = context.getString(R.string.price_and_seats_format,
//                                           post.price.toString(), post.seat.toString())
////        seats.text = post.seat.toString()
//
//            if (!post.remark.isBlank()) {
//                note.visibility = View.VISIBLE
//                note.text = post.remark
//            } else {
//                note.visibility = View.GONE
//            }
//            cardParent.setOnClickListener {
//                context.start<PassengerPostActivity> {
//                    putExtra(ARG_PASSENGER_POST,
//                             PassengerPostViewObj.mapFromPassengerPostModel(post))
//                }
//            }
//        }
//    }
//
//    override fun getLayout() = R.layout.item_passenger_post
//}
