package com.shdwraze.natife.network.model

import com.google.gson.annotations.SerializedName


data class Giphy (

    @SerializedName("data"       ) var data       : ArrayList<Data> = arrayListOf(),
    @SerializedName("pagination" ) var pagination : Pagination?     = Pagination(),
    @SerializedName("meta"       ) var meta       : Meta?           = Meta()

)