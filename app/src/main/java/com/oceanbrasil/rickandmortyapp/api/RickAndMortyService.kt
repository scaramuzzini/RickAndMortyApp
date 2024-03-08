package com.oceanbrasil.rickandmortyapp.api

import com.oceanbrasil.rickandmortyapp.domain.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("api/character")
    fun getAllCharacters(@Query("page") page: Int): Call<CharactersResponse>
}