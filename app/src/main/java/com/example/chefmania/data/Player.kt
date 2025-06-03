package com.example.chefmania.data

data class Player (
    val name: String,
    var pieces: List<Piece>,
    var won: Boolean = false,
    val homeBase: Coordinate,
   // var opp: Player = Player(name = "", pieces = emptyList(), homeBase =  Coordinate(0,0)),
    var opp: Player? = null,
    var movesets: MutableList<MoveSet> = emptyList<MoveSet>().toMutableList(),
    var main: MainPiece = MainPiece(Coordinate(0,0))
){
}