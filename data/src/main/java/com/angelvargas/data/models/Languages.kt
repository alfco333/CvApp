package com.angelvargas.data.models

import com.google.gson.annotations.SerializedName

data class Languages (
	@SerializedName("language")
	val language : String?,
	@SerializedName("fluency")
	val fluency : String?
)