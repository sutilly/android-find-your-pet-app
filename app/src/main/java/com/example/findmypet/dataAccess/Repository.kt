package com.example.findmypet.dependencyInjection

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.findmypet.dataAccess.AppExecutors
import com.example.findmypet.dataAccess.PetsAndOwnersWebService
import com.example.findmypet.database.Owner
import com.example.findmypet.database.Pet
import com.example.findmypet.database.PetsAndOwnersDatabase
import java.util.*
import javax.inject.Inject
import kotlin.random.Random.Default.nextBoolean

enum class RepoStatus { IDLE, IN_PROGRESS }

interface Repository {
    val status: LiveData<RepoStatus>
    fun loadOwners()
    fun getAllOwners(): LiveData<List<Owner>>
    fun getOwnerById(id: Int): Owner
    fun getPetsOfOwner(id: Int): LiveData<List<Pet>>
    fun getPetsOfOwnerAllOrMissing(id: Int, checkedState: Boolean): LiveData<List<Pet>>
    fun getNumberOfMissingPets(id: Int): LiveData<Int>
}

class RepositoryImpl @Inject constructor(
    private val petsAndOwnersDatabase: PetsAndOwnersDatabase,
    private val appExecutors: AppExecutors,
    private val petsAndOwnersWebService: PetsAndOwnersWebService

) : Repository {

    private val currentStatus = MutableLiveData<RepoStatus>(RepoStatus.IDLE);

    fun updateStatus(repoStatus: RepoStatus) {
        appExecutors.mainThreadExecutor.execute {
            currentStatus.value = repoStatus
        }
    }

    override fun loadOwners() {
        updateStatus(RepoStatus.IN_PROGRESS)
        appExecutors.bgThreadExecutor.execute {
            Thread.sleep(3000)
            val response = petsAndOwnersWebService.getAllOwnersAndPets().execute()
            val webOwners = response.body() ?: ArrayList()
            var dbPets = webOwners.map {
                it.pets.map {
                    Pet(
                        it.id,
                        it.ownerId,
                        it.name,
                        it.isMissing,
                        it.latitude,
                        it.longitude
                    )
                }
            }.flatten()
            val dbOwners = webOwners.map {
                Owner(
                    it.id,
                    it.name,
                    it.latitude,
                    it.longitude
                )
            }

            // load owners into DB
            dbOwners.forEach {
                petsAndOwnersDatabase.getOwnerDao().upsertOwner(it)
            }

            // load pets into DB
            dbPets.forEach {
                it.isMissing = Random().nextBoolean()
                petsAndOwnersDatabase.getPetDao().upsertPet(it)
            }

            updateStatus(RepoStatus.IDLE)
        }
    }

    override fun getAllOwners(): LiveData<List<Owner>> {
        return petsAndOwnersDatabase.getOwnerDao().getAllOwners()
    }

    override fun getOwnerById(id: Int): Owner {
        return petsAndOwnersDatabase.getOwnerDao().getOwnerById(id)
    }

    override fun getPetsOfOwner(id: Int): LiveData<List<Pet>> {
        return petsAndOwnersDatabase.getPetDao().getAllPetsOfOwner(id)
    }

    override fun getPetsOfOwnerAllOrMissing(id: Int, checkedState: Boolean): LiveData<List<Pet>> {
        return when {
            !checkedState -> {
                petsAndOwnersDatabase.getPetDao().getAllPetsOfOwner(id)
            }
            else -> {
                petsAndOwnersDatabase.getPetDao().getOnlyMissingPetsOfOwner(id)
            }
        }
    }

    override fun getNumberOfMissingPets(id: Int): LiveData<Int> {
        return petsAndOwnersDatabase.getPetDao().getNumberOfMissingPets(id)
    }


    override val status: LiveData<RepoStatus>
        get() = currentStatus;
}
