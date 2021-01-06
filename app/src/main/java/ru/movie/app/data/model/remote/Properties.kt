package ru.movie.app.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Properties(
    @SerialName("images") val imageProperties: ImageProps
)
