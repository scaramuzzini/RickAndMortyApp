package com.oceanbrasil.rickandmortyapp.domain

data class CharactersResponse(
    val info: Info,
    val results: List<Character>
)

