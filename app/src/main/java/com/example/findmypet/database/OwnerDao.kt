package com.example.findmypet.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.findmypet.database.Owner

@Dao
interface OwnerDao {
    // TODO: Implement CRUD operation queries

    @Query("SELECT * FROM owners")
    fun getAllOwners(): LiveData<List<Owner>>

    @Update (onConflict = OnConflictStrategy.IGNORE)
    fun updateOwner(owner: Owner)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOwner(owner: Owner): Long

    @Transaction
    fun upsertOwner(owner: Owner) {
        val id = insertOwner(owner)
        if (id == ((-1).toLong())) {
            updateOwner(owner)}
    }

    @Query("SELECT * FROM owners WHERE id = :id")
    fun getOwnerById(id: Int): Owner

    @Query("SELECT EXISTS(SELECT 1 FROM owners WHERE id = :id)")
    fun ownerExists(id: Int): Boolean

    /* so far not used
    *
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(owners: List<Owner>)

    @Query("DELETE FROM owners")
    fun deleteAll()

    @Delete
    fun delete(owner: Owner)

    @Query("SELECT * FROM owners WHERE name LIKE '%' || :searchTerm || '%'")
    fun getAllOwnersByNameWithLiveData(searchTerm: String): LiveData<List<Owner>>

    * */
}