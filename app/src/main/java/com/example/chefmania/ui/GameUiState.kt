package com.example.chefmania.ui

import android.text.Highlights
import com.example.chefmania.data.*

data class GameUiState(
    var gameOnGoing: Boolean = false,
    var ai: Int = 0,
    var AIRunning: Boolean = false,
    var currentSelectedPiece: Piece? = null,
    var currentSelectedMovSet: MoveSet? = null,
    var highlights: List<Coordinate>? = null,
    var turn: Player = Player(name = "", pieces = emptyList(), homeBase =  Coordinate(0,0)),
    val movesets: List<MoveSet> = emptyList(),
    var newMovesetIndex: Int = 4,
    val players: List<Player> = emptyList(),
    var winner: Player? = null,
    var standby: MoveSet? = null,
    var moves: List<Move> = emptyList(),
    val squares: List<List<Coordinate>> = listOf(
        listOf(Coordinate(0,0),
            Coordinate(0,1),
            Coordinate(0,2),
            Coordinate(0,3),
            Coordinate(0,4)),
        listOf(Coordinate(1,0),
            Coordinate(1,1),
            Coordinate(1,2),
            Coordinate(1,3),
            Coordinate(1,4)),
        listOf(Coordinate(2,0),
            Coordinate(2,1),
            Coordinate(2,2),
            Coordinate(2,3),
            Coordinate(2,4)),
        listOf(Coordinate(3,0),
            Coordinate(3,1),
            Coordinate(3,2),
            Coordinate(3,3),
            Coordinate(3,4)),
        listOf(Coordinate(4,0),
            Coordinate(4,1),
            Coordinate(4,2),
            Coordinate(4,3),
            Coordinate(4,4)),
    )
)
