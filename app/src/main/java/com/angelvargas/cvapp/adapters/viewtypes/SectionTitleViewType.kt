package com.angelvargas.cvapp.adapters.viewtypes

class SectionTitleViewType(val sectionTitle: String): RecyclerViewType {

    override fun getViewType(): Int {
        return SECTION_VIEW_TYPE
    }
}