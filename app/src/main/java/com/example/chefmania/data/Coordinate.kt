package com.example.chefmania.data

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
}
