package com.example.chefmania

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.chefmania.data.gameStateDatabase
import com.example.chefmania.ui.GameUiState
import com.example.chefmania.ui.GameViewModel
import com.example.chefmania.ui.theme.ChefManiaTheme
import kotlinx.coroutines.launch
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.chefmania.data.Coordinate
import com.example.chefmania.data.GameStateDao
import com.example.chefmania.data.GameStateEntity
import com.example.chefmania.data.MainPiece
import com.example.chefmania.data.MoveSet
import com.example.chefmania.data.Occupancy
import com.example.chefmania.data.Piece
import com.example.chefmania.data.Player

class MainActivity : ComponentActivity() {
    var state: GameUiState = GameUiState()
    lateinit var db: gameStateDatabase
    lateinit var dao: GameStateDao
    lateinit var viewModel: GameViewModel

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        db = Room.databaseBuilder(this, gameStateDatabase::class.java, "game.db").build()
        dao = db.gameStateDao()
        enableEdgeToEdge()
        setContent {
            ChefManiaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    chefmania({ finish() },viewModel,modifier = Modifier.padding(0.dp))
                }
            }
        }
    }

    override fun onStart(){
        super.onStart()
        lifecycleScope.launch {
            val entity = dao.get()
            if (entity?.ongoing == 1) {
                val pPieces: MutableList<Piece> = emptyList<Piece>().toMutableList()
                val cPieces: MutableList<Piece> = emptyList<Piece>().toMutableList()
                var pMain: MainPiece = MainPiece(Coordinate(0, 0))
                var cMain: MainPiece = MainPiece(Coordinate(4, 4))

                var moves = emptyList<MoveSet>().toMutableList()
                for (move in entity!!.moves.split(",")) {
                    for (card in MoveSet.movesets) {
                        if (move == card.name) {
                            moves.add(card)
                            break
                        }
                    }
                }

                var i = 0
                var j = 0
                while (i * 5 + j < 25) {
                    val value = entity?.board?.get(i * 5 + j)?.digitToInt()
                    if (value != 0) {
                        if (value in 1..2) {
                            state.squares[i][j].occupant = Occupancy.Plyr
                            if (value == 1) {
                                state.squares[i][j].piece = Piece(state.squares[i][j])
                                pPieces.add(state.squares[i][j].piece!!)
                            }
                            if (value == 2) {
                                state.squares[i][j].piece = MainPiece(state.squares[i][j])
                                pMain = (state.squares[i][j].piece as MainPiece?)!!
                                pPieces.add(state.squares[i][j].piece!!)
                            }
                        } else {
                            state.squares[i][j].occupant = Occupancy.Comp
                            if (value == 3) {
                                state.squares[i][j].piece = Piece(state.squares[i][j])
                                cPieces.add(state.squares[i][j].piece!!)
                            }
                            if (value == 4) {
                                state.squares[i][j].piece = MainPiece(state.squares[i][j])
                                cMain = (state.squares[i][j].piece as MainPiece?)!!
                                cPieces.add(state.squares[i][j].piece!!)
                            }
                        }
                    }
                    j += 1
                    if (j % 5 == 0) {
                        i += 1
                        j = 0
                    }
                }

                var player = Player(
                    name = "p1", pieces = pPieces,
                    homeBase = Coordinate(2, 0), movesets = moves.subList(0, 2), main = pMain
                )

                var comp = Player(
                    name = "comp", pieces = cPieces,
                    homeBase = Coordinate(2, 4), movesets = moves.subList(2, 4), main = cMain
                )
                player.opp = comp
                comp.opp = player
                var players = listOf(player, comp)
                state = state.copy(
                    ai = entity.ai, AIRunning = entity.turn == 1, turn = players[entity.turn],
                    movesets = moves, players = players, standby = moves[4], gameOnGoing = true
                )
                viewModel.updateState(state)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch {
            var entity: GameStateEntity
            val state = viewModel.uiState.value
            if(state.gameOnGoing) {
                var board: String = ""
                for (i in 0 until 5) {
                    for (j in 0 until 5) {
                        if (state.squares[i][j].occupant == Occupancy.Vacant) {
                            board += "0"
                        } else if (state.squares[i][j].occupant == Occupancy.Plyr) {
                            if (state.squares[i][j].piece is MainPiece) {
                                board += "2"
                            } else {
                                board += "1"
                            }
                        } else {
                            if (state.squares[i][j].piece is MainPiece) {
                                board += "4"
                            } else {
                                board += "3"
                            }
                        }
                    }
                }

                var moves: String = ""
                for (move in state.movesets) {
                    moves += move.name + ","
                }
                moves = moves.dropLast(1)

                entity = GameStateEntity(
                    ongoing = 1, board = board, turn = if (state.turn == state.players[1]) 1 else 0,
                    moves = moves, ai = state.ai
                )

                dao.save(entity)
            }
            else{
                dao.save(GameStateEntity(0,0,"",0,"",0))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChefManiaTheme {
        Scaffold{
            chefmania({})
        }
    }
}