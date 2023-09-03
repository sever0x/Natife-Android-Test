package com.shdwraze.natife.network.model

import com.google.gson.annotations.SerializedName


data class Meta (

	@SerializedName("status"      ) var status     : Int?    = null,
	@SerializedName("msg"         ) var msg        : String? = null,
	@SerializedName("response_id" ) var responseId : String? = null

)