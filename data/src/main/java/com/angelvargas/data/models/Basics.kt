package com.angelvargas.data.models

import com.google.gson.annotations.SerializedName

data class Basics(
    @SerializedName("name")
    val name: String?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("picture")
    val picture: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("profiles")
    val profiles: List<Profiles>?
)