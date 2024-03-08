package com.oceanbrasil.rickandmortyapp.domain

data class Info(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?
)