package com.novatec.data.mapper

import com.novatec.data.model.DriverPostEntity
import com.novatec.data.model.FilterEntity
import com.novatec.data.model.PassengerPostEntity
import com.novatec.data.model.PlaceEntity
import com.novatec.domain.domainmodel.Filter
import com.novatec.domain.domainmodel.PassengerPost
import com.novatec.domain.domainmodel.Place
import javax.inject.Inject


/**
 * Map a [DriverPostEntity] to and from a [DriverPostModel] instance when data is moving between
 * this later and the Domain layer
 */
open class FilterMapper @Inject constructor() : Mapper<FilterEntity, Filter> {

    /**
     * Map a [PassengerPostEntity] instance to a [PassengerPost] instance
     */
    override fun mapFromEntity(type: FilterEntity): Filter {

        return Filter(type.airConditioner,
                             type.departureDate,
                             type.fromDistrictId,
                             type.fromRegionId,
                             type.toDistrictId,
                             type.toRegionId,
                             type.maxPrice,
                             type.minPrice,
                             type.priceOrder,
                             type.seat,
                             type.timeFirstPart,
                             type.timeSecondPart,
                             type.timeThirdPart,
                             type.timeFourthPart)
    }

    /**
     * Map a [PassengerPost] instance to a [PassengerPostEntity] instance
     */
    override fun mapToEntity(type: Filter): FilterEntity {

        return FilterEntity(type.airConditioner,
                            type.departureDate,
                            type.fromDistrictId,
                            type.fromRegionId,
                            type.toDistrictId,
                            type.toRegionId,
                            type.maxPrice,
                            type.minPrice,
                            type.priceOrder,
                            type.seat,
                            type.timeFirstPart,
                            type.timeSecondPart,
                            type.timeThirdPart,
                            type.timeFourthPart)
    }

}