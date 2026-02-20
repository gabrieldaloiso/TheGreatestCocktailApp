package fr.isen.daloiso.thegreatestcocktailapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.daloiso.thegreatestcocktailapp.FavoritesManager
import fr.isen.daloiso.thegreatestcocktailapp.R
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.daloiso.thegreatestcocktailapp.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun DetailCocktailScreen(modifier: Modifier, drinkId: String?) {
    var drink by remember { mutableStateOf<Drink?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var isFavorite by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(drinkId) {
        scope.launch {
            try {
                val response = if (drinkId != null) {
                    RetrofitClient.apiService.getDrinkDetail(drinkId)
                } else {
                    RetrofitClient.apiService.getRandomCocktail()
                }
                drink = response.drinks?.firstOrNull()
                drink?.idDrink?.let { id ->
                    isFavorite = FavoritesManager.isFavorite(context, id)
                }

                isLoading = false
            } catch (e: Exception) {
                isLoading = false
            }
        }
    }

    Box(
        Modifier.background(
            brush = Brush.verticalGradient(
                listOf(
                    colorResource(R.color.orange_200),
                    colorResource(R.color.orange_700)
                )
            )
        )
            .fillMaxSize()
    ) {
        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (drink != null) {
            Column(
                modifier = modifier.fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(state = rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            drink?.let { d ->
                                FavoritesManager.toggleFavorite(
                                    context,
                                    d.idDrink ?: "",
                                    d.strDrink ?: "",
                                    d.strDrinkThumb ?: ""
                                )
                                isFavorite = !isFavorite
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favori",
                            tint = colorResource(R.color.white),
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                AsyncImage(
                    model = drink!!.strDrinkThumb,
                    contentDescription = drink!!.strDrink,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .clip(CircleShape)
                        .border(4.dp, colorResource(R.color.orange_700), CircleShape)
                )
                Text(
                    drink!!.strDrink ?: "Sans nom",
                    fontSize = 40.sp,
                    color = colorResource(R.color.white)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = drink?.strCategory ?: "",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .background(colorResource(R.color.teal_200), shape = RoundedCornerShape(50))
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = drink?.strAlcoholic ?: "",
                            fontSize = 20.sp,
                            modifier = Modifier
                                .background(colorResource(R.color.purple_200), shape = RoundedCornerShape(50))
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Text(
                    drink!!.strGlass ?: "Unknown glass",
                    color = colorResource(R.color.white)
                )
                Card() {
                    Column(Modifier.padding(16.dp).fillMaxWidth()) {
                        Text(stringResource(R.string.igrendient))
                        drink!!.ingredientList().forEach { (ingredient, measure) ->
                            Text("• ${measure.ifBlank { "" }} $ingredient")
                        }
                    }
                }
                if (!drink!!.strInstructions.isNullOrBlank()) {
                    Card() {
                        Column(Modifier.padding(16.dp).fillMaxWidth()) {
                            Text(stringResource(R.string.preparation))
                            Text(drink!!.strInstructions ?: "")
                        }
                    }
                }
            }
        }
    }
}