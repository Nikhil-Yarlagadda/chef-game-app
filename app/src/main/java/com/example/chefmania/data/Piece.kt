package com.example.chefmania.data

open class Piece (
    open var pos: Coordinate,
    //open val player: Player
){
    open var alive: Boolean = true

    open fun move(newPos: Coordinate, owner:Player){
        val temp: Occupancy = pos.occupant
        pos.occupant = Occupancy.Vacant
        pos = newPos
        pos.occupant = temp
    }

    open fun die(){
        alive = false
    }

    constructor(other: Piece, board:List<List<Coordinate>>) : this(board[other.pos.x][other.pos.y])
}