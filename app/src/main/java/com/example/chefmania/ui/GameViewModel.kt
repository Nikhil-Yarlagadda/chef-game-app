package com.example.chefmania.ui

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.example.chefmania.data.AIAlgo
import com.example.chefmania.data.AIAlgoEasy
import com.example.chefmania.data.AIAlgoHard
import com.example.chefmania.data.Coordinate
import com.example.chefmania.data.MainPiece
import com.example.chefmania.data.MoveSet
import com.example.chefmania.data.Occupancy
import com.example.chefmania.data.Piece
import com.example.chefmania.data.Player
import com.example.chefmania.data.AIAlgoMedium
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState:StateFlow<GameUiState> = _uiState.asStateFlow()

    init{
       //resetGame()
    }
    fun updateState(state: GameUiState){
        _uiState.update {
            state
        }
    }

    fun resetGame(diff: Int){
        _uiState.update {
            GameUiState(gameOnGoing = true)
        }

        val movesets: List<MoveSet> = MoveSet.staticSelect5()

        val p1: Player = Player(name = "p1",
            pieces = listOf(
                Piece(_uiState.value.squares[0][0]),
                Piece(_uiState.value.squares[1][0]),
                MainPiece(_uiState.value.squares[2][0]),
                Piece(_uiState.value.squares[3][0]),
                Piece(_uiState.value.squares[4][0]),
            ),
            homeBase = _uiState.value.squares[2][0],
            movesets = listOf(movesets[0],movesets[1]).toMutableList()
        )
        for(i in 0 until 5){
            _uiState.value.squares[i][0].occupant = Occupancy.Plyr
            _uiState.value.squares[i][0].piece = p1.pieces[i]
        }

        val comp: Player = Player(name = "comp",
            pieces = listOf(
                Piece(_uiState.value.squares[0][4]),
                Piece(_uiState.value.squares[1][4]),
                MainPiece(_uiState.value.squares[2][4]),
                Piece(_uiState.value.squares[3][4]),
                Piece(_uiState.value.squares[4][4])
            ),
            homeBase = _uiState.value.squares[2][4],
            movesets = listOf(movesets[2],movesets[3]).toMutableList()
        )
        for(i in 0 until 5){
            _uiState.value.squares[i][4].occupant = Occupancy.Comp
            _uiState.value.squares[i][4].piece = comp.pieces[i]
        }
        p1.opp = comp
        comp.opp = p1

        _uiState.update {
            gameUiState ->
            GameUiState(
                gameOnGoing = true,
                movesets = movesets,
                players = listOf(p1,comp),
                turn = p1,
                standby = movesets[4],
                ai = diff,
                squares = gameUiState.squares
            )
        }
    }

    fun selectPiece(piece:Piece?){
        _uiState.update{
            GameUiState -> GameUiState.copy(currentSelectedPiece = piece)
        }
        if(_uiState.value.currentSelectedMovSet != null){
            highlightMoves()
        }
    }

    fun selectMoveset(moveset:MoveSet){
        _uiState.update{
                GameUiState -> GameUiState.copy(currentSelectedMovSet = moveset)
        }
        if(_uiState.value.currentSelectedPiece != null){
            highlightMoves()
        }
    }

    fun highlightMoves(){
        _uiState.update {
            GameUiState ->
            GameUiState.copy(highlights = GameUiState.currentSelectedPiece?.let {
                GameUiState.currentSelectedMovSet?.possibleMoves(
                    piece = it,
                    player = GameUiState.turn,
                    board = GameUiState.squares
                )
            })
        }
    }

    fun move(piece: Piece, cord: Coordinate, plyr: Player){
        piece.move(cord, plyr)
        _uiState.update {
            GameUiState ->
            if(GameUiState.currentSelectedMovSet?.name == GameUiState.players[0].movesets[0].name){
                GameUiState.players[0].movesets[0] = GameUiState.standby!!
            }
            else{
                GameUiState.players[0].movesets[1] = GameUiState.standby!!
            }
            GameUiState.turn.opp?.let { GameUiState.copy(
                AIRunning = true,
                currentSelectedPiece = null,
                currentSelectedMovSet = null,
                highlights = null,
                turn = it,
                standby = GameUiState.currentSelectedMovSet,
                winner = if(plyr.won) plyr else GameUiState.winner,
            ) }!!


        }
        println(_uiState.value.players[0].pieces.size)
        println(_uiState.value.players[1].pieces.size)
    }

    fun compMove(){
        val algo: AIAlgo
        if (_uiState.value.ai == 0){
            algo = AIAlgoEasy(3)
        }
        else if (_uiState.value.ai == 1){
            algo = AIAlgoMedium(5)
        }
        else{
            algo = AIAlgoHard(5)
        }

        viewModelScope.launch {
            val bestMove = withContext(Dispatchers.Default) {
                algo.makeMove(_uiState.value)
            }.copy(AIRunning = false)

            _uiState.update { bestMove }
        }
    }

}