package fr.isen.daloiso.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.isen.daloiso.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.DrinkPreview
import fr.isen.daloiso.thegreatestcocktailapp.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun DrinksScreen(modifier: Modifier, category: String) {
    var drinks by remember { mutableStateOf<List<DrinkPreview>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(category) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getDrinksByCategory(category)
                drinks = response.drinks ?: emptyList()
                isLoading = false
            } catch (e: Exception) {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(drinks) { drink ->
                Card(Modifier.clickable {
                    val intent = Intent(context, DetailCocktailActivity::class.java)
                    intent.putExtra("drinkId", drink.idDrink)
                    context.startActivity(intent)
                }) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = drink.strDrinkThumb,
                            contentDescription = drink.strDrink,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 8.dp)
                        )

                        // Nom du cocktail
                        Text(drink.strDrink ?: "Sans nom")
                    }
                }
            }
        }
    }
}