package com.shdwraze.natife.network.model

import com.google.gson.annotations.SerializedName


data class DownsizedStill (

	@SerializedName("height" ) var height : String? = null,
	@SerializedName("width"  ) var width  : String? = null,
	@SerializedName("size"   ) var size   : String? = null,
	@SerializedName("url"    ) var url    : String? = null

)