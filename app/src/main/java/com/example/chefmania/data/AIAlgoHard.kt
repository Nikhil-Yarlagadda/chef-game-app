package com.example.chefmania.data

import com.example.chefmania.ui.GameUiState
import kotlin.math.absoluteValue

class AIAlgoHard(
    override val maxDepth: Int,
): AIAlgo(maxDepth) {

    override fun heuristic(state:GameUiState): Double{

        val comp: Player = state.players[1]
        val plyr: Player = state.players[0]

        val movesSet = (comp.movesets[0].moves + comp.movesets[1].moves).toSet()
        var movesAdv = movesSet.size * 0.015
        for (move in movesSet){
            if(move.y == 1){
                movesAdv += .005
            }
            if(move.y == -1){
                movesAdv -= 0.002
            }
        }

        var positionalAdv = 0.0
        var mainPieceSurroundment = 0.0
        for (piece in comp.pieces){
            if (piece is MainPiece){
                positionalAdv -= piece.pos.dist(plyr.homeBase) * 0.032
            }
            else {
                mainPieceSurroundment -= piece.pos.dist(comp.main.pos) * 0.006
                positionalAdv -= piece.pos.dist(plyr.homeBase) * 0.02
            }
        }
        for (piece in plyr.pieces){
            if (piece is MainPiece){
                positionalAdv += piece.pos.dist(comp.homeBase) * 0.032
            }
            else {
                mainPieceSurroundment += piece.pos.dist(plyr.main.pos) * 0.006
                positionalAdv += piece.pos.dist(comp.homeBase) * 0.02
            }
        }

        var pieceAdv: Double = ((comp.pieces.size - plyr.pieces.size).toDouble())
        if(pieceAdv.absoluteValue < 3){
            pieceAdv *= .3
        }
        else {
            pieceAdv *= .18
        }

        val totalAdv = pieceAdv + positionalAdv + movesAdv + mainPieceSurroundment
        if(totalAdv >= 1){
            return .99
        }
        if(totalAdv <= -1){
            return -.99
        }
        return totalAdv
    }

    override fun sortingHeuristic(state: GameUiState): Double {
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