package com.example.chefmania.data

class MainPiece(
    override var pos: Coordinate,
    //override val player: Player
) : Piece(pos){

    override fun move(newPos: Coordinate, owner:Player) {
        super.move(newPos, owner)
        //check if new pos is opponent's home base
        val oppHome: Coordinate? = owner.opp?.homeBase
        if (oppHome != null) {
            if(pos.x == oppHome.x && pos.y == oppHome.y){
                //trigger win the game for owner
            }

        }
    }

    fun die(owner: Player) {
        super.die()
        //trigger lose the game for owner
    }

    constructor(other: MainPiece, board:List<List<Coordinate>>) : this(board[other.pos.x][other.pos.y])
}