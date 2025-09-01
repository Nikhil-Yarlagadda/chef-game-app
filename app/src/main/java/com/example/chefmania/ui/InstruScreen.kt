package com.example.chefmania.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefmania.R

class InstruScreen {
    @Composable
    fun instructions(back: ()-> Unit){
        Box(
            modifier = Modifier.fillMaxSize().background(Color(0xFFD5BDB6))
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp).fillMaxWidth()) {
                item {
                    Row(modifier = Modifier.fillMaxWidth().padding(2.dp)) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                            Text(
                                "Instructions",
                                color = Color.Black,
                                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 68.sp)
                            )
                        }
                    }


                    Row(Modifier.fillMaxWidth().padding(2.dp)) {
                        Image(
                            painter = painterResource(R.drawable.studentblue_cropped),
                            contentDescription = "Cook",
                            modifier = Modifier.size(64.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.studentblue_cropped),
                            contentDescription = "Cook",
                            modifier = Modifier.size(64.dp)
                        )

                        Image(
                            painter = painterResource(R.drawable.bluemasterchef_cropped),
                            contentDescription = "Head Chef",
                            modifier = Modifier.size(80.dp)
                        )

                        Image(
                            painter = painterResource(R.drawable.studentblue_cropped),
                            contentDescription = "Cook",
                            modifier = Modifier.size(64.dp)
                        )
                        Image(
                            painter = painterResource(R.drawable.studentblue_cropped),
                            contentDescription = "Cook",
                            modifier = Modifier.size(64.dp)
                        )
                    }

                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier.padding(2.dp).fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                "Pieces",
                                color = Color.Black,
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Row {
                                Text(
                                    "You start with 4 cooks and 1 Head Chef",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
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
                            Row {
                                Text(
                                    "Each player has 2 cards in front of them and" +
                                            " an extra 5th card is placed in the center. Select one of the cards on your side. " +
                                            "Then select a piece. The blue square in the center of the diagram in the card represents the " +
                                            "location your piece currently is. The green squares are where you can move the piece using this card. " +
                                            "Once you move using a card, that card is rotated out with the center card in rotation to keep the cards" +
                                            " moving between players.",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                "Capturing",
                                color = Color.Black,
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Row {
                                Text(
                                    "You can capture pieces by moving your piece onto your opponent's piece." +
                                            " You cannot take your own pieces",
                                    color = Color.Black,
                                    style = MaterialTheme.typography.labelMedium
                                )
                            }
                        }
                    }

                    Card(
                        colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier.padding(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                "How to Win",
                                color = Color.Black,
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Text(
                                "You can win by doing one of two things.",
                                color = Color.Black,
                                style = MaterialTheme.typography.labelMedium
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
            
            Image(
                painter = painterResource(R.drawable.baseline_keyboard_return_24),
                contentDescription = "return",
                modifier = Modifier.size(42.dp).clickable(
                    onClick = back
                )
            )
        }
    }
}