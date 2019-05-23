package com.angelvargas.cvapp.domain.models


data class BasicsData(
    val name: String? = "",
    val label: String? = "",
    val picture: String? = "",
    val email: String? = "",
    val summary: String? = "",
    val profiles: List<ProfileData>? = emptyList()
)