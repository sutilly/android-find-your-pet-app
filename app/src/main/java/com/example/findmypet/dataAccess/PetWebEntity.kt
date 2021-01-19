package com.example.findmypet.dataAccess

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class PetWebEntity (
    @JsonProperty("id") val id: Int?,
    @JsonProperty("ownerId") val ownerId: Int,
    @JsonProperty("name") val name: String,
    @JsonProperty("isMissing") val isMissing: Boolean,
    @JsonProperty("latitude") val latitude: Double,
    @JsonProperty("longitude") val longitude: Double
)
