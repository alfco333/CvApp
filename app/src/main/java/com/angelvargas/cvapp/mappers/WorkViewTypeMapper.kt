package com.angelvargas.cvapp.mappers

import com.angelvargas.cvapp.adapters.viewtypes.WorkViewType
import com.angelvargas.cvapp.domain.models.WorkData
import com.angelvargas.data.mappers.Transform

class WorkViewTypeMapper: Transform<WorkData, WorkViewType>() {

    override fun transform(value: WorkData): WorkViewType {
        return WorkViewType(value.company,
            value.position,
            value.urlImage,
            value.startDate,
            value.endDate,
            value.summary)
    }
}