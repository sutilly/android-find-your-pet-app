package com.example.findmypet.dataAccess

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class OwnerWebEntity (
    @JsonProperty("id") val id: Int?,
    @JsonProperty("name") val name: String,
    @JsonProperty("latitude") val latitude: Double,
    @JsonProperty("longitude") val longitude: Double,
    @JsonProperty("pets") val pets: List<PetWebEntity>
)

