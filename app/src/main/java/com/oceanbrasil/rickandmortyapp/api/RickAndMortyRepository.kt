package com.oceanbrasil.rickandmortyapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RickAndMortyRepository {

    val instance: RickAndMortyService by lazy {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(RickAndMortyService::class.java)
    }

}