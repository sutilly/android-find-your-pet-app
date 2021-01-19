package com.example.findmypet.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Owner::class, Pet::class], version = 2)
abstract class PetsAndOwnersDatabase : RoomDatabase() {
    abstract fun getOwnerDao(): OwnerDao
    abstract fun getPetDao(): PetDao
}
