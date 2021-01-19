package com.example.findmypet.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pets")
data class Pet (
        @PrimaryKey
    val id: Int?,
        val ownerId: Int,
        val name: String,
        var isMissing: Boolean,
        val latitude: Double,
        var longitude: Double
)