package com.example.findmypet.mainFragment

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.findmypet.database.Owner
import com.example.findmypet.dependencyInjection.Repository

class MainViewModel @ViewModelInject constructor(
    private val repository: Repository
): ViewModel() {

    val allOwnersLiveData: LiveData<List<Owner>> = repository.getAllOwners()
    val repoStatus = repository.status;

    fun onRefreshData() {
        repository.loadOwners()
    }
}
