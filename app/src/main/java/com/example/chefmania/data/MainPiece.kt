package com.example.chefmania.data

class MainPiece(
    override var pos: Coordinate,
    //override val player: Player
    override var alive: Boolean = true
) : Piece(pos){

    override fun move(newPos: Coordinate, owner:Player) {
        super.move(newPos, owner)
        //check if new pos is opponent's home base
        val oppHome: Coordinate? = owner.opp?.homeBase
        if (oppHome != null) {
            if(pos.x == oppHome.x && pos.y == oppHome.y){
                //trigger win the game for owner
                owner.won = true
            }

        }
    }

    override fun die(owner: Player) {
        super.die(owner)
        //trigger lose the game for owner
        owner.opp?.won = true
        owner.main.alive = false
    }

    constructor(other: MainPiece, board:List<List<Coordinate>>) : this(board[other.pos.x][other.pos.y], other.alive)
}