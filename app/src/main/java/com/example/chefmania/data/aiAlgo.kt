package com.example.chefmania.data

import com.example.chefmania.ui.GameUiState

open class AIAlgo(
    open val maxDepth: Int = 3,
) {



    fun succ(gameState:GameUiState): List<GameUiState>{
        if(gameValue(gameState) != 0){
            return emptyList()
        }
        val currentPlayer = gameState.turn
        val board = gameState.squares
        val succStates = emptyList<GameUiState>().toMutableList()
        if (currentPlayer != null) {
            for(i in 0..<currentPlayer.movesets.size){
                for(j in 0..<currentPlayer.pieces.size){
                    val possibleMoves = currentPlayer.movesets[i].possibleMoves(currentPlayer.pieces[j], currentPlayer, board)
                    for(k in possibleMoves){
                        val list: MutableList<MutableList<Coordinate>> = emptyList<MutableList<Coordinate>>().toMutableList()
                        for( ci in 0 until 5){
                            list.add(emptyList<Coordinate>().toMutableList())
                            for(cj in 0 until 5){
                                list.get(ci).add(Coordinate(ci,cj,gameState.squares[ci][cj].occupant))
                            }
                        }
                        val temp = gameState.copy(
                            players = listOf(gameState.players[0].copy(),
                                gameState.players[1].copy()),
                            squares = list)
                        if(gameState.turn == gameState.players[0]){
                            temp.turn = temp.players[1]
                        }
                        else{
                            temp.turn = temp.players[0]
                        }


                        temp.players[0].opp = temp.players[1]
                        temp.players[1].opp = temp.players[0]

                        val plyr = temp.turn.opp!!
                        temp.currentSelectedPiece = plyr.pieces[j]
                        temp.highlights = listOf(plyr.pieces[j].pos)
                        temp.newMovesetIndex = i
                        var mainFound = false
                        val oldMain = plyr.main
                        plyr.pieces = currentPlayer.pieces.map {
                            if(it is MainPiece){
                                plyr.main =MainPiece(it,temp.squares)
                                temp.squares[it.pos.x][it.pos.y].piece = plyr.main
                                mainFound = true
                                plyr.main
                            }
                            else{
                                val t = Piece(it, temp.squares)
                                temp.squares[it.pos.x][it.pos.y].piece = t
                                t
                            }}
                        if(oldMain == plyr.main){
                            print("Player.main hasn't changed")
                        }
                        if(!mainFound){
                            plyr.main.alive = false
                        }

                        mainFound = false
                        temp.turn.pieces = currentPlayer.opp?.pieces!!.map{
                            if(it is MainPiece){
                                val t = MainPiece(it,temp.squares)
                                temp.squares[it.pos.x][it.pos.y].piece = t
                                temp.turn.main = t
                                mainFound = true
                                t
                            }
                            else{
                                val t = Piece(it, temp.squares)
                                temp.squares[it.pos.x][it.pos.y].piece = t
                                t
                            }}
                        if(!mainFound){
                            temp.turn.main.alive = false
                        }

                        plyr.movesets = listOf(currentPlayer.movesets[0], currentPlayer.movesets[1]).toMutableList()


                        val usedMove = plyr.movesets[i]
                        plyr.movesets[i] = temp.standby?: MoveSet(emptyList(), "",0,0)
                        temp.standby = usedMove

                        if(temp.squares[k.x][k.y].occupant != Occupancy.Vacant){
                            for(b in 0 until plyr.opp?.pieces?.size!!){
                                if(plyr.opp?.pieces?.get(b)?.pos == temp.squares[k.x][k.y]){
                                    plyr.opp?.pieces?.get(b)?.alive = false
                                    var mutabletemp = plyr.opp?.pieces?.toMutableList()
                                    mutabletemp?.removeAt(b)
                                    if (mutabletemp != null) {
                                        plyr.opp?.pieces = mutabletemp.toList()
                                    }
                                    break
                                }
                            }
                            /*
                            val deadPiece = temp.squares[k.x][k.y].piece
                            deadPiece?.alive = false
                            if(deadPiece is MainPiece){
                                plyr.opp?.main?.alive = false
                            }
                            var mutabletemp = plyr.opp?.pieces?.toMutableList()
                            mutabletemp?.remove(deadPiece)
                            plyr.opp!!.pieces = mutabletemp!!*/

                        }

                        temp.squares[k.x][k.y].occupant = plyr.pieces[j].pos.occupant
                        temp.squares[k.x][k.y].piece = plyr.pieces[j]
                        plyr.pieces[j].pos.occupant = Occupancy.Vacant
                        plyr.pieces[j].pos.piece = null
                        plyr.pieces[j].pos = temp.squares[k.x][k.y]


                        succStates.add(temp)
                    }
                }
            }

        }

        return succStates
    }

    fun gameValue(state: GameUiState): Int{
        val comp: Player = state.players[1]
        val plyr: Player = state.players[0]
        if(!comp.main.alive){
            //print(comp.main.alive)
            return -1
        }
        if(comp.main.pos == plyr.homeBase){
            return 1
        }
        if(!plyr.main.alive){
            return 1
        }
        if(plyr.main.pos == comp.homeBase){
            return -1
        }
        return 0
    }

    open fun heuristic(state:GameUiState): Double{
        return 0.0
        //function to be implemented in inherited classes
    }

    open fun sortingHeuristic(state:GameUiState): Double{
        return heuristic(state)
        //function to be implemented in inherited classes
    }

    private fun scoreTerminal(state: GameUiState, depth: Int): Double {
        return when (gameValue(state)) {
            1 -> 1.0 - depth * 0.0001     // win (sooner is better)
            -1 -> -1.0 + depth * 0.0001   // loss (later is better)
            else -> throw IllegalStateException("Invalid game result")
        }
    }

    fun maxValue(state:GameUiState, depth:Int, alpha:Double, beta:Double): Double{
        if(gameValue(state) != 0){
            return scoreTerminal(state, depth)
        }
        if(depth == maxDepth){
            return heuristic(state)
        }
        var max = alpha
        val states = succ(state).sortedByDescending { sortingHeuristic(it) }
        for(nextState in states){
            val value = minValue(nextState, depth+1, max, beta)
            if(value > max){
                max = value
            }
            if(max>= beta){
                return beta
            }
        }
        return max
    }

    fun minValue(state:GameUiState, depth:Int, alpha:Double, beta:Double): Double{
        if(gameValue(state) != 0){
            return scoreTerminal(state, depth)
        }
        if(depth == maxDepth){
            return heuristic(state)
        }
        var min = beta
        val states = succ(state).sortedBy{ sortingHeuristic(it)}
        for(nextState in states){
            val value = maxValue(nextState, depth+1, alpha, min)
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
        if (gameValue(state) != 0){
            return state
        }
        var max = -2.0;
        var maxS: GameUiState = state
        val succ = succ(state).sortedByDescending { sortingHeuristic(it) }
        for(s in succ){
            val m = minValue(s,0,-2.0,2.0)
            if(m > max){
                max = m
                maxS = s
            }
        }
        if(max == 1.0){
            maxS = maxS.copy(winner = maxS.players[1])
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
    var algo = AIAlgo()
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