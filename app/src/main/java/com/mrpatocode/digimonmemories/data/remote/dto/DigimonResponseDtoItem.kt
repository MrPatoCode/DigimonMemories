package com.mrpatocode.digimonmemories.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DigimonResponseDtoItem(
    @SerializedName("img")
    val img: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("name")
    val name: String
)