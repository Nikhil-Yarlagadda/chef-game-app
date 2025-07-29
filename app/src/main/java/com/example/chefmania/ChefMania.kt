package com.example.chefmania

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chefmania.ui.GameScreen
import com.example.chefmania.ui.GameUiState
import com.example.chefmania.ui.GameViewModel
import com.example.chefmania.ui.HomeScreen

enum class ChefMania{
    Home,Game,HowtoPlay
}

@Composable
fun chefmania(
    finish: ()->Unit,
    viewModel: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier){

    val backStackEntry by navController.currentBackStackEntryAsState()
    val gameUiState: GameUiState = viewModel.uiState.collectAsState().value

    NavHost(
        navController = navController,
        startDestination = ChefMania.Home.name,
        modifier = Modifier
    ){
        composable(route = ChefMania.Home.name) {
            println("Hi")
            HomeScreen().homeScreen({ diff ->
                viewModel.resetGame(diff)
                navController.navigate(ChefMania.Game.name)
            }, {
                viewModel.resetGame(0)
                navController.navigate(ChefMania.HowtoPlay.name)
            },
            finish)
        }

        composable(route = ChefMania.Game.name) {
            GameScreen().gameScreen(viewModel = viewModel,
            {navController.navigate(ChefMania.Home.name)})
        }

        composable(route = ChefMania.HowtoPlay.name) {
            //GameScreen().gameScreen()
        }
    }
}