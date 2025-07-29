package com.example.chefmania.data

import com.example.chefmania.ui.GameUiState

class AIAlgoEasy(
    override val maxDepth: Int,
): AIAlgo(maxDepth) {

    override inline fun heuristic(state:GameUiState): Double{
        val gameOver = gameValue(state)
        val comp: Player
        val plyr: Player
        if(gameOver != 0){
            return gameOver.toDouble()
        }

        if(state.turn.homeBase.y == 4){
            comp = state.turn
            plyr = comp.opp!!
        }else{
            plyr = state.turn
            comp = plyr.opp!!
        }

        return  ((comp.pieces.size - plyr.pieces.size) * .19 + ((-10 .. 10).random() * .05)).coerceIn(-.99, .99)
    }
}