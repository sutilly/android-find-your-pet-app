package com.example.findmypet.ownerDetailFragment

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.findmypet.database.Owner
import com.example.findmypet.dependencyInjection.Repository

class OwnerDetailViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val repository: Repository
) : ViewModel() {
    val ownerId: Int = savedStateHandle.get<Int>("ownerId") ?: 0
    val currentOwner: MutableLiveData<Owner> = getOwner()
    private val showOnlyMissingPets = MutableLiveData(false)
    val missingPets: LiveData<Int> = getNumberOfMissingPets()

    val pets = Transformations.switchMap(showOnlyMissingPets) {
        repository.getPetsOfOwnerAllOrMissing(ownerId, it)
    }

    private fun getOwner(): MutableLiveData<Owner> {
        val owner = repository.getOwnerById(ownerId)
        return MutableLiveData(owner)
    }

    private fun getNumberOfMissingPets(): LiveData<Int>{
        return repository.getNumberOfMissingPets(ownerId)
    }

    fun onCheckedChange(switchState: Boolean) {
        showOnlyMissingPets.value = switchState
    }
}