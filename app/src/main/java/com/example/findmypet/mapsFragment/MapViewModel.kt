package com.example.findmypet.mapsFragment

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.findmypet.database.Owner
import com.example.findmypet.database.Pet
import com.example.findmypet.dependencyInjection.Repository
import java.nio.file.Files.getOwner

class MapViewModel @ViewModelInject constructor(
        @Assisted private val savedStateHandle: SavedStateHandle,
        private val repository: Repository
): ViewModel() {
    val ownerId: Int = savedStateHandle.get<Int>("ownerId") ?: 0
    val currentOwner: LiveData<Owner> = getOwner()
    val pets: LiveData<List<Pet>> = repository.getPetsOfOwner(ownerId);


    private fun getOwner(): MutableLiveData<Owner> {
        val owner = repository.getOwnerById(ownerId)
        return MutableLiveData(owner)
    }
}
