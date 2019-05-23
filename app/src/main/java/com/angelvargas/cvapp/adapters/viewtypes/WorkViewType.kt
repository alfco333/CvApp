package com.angelvargas.cvapp.adapters.viewtypes

class WorkViewType(val company: String?,
                   val position: String?,
                   val urlImage: String?,
                   val startDate: String?,
                   val endDate: String?,
                   val summary: String?): RecyclerViewType {

    override fun getViewType(): Int {
        return WORK_VIEW_TYPE
    }
}