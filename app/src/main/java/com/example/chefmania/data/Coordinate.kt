package com.example.chefmania.data

import kotlin.math.abs

data class Coordinate(
    var x: Int,
    var y: Int,
    var occupant: Occupancy = Occupancy.Vacant,
    var piece: Piece? = null
){
    fun add(other: Coordinate): Coordinate {
        return Coordinate(x+other.x, y+other.y)
    }
    fun sub(other: Coordinate): Coordinate {
        return Coordinate(x-other.x, y-other.y)
    }
    fun dist(other: Coordinate): Int{
        return abs(x- other.x) + abs(y - other.y)
    }
}
