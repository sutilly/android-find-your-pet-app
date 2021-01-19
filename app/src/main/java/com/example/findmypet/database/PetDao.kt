package com.example.findmypet.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PetDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insertPet(pet: Pet): Long

    @Update (onConflict = OnConflictStrategy.IGNORE)
    fun updatePet(pet:Pet)

    @Transaction
    fun upsertPet(pet:Pet) {
        val insertPet = insertPet(pet)
        if (insertPet == ((-1).toLong())) {
            updatePet(pet)
        }
    }

    @Query("SELECT * FROM pets WHERE ownerId = :id order by name")
    fun getAllPetsOfOwner(id: Int):LiveData<List<Pet>>

    @Query("SELECT * FROM pets WHERE ownerId = :id AND isMissing = 1 order by name")
    fun getOnlyMissingPetsOfOwner(id:Int): LiveData<List<Pet>>

    @Query("SELECT COUNT(id) FROM pets WHERE ownerId = :id AND isMissing = 1")
    fun getNumberOfMissingPets(id:Int): LiveData<Int>

    /*
    *  @Query("DELETE from pets")
    fun deleteAll()

    @Delete
    fun delete(pet: Pet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pets: List<Pet>)
    * */
}