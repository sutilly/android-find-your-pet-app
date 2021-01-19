package com.example.findmypet.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "owners")
data class Owner(
    @PrimaryKey
    val id: Int?,
    val name: String,
    val latitude: Double,
    val longitude: Double
)
