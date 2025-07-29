package com.example.chefmania.data

open class Piece (
    open var pos: Coordinate,
    //open val player: Player
    open var alive: Boolean = true
){


    open fun move(newPos: Coordinate, owner:Player){
        if(newPos.occupant != Occupancy.Vacant){
            var t = owner.opp?.pieces?.toMutableList()
            if (t != null) {
                for(i in 0 until t.size){
                    if(newPos.x == t[i].pos.x &&
                        newPos.y == t[i].pos.y){
                        t[i].die(owner.opp!!)
                        t.removeAt(i)
                        break
                    }
                }
            }
            owner.opp?.pieces = t!!
        }
        val temp: Occupancy = pos.occupant
        val temp2: Piece? = pos.piece
        pos.occupant = Occupancy.Vacant
        pos.piece = null
        pos = newPos
        pos.occupant = temp
        pos.piece = temp2
    }

    open fun die(owner: Player){
        alive = false
    }

    constructor(other: Piece, board:List<List<Coordinate>>) : this(board[other.pos.x][other.pos.y], other.alive){

    }

}