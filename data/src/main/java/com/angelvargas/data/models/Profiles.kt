package com.angelvargas.data.models

import com.google.gson.annotations.SerializedName

data class Profiles(
    @SerializedName("network")
    val network: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("url")
    val url: String?
)