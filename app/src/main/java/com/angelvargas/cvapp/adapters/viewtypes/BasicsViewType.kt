package com.angelvargas.cvapp.adapters.viewtypes

class BasicsViewType(
    val name: String?,
    val label: String?,
    val picture: String?,
    val email: String?,
    val summary: String?
): RecyclerViewType {

    override fun getViewType(): Int {
        return BASICS_VIEW_TYPE
    }
}