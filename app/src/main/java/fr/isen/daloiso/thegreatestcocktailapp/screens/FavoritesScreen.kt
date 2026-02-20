package fr.isen.daloiso.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.daloiso.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.daloiso.thegreatestcocktailapp.FavoritesManager

@Composable
fun FavoritesScreen(modifier: Modifier) {
    val context = LocalContext.current
    var favorites by remember { mutableStateOf(FavoritesManager.getFavorites(context)) }

    LaunchedEffect(Unit) {
        favorites = FavoritesManager.getFavorites(context)
    }

    Column(modifier.padding(16.dp)) {
        Text(
            text = "Mes Favoris (${favorites.size})",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Aucun favori pour le moment")
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { favorite ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                val intent = Intent(context, DetailCocktailActivity::class.java)
                                intent.putExtra("drinkId", favorite.id)
                                context.startActivity(intent)
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = favorite.image,
                                contentDescription = favorite.name,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(60.dp)
                                    .padding(end = 8.dp)
                            )

                            // Nom
                            Text(favorite.name)
                        }
                    }
                }
            }
        }
    }
}