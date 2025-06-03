package com.example.chefmania.data

import com.example.chefmania.ui.GameUiState
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.max

class aiAlgo1 {
    val maxDepth: Int = 3


    fun succ(gameState:GameUiState): List<GameUiState>{
        val currentPlayer = gameState.turn
        val board = gameState.squares
        val succStates = emptyList<GameUiState>().toMutableList()
        if (currentPlayer != null) {
            for(i in 0..<currentPlayer.movesets.size){
                for(j in 0..<currentPlayer.pieces.size){
                    val possibleMoves = currentPlayer.movesets[i].possibleMoves(currentPlayer.pieces[j], currentPlayer, board)
                    for(k in possibleMoves){
                        var list: MutableList<MutableList<Coordinate>> = emptyList<MutableList<Coordinate>>().toMutableList()
                        for( ci in 0 until 5){
                            list.add(emptyList<Coordinate>().toMutableList())
                            for(cj in 0 until 5){
                                list.get(ci).add(Coordinate(ci,cj,gameState.squares[ci][cj].occupant))
                            }
                        }
                        val temp = gameState.copy(
                            players = listOf(gameState.players?.get(0)!!.copy(),
                                gameState.players[1].copy()),
                            squares = list)
                        if(gameState.turn == gameState.players[0]){
                            temp.turn = temp.players?.get(1)!!
                        }
                        else{
                            temp.turn = temp.players?.get(0)!!
                        }

                        temp.players[0].opp = temp.players[1]
                        temp.players[1].opp = temp.players[0]

                        val plyr = temp.turn.opp!!
                        plyr.pieces = currentPlayer.pieces.map {
                            if(it is MainPiece){
                                plyr.main =MainPiece(it,temp.squares)
                                plyr.main
                            }
                            else{
                                Piece(it, temp.squares)
                            }}
                        temp.turn.pieces = currentPlayer.opp?.pieces!!.map{
                            if(it is MainPiece){
                                MainPiece(it,temp.squares)
                            }
                            else{
                                Piece(it, temp.squares)
                            }}
                        plyr.movesets = listOf(currentPlayer.movesets[0], currentPlayer.movesets[1]).toMutableList()

                        /*
                        temp.squares[k.x][k.y].occupant = plyr.pieces[j].pos.occupant
                        plyr.pieces[j].pos.occupant = Occupancy.Vacant
                        plyr.pieces[j].pos = temp.squares[k.x][k.y]*/

                        val usedMove = plyr.movesets[i]
                        plyr.movesets[i] = temp.standby?: MoveSet(emptyList(), "")
                        temp.standby = usedMove

                        if(temp.squares[k.x][k.y].occupant != Occupancy.Vacant){
                            for(b in 0..<plyr.opp?.pieces?.size!!){
                                if(plyr.opp?.pieces?.get(b)?.pos == temp.squares[k.x][k.y]){
                                    plyr.opp?.pieces?.get(b)?.alive = false
                                    var mutabletemp = plyr.opp?.pieces?.toMutableList()
                                    if (mutabletemp != null) {
                                        mutabletemp.removeAt(b)
                                    }
                                    if (mutabletemp != null) {
                                        plyr.opp?.pieces = mutabletemp.toList()
                                    }
                                    break
                                }
                            }
                        }
                        
                        temp.squares[k.x][k.y].occupant = plyr.pieces[j].pos.occupant
                        plyr.pieces[j].pos.occupant = Occupancy.Vacant
                        plyr.pieces[j].pos = temp.squares[k.x][k.y]

                        succStates.add(temp)
                    }
                }
            }

        }
        return succStates
    }

    fun gameValue(plyr:Player): Int{
        if(!plyr.main.alive){
            return -1
        }
        if(plyr.main.pos == plyr.opp?.homeBase){
            return 1
        }
        if(!plyr.opp?.main?.alive!!){
            return 1
        }
        if(plyr.opp?.main?.pos == plyr.homeBase){
            return -1
        }

        return 0
    }

    fun heuristic(state:GameUiState): Int{
        return 0
    }

    fun maxValue(state:GameUiState, depth:Int, alpha:Int, beta:Int): Int{
        if(depth == maxDepth){
            return heuristic(state)
        }
        var max = alpha
        val states = succ(state)
        for(state in states){
            val value = minValue(state, depth+1, max, beta)
            if(value > max){
                max = value
            }
            if(max>= beta){
                return beta
            }
        }
        return max
    }

    fun minValue(state:GameUiState, depth:Int, alpha:Int, beta:Int): Int{
        if(depth == maxDepth){
            return heuristic(state)
        }
        var min = beta
        val states = succ(state)
        for(state in states){
            val value = maxValue(state, depth+1, alpha, min)
            if(value < min){
                min = value
            }
            if(alpha >= min){
                return alpha
            }
        }
        return min
    }

    fun makeMove(state:GameUiState):GameUiState{
        var max = -2;
        var maxS: GameUiState = state
        var succ = succ(state)
        for(s in succ){
            val m = maxValue(s,0,-2,2)
            if(m > max){
                max = m
                maxS = s
            }
        }
        return maxS
    }
}

fun main(){
    val movesets: List<MoveSet> = MoveSet.staticSelect5()
    println(movesets[0].name)
    println(movesets[1].name)
    var state = GameUiState()

    val p1: Player = Player(name = "p1",
        pieces = listOf(
            Piece(state.squares[0][0]),
            Piece(state.squares[1][0]),
            MainPiece(state.squares[2][0]),
            Piece(state.squares[2][3]),
            Piece(state.squares[3][2]),
        ),
        homeBase = Coordinate(2,0),
        movesets = listOf(movesets[0],movesets[1]).toMutableList()
    )
    for(i in 0 until 3){
        state.squares[i][0].occupant = Occupancy.Plyr
    }
    state.squares[2][3].occupant = Occupancy.Plyr
    state.squares[3][2].occupant = Occupancy.Plyr

    val comp: Player = Player(name = "comp",
        pieces = listOf(
            Piece(state.squares[0][4]),
            Piece(state.squares[1][4]),
            MainPiece(state.squares[2][4]),
            Piece(state.squares[3][4]),
            Piece(state.squares[4][4])
        ),
        homeBase = Coordinate(2,4),
        movesets = listOf(movesets[2],movesets[3]).toMutableList()
    )
    for(i in 0 until 5){
        state.squares[i][4].occupant = Occupancy.Comp
    }
    p1.opp = comp
    comp.opp = p1

    state = GameUiState(
        movesets = movesets,
        players = listOf(p1,comp),
        turn = p1,
        standby = movesets[4],
        squares = state.squares
    )
    var algo = aiAlgo1()
    var list = algo.succ(state)
    for(s in list){
        println()
        for(i in 4 downTo 0){
            for(j in 0 .. 4){
                print(s.squares[j][i])
                print("   ")
            }
            println()
        }
        print(s.standby?.name)
    }

}