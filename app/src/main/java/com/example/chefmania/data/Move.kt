package com.example.chefmania.data

data class Move(
    val player: Int,
    val from: Coordinate,
    val to: Coordinate,
    var newCard: Int
)
