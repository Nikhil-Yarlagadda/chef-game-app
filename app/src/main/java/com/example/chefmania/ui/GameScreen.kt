package com.example.chefmania.ui

import android.content.res.Resources
import androidx.annotation.IdRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.chefmania.R
import org.intellij.lang.annotations.Identifier

class GameScreen {

    @Composable
    fun gameScreen(gameUiState: GameUiState){
        //opponent cards
        Row(modifier = Modifier.graphicsLayer {
            rotationZ = 180f
        }){
            Card(
                colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
            ) {
                Row(){
                    val move = gameUiState.players[1].movesets[0]
                    Column(Modifier.weight(1.0f)){

                        Text(
                            text = move.name,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Image(
                            painter = painterResource(move.resId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(Modifier.weight(1.0f)){
                        Image(
                            painter = painterResource(move.resBoardId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.weight(1.0f)
                        )
                    }
                }

            }

            Card(
                colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
            ) {
                Row(){
                    val move = gameUiState.players[1].movesets[1]
                    Column(Modifier.weight(1.0f)){

                        Text(
                            text = move.name,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Image(
                            painter = painterResource(move.resId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(Modifier.weight(1.0f)){
                        Image(
                            painter = painterResource(move.resBoardId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.weight(1.0f)
                        )
                    }
                }

            }
        }

        //board
        Row(){
            Box(){
                //smtg for square
                //image for potential pieces
            }
        }
        Row(){

        }
        Row(){

        }
        Row(){

        }
        Row(){

        }


        //your cards with a mini card representing the stand by card
        Row(){
            Card(
                colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
            ) {
                Row(){
                    val move = gameUiState.players[0].movesets[0]
                    Column(Modifier.weight(1.0f)){

                        Text(
                            text = move.name,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Image(
                            painter = painterResource(move.resId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(Modifier.weight(1.0f)){
                        Image(
                            painter = painterResource(move.resBoardId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.weight(1.0f)
                        )
                    }
                }

            }

            Card(
                colors = CardDefaults.cardColors().copy(containerColor = Color(0xffe0d8bb)),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary)
            ) {
                Row(){
                    val move = gameUiState.players[0].movesets[1]
                    Column(Modifier.weight(1.0f)){

                        Text(
                            text = move.name,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Image(
                            painter = painterResource(move.resId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Column(Modifier.weight(1.0f)){
                        Image(
                            painter = painterResource(move.resBoardId),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.weight(1.0f)
                        )
                    }
                }

            }
        }




    }
}