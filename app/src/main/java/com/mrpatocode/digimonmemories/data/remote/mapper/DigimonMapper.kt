package com.mrpatocode.digimonmemories.data.remote.mapper

import com.mrpatocode.digimonmemories.data.remote.dto.DigimonResponseDtoItem
import com.mrpatocode.digimonmemories.domain.model.Digimon

fun DigimonResponseDtoItem.toDomain(): Digimon{
    return Digimon(
        img = this.img,
        level = this.level,
        name = this.name
    )
}