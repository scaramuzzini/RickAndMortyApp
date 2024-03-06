package com.oceanbrasil.rickandmortyapp.api

import com.oceanbrasil.rickandmortyapp.domain.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyService {
    @GET("api/character")
    fun getAllCharacters(): Call<CharactersResponse>
}