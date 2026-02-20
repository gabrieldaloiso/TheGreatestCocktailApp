package fr.isen.daloiso.thegreatestcocktailapp.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import fr.isen.daloiso.thegreatestcocktailapp.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun RandomCocktailScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var drinkId by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getRandomCocktail()
                drinkId = response.drinks?.firstOrNull()?.idDrink
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    if (drinkId != null) {
        LaunchedEffect(drinkId) {
            navController.navigate("cocktail/$drinkId")
        }
    }
}
