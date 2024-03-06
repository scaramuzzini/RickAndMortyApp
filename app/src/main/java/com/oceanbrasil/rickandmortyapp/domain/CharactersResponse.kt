package com.oceanbrasil.rickandmortyapp.domain

data class CharactersResponse(
    val info: Info,
    val results: List<Character>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?
)