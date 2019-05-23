package com.angelvargas.cvapp.adapters.viewtypes

class SkillsViewType(val name : String?,
                     val level : String?,
                     val keywords : List<String>?): RecyclerViewType {

    override fun getViewType(): Int {
        return SKILLS_VIEW_TYPE
    }
}