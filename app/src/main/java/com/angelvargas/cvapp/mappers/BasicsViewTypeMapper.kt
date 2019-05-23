package com.angelvargas.cvapp.mappers

import com.angelvargas.cvapp.adapters.viewtypes.BasicsViewType
import com.angelvargas.cvapp.domain.models.BasicsData
import com.angelvargas.data.mappers.Transform

class BasicsViewTypeMapper: Transform<BasicsData, BasicsViewType>() {

    override fun transform(value: BasicsData): BasicsViewType {
        return BasicsViewType(value.name, value.label, value.picture, value.email, value.summary)
    }
}