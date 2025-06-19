package com.example.chefmania.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.chefmania.R
import com.example.compose.tertiaryDark

class HomeScreen {


    @Composable
    fun homeScreen(onStartPressed: ()->Unit, onInstruPressed: ()-> Unit, finish: ()-> Unit){
        Box(modifier = Modifier.fillMaxSize()){
            Image(
                painter = painterResource(id = R.drawable.kitchenfloorstains),
                contentScale = ContentScale.Crop,
                contentDescription = "",
                modifier = Modifier.matchParentSize()
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.matchParentSize()){
                Spacer(modifier= Modifier.height(50.dp))

                Text("Chef", modifier = Modifier,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.displayLarge)
                Text("Mania", modifier = Modifier,
                    color = MaterialTheme.colorScheme.tertiary,
                    style = MaterialTheme.typography.displayLarge)


                Spacer(modifier= Modifier.height(30.dp))

                OutlinedButton(border = BorderStroke(1.dp, Color.White),
                    elevation = ButtonDefaults.elevatedButtonElevation(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                    onClick = onStartPressed,modifier = Modifier.padding(24.dp)) {
                    Text("Start Game",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.headlineMedium)
                }

                OutlinedButton(border = BorderStroke(1.dp, Color.White),
                    elevation = ButtonDefaults.elevatedButtonElevation(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                    onClick = onInstruPressed, modifier = Modifier.padding(24.dp)) {
                    Text("Instructions",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.headlineMedium)
                }

                OutlinedButton(border = BorderStroke(1.dp, Color.White),
                    elevation = ButtonDefaults.elevatedButtonElevation(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                    onClick = finish,modifier = Modifier.padding(24.dp)) {
                    Text("Quit Game",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.headlineMedium)
                }
            }
        }
    }
}