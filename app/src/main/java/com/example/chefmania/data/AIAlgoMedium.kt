package com.example.chefmania.data

import com.example.chefmania.ui.GameUiState

class AIAlgoMedium(
    override val maxDepth: Int,
): AIAlgo(maxDepth) {

    override fun heuristic(state:GameUiState): Double{
//        val gameOver = gameValue(state)
//        if(gameOver != 0){
//            return gameOver.toDouble()
//        }

        val comp: Player = state.players[1]
        val plyr: Player = state.players[0]

        var positionalAdv = 0.0
        for (piece in comp.pieces){
            if (piece is MainPiece){
                positionalAdv -= piece.pos.dist(plyr.homeBase) * 0.032
            }
            positionalAdv -= piece.pos.dist(plyr.homeBase) * 0.02

        }
        for (piece in plyr.pieces){
            if (piece is MainPiece){
                positionalAdv += piece.pos.dist(plyr.homeBase) * 0.032
            }
            positionalAdv += piece.pos.dist(comp.homeBase) * 0.02
        }

        val pieceAdv = (comp.pieces.size - plyr.pieces.size) * .18


        val totalAdv = pieceAdv + positionalAdv + ((-5 .. 5).random() * .001)
        if(totalAdv >= 1){
            return .99
        }
        if(totalAdv <= -1){
            return -.99
        }
        return totalAdv
    }
}