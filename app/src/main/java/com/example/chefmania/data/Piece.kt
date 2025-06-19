package com.example.chefmania.data

open class Piece (
    open var pos: Coordinate,
    //open val player: Player
){
    open var alive: Boolean = true

    open fun move(newPos: Coordinate, owner:Player){
        val temp: Occupancy = pos.occupant
        val temp2: Piece? = pos.piece
        pos.occupant = Occupancy.Vacant
        pos.piece = null
        pos = newPos
        pos.occupant = temp
        pos.piece = temp2
    }

    open fun die(){
        alive = false
    }

    constructor(other: Piece, board:List<List<Coordinate>>) : this(board[other.pos.x][other.pos.y])
}