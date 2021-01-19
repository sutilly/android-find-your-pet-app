package com.example.findmypet.dataAccess

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET

interface PetsAndOwnersWebService {
    @GET("owners-and-pets.json")
    fun getAllOwnersAndPets(): Call<List<OwnerWebEntity>>
}

fun buildWebService(): PetsAndOwnersWebService {
    val retrofit = Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/GithubGenericUsername/find-your-pet/master/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
    return retrofit.create(PetsAndOwnersWebService::class.java)
}