package com.example.chefmania.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefmania.R

class InstruScreen {
    @Composable
    fun instructions(){
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xFFD5BDB6))
        ) {
            Column {
                Row(Modifier.fillMaxWidth()){
                    Image(
                        painter = painterResource(R.drawable.bluemasterchef_cropped),
                        contentDescription = "Head Chef",
                        modifier = Modifier.size(64.dp)
                    )

                    Image(
                        painter = painterResource(R.drawable.studentblue_cropped),
                        contentDescription = "Cook",
                        modifier = Modifier.size(64.dp)
                    )
                }

                Card(colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.DarkGray)) {
                    Column(modifier = Modifier.padding(6.dp)) {
                        Text(
                            "Pieces",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Row{
                            Text(
                                "You start with 4 cooks and 1 Head Chef",
                                color = Color.Black,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }

                Card(colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.DarkGray)) {
                    Column {
                        Text(
                            "Moving",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Image(
                            painter = painterResource(R.drawable.pizzaboard),
                            contentDescription = "Example movement diagram in a card",
                            modifier = Modifier.size(64.dp)
                        )
                        Row{
                            Text("You also start with 2 cards in front of your side of the screen so does opponent" +
                                    " but an extra 5th card is in the center. Select one of the cards on your side." +
                                    "Then select a piece. The blue square in the center of the diagram in the card represents the " +
                                    "location your piece currently is. The green squares are where you can move the piece using this card. " +
                                    "Once you move using a card, that card is rotated out with the center card in rotation to keep the cards" +
                                    " moving between players.",
                                color = Color.Black,
                                style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }

                Card(colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.DarkGray)) {
                    Column {
                        Text(
                            "Capturing",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Row{
                            Text("You can capture pieces by moving your piece onto your opponent's piece." +
                                    " You cannot take your own pieces",
                                color = Color.Black,
                                style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }

                Card (colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color.DarkGray)){
                    Column {
                        Text(
                            "How to Win",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            "1. Capture your opponents Head Chef",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            "2. Move your Head Chef to your opponents Kitchen." +
                                    "Your Kitchen is the square your Head Chef starts on. The kitchen square will be outlined in your color.",
                            color = Color.Black,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}