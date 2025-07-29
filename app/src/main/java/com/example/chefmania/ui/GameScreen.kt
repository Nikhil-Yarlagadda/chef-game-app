package com.example.chefmania.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefmania.R
import com.example.chefmania.data.Coordinate
import com.example.chefmania.data.MainPiece
import com.example.chefmania.data.Occupancy
import kotlinx.coroutines.delay

class GameScreen {

    @Composable
    fun gameScreen(viewModel: GameViewModel, gameOver: ()->Unit) {
        val uiState by viewModel.uiState.collectAsState()
        println(uiState.players[0].pieces.size)
        println(uiState.players[1].pieces.size)
        println(uiState.players[1].main.alive)
        val enabled = uiState.turn == uiState.players[0]
        if(uiState.winner!=null){
            gameOver()
        }
        else if(uiState.AIRunning){
            viewModel.compMove()
        }
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xFFD5BDB6))
        ) {
            Column(modifier = Modifier.matchParentSize()) {

                //opponent cards
                Row(
                    Modifier.padding(6.dp, 3.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.size(185.dp, 100.dp).graphicsLayer {
                            rotationZ = 180f
                        }
                    ) {
                        Row(Modifier.weight(1.0f)) {
                            val move = uiState.players[1].movesets[0]
                            Column(Modifier.weight(1.0f)) {

                                Text(
                                    text = move.name,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.padding(4.dp, 0.dp)
                                )
                                Image(
                                    painter = painterResource(move.resId),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(66.dp)
                                )
                            }
                            Column(Modifier.weight(1.2f)) {
                                Image(
                                    painter = painterResource(move.resBoardId),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.weight(1.0f)
                                )
                            }
                        }

                    }

                    Spacer(Modifier.width(18.dp))

                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
                        modifier = Modifier.size(185.dp, 100.dp).graphicsLayer {
                            rotationZ = 180f
                        }
                    ) {
                        Row() {
                            val move = uiState.players[1].movesets[1]
                            Column(modifier = Modifier.weight(1.0f)) {

                                Text(
                                    text = move.name,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.padding(4.dp, 0.dp)
                                )
                                Image(
                                    painter = painterResource(move.resId),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(66.dp)
                                )
                            }
                            Column(Modifier.weight(1.2f)) {
                                Image(
                                    painter = painterResource(move.resBoardId),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.weight(1.0f)
                                )
                            }
                        }

                    }
                }

                if(!uiState.AIRunning){
                    Spacer(modifier = Modifier.height(190.dp))
                }
                else {
                    Spacer(modifier = Modifier.height(30.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth().height(88.dp).graphicsLayer {
                            rotationZ = 180f
                        },
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "In Rotation",
                            color = MaterialTheme.colorScheme.onTertiary,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp),
                            modifier = Modifier.padding(4.dp, 0.dp)
                        )
                        Card(
                            colors = CardDefaults.cardColors()
                                .copy(containerColor = Color(0xffe0d8bb)),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
                            modifier = Modifier.size(135.dp, 80.dp).clickable(
                                onClick = { println(uiState.squares) }
                            )
                        ) {
                            Row(Modifier.weight(1.0f)) {
                                val move = uiState.standby
                                Column(Modifier.weight(1.0f)) {

                                    Text(
                                        text = move?.name!!,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                                        modifier = Modifier.padding(4.dp, 1.dp)
                                    )
                                    Image(
                                        painter = painterResource(move.resId),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(44.dp)
                                    )
                                }
                                Column(Modifier.weight(1.3f)) {
                                    Image(
                                        painter = painterResource(move?.resBoardId!!),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier.weight(1.0f).padding(1.dp)
                                    )
                                }
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(72.dp))
                }

                //board
                var checkered = true
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                   Card(shape = RectangleShape,border = BorderStroke(3.dp, Color.DarkGray), colors = CardDefaults.cardColors().copy(containerColor = Color.DarkGray)) {
                       for (i in 4 downTo 0) {
                           Row() {
                               for (j in 0 until 5) {
                                   var possible = false
                                   Card(shape = RectangleShape,
                                       border = if(j == 2 && i == 0) {BorderStroke(2.dp,Color.Blue )}
                                       else if(j == 2 && i == 4){
                                           BorderStroke(2.dp, Color.Red)}
                                       else{BorderStroke(0.dp, Color.White)},
                                       colors = CardDefaults.cardColors
                                           (
                                           containerColor =
                                           if (uiState.currentSelectedPiece == uiState.squares[j][i].piece && uiState.currentSelectedPiece != null)
                                               MaterialTheme.colorScheme.primary
                                           else if (uiState.highlights?.contains(
                                                   Coordinate(
                                                       j,
                                                       i,
                                                       Occupancy.Vacant,
                                                       null
                                                   )
                                               ) == true
                                           ) {
                                               possible = true
                                               Color.Green
                                           } else if (checkered) Color.White
                                           else Color.Black
                                       ),
                                       modifier = Modifier.size(64.dp).clickable(
                                           onClick = {
                                               if(enabled) {
                                                   if (possible) {
                                                       uiState.currentSelectedPiece?.let {
                                                           viewModel.move(
                                                               it,
                                                               uiState.squares[j][i],
                                                               uiState.turn
                                                           )
                                                       }
                                                   } else if (uiState.squares[j][i].occupant == Occupancy.Plyr) {
                                                       viewModel.selectPiece(uiState.squares[j][i].piece)
                                                   }
                                               }
                                           }
                                       )
                                   ) {
                                       checkered = !checkered
                                       Box(Modifier.fillMaxSize()) {
                                           if (uiState.squares[j][i].occupant == Occupancy.Plyr) {
                                               if (uiState.squares[j][i].piece is MainPiece) {
                                                   Image(
                                                       painter = painterResource(R.drawable.bluemasterchef_cropped),
                                                       contentDescription = null,
                                                       contentScale = ContentScale.Fit,
                                                       modifier = Modifier.size(48.dp)
                                                           .align(Alignment.Center)
                                                   )
                                               } else {
                                                   Image(
                                                       painter = painterResource(R.drawable.studentblue_cropped),
                                                       contentDescription = null,
                                                       contentScale = ContentScale.Fit,
                                                       modifier = Modifier.size(36.dp)
                                                           .align(Alignment.Center)
                                                   )
                                               }
                                           } else if (uiState.squares[j][i].occupant == Occupancy.Comp) {
                                               if (uiState.squares[j][i].piece is MainPiece) {
                                                   Image(
                                                       painter = painterResource(R.drawable.redmasterchef_cropped),
                                                       contentDescription = null,
                                                       contentScale = ContentScale.Fit,
                                                       modifier = Modifier.size(48.dp)
                                                           .align(Alignment.Center)
                                                   )
                                               } else {
                                                   Image(
                                                       painter = painterResource(R.drawable.studentred_cropped),
                                                       contentDescription = null,
                                                       contentScale = ContentScale.Fit,
                                                       modifier = Modifier.size(36.dp)
                                                           .align(Alignment.Center)
                                                   )
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
                }

                if(uiState.AIRunning){
                    Spacer(modifier = Modifier.height(190.dp))
                }
                else {
                    Spacer(modifier = Modifier.height(30.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth().height(88.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "In Rotation",
                            color = MaterialTheme.colorScheme.onTertiary,
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 14.sp),
                            modifier = Modifier.padding(4.dp, 0.dp)
                        )
                        Card(
                            colors = CardDefaults.cardColors()
                                .copy(containerColor = Color(0xffe0d8bb)),
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
                            modifier = Modifier.size(135.dp, 80.dp).clickable(
                                onClick = { println(uiState.squares) }
                            )
                        ) {
                            Row(Modifier.weight(1.0f)) {
                                val move = uiState.standby
                                Column(Modifier.weight(1.0f)) {

                                    Text(
                                        text = move?.name!!,
                                        color = MaterialTheme.colorScheme.tertiary,
                                        style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                                        modifier = Modifier.padding(4.dp, 1.dp)
                                    )
                                    Image(
                                        painter = painterResource(move.resId),
                                        contentDescription = null,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier.size(44.dp)
                                    )
                                }
                                Column(Modifier.weight(1.3f)) {
                                    Image(
                                        painter = painterResource(move?.resBoardId!!),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillHeight,
                                        modifier = Modifier.weight(1.0f).padding(1.dp)
                                    )
                                }
                            }

                        }
                    }

                    Spacer(modifier = Modifier.height(72.dp))
                }
                //your cards with a mini card representing the stand by card
                Row(
                    Modifier.padding(6.dp, 3.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                        border = BorderStroke(2.dp, if(uiState.currentSelectedMovSet == uiState.players[0].movesets[0]) {MaterialTheme.colorScheme.primary}
                        else{ MaterialTheme.colorScheme.tertiary}),
                        modifier = Modifier.size(185.dp, 100.dp).clickable(
                            onClick = {
                                if(enabled) {
                                    viewModel.selectMoveset(uiState.players[0].movesets[0])
                                }
                            }
                        )
                    ) {
                        Row(Modifier.weight(1.0f)) {
                            val move = uiState.players[0].movesets[0]
                            Column(Modifier.weight(1.0f)) {

                                Text(
                                    text = move.name,
                                    color = if(uiState.currentSelectedMovSet == uiState.players[0].movesets[0]) {MaterialTheme.colorScheme.primary}
                                    else{ MaterialTheme.colorScheme.tertiary},
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.padding(4.dp, 0.dp)
                                )
                                Image(
                                    painter = painterResource(move.resId),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(66.dp)
                                )
                            }
                            Column(Modifier.weight(1.2f)) {
                                Image(
                                    painter = painterResource(move.resBoardId),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.weight(1.0f)
                                )
                            }
                        }

                    }

                    Spacer(Modifier.width(18.dp))

                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                        border = BorderStroke(2.dp,
                            if(uiState.currentSelectedMovSet == uiState.players[0].movesets[1]) {MaterialTheme.colorScheme.primary}
                            else{ MaterialTheme.colorScheme.tertiary}),
                        modifier = Modifier.size(185.dp, 100.dp).clickable(
                            onClick = {
                                if(enabled) {
                                    viewModel.selectMoveset(uiState.players[0].movesets[1])
                                }
                            }
                        )
                    ) {
                        Row() {
                            val move = uiState.players[0].movesets[1]
                            Column(modifier = Modifier.weight(1.0f)) {

                                Text(
                                    text = move.name,
                                    color = if(uiState.currentSelectedMovSet == uiState.players[0].movesets[1]) {MaterialTheme.colorScheme.primary}
                                    else{ MaterialTheme.colorScheme.tertiary},
                                    style = MaterialTheme.typography.labelMedium,
                                    modifier = Modifier.padding(4.dp, 0.dp)
                                )
                                Image(
                                    painter = painterResource(move.resId),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.size(66.dp)
                                )
                            }
                            Column(Modifier.weight(1.2f)) {
                                Image(
                                    painter = painterResource(move.resBoardId),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier.weight(1.0f)
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}