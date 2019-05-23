package com.angelvargas.cvapp.adapters.viewtypes

import com.angelvargas.cvapp.adapters.RecyclerViewType

class SectionTitleViewType(val sectionTitle: String): RecyclerViewType {

    override fun getViewType(): Int {
        return SECTION_VIEW_TYPE
    }
}