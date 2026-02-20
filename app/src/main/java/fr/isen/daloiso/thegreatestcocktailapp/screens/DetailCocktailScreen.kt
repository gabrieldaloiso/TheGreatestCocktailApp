package fr.isen.daloiso.thegreatestcocktailapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.daloiso.thegreatestcocktailapp.R
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.daloiso.thegreatestcocktailapp.models.Category
import fr.isen.daloiso.thegreatestcocktailapp.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun DetailCocktailScreen(modifier: Modifier) {
    var drink by remember { mutableStateOf<Drink?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getRandomCocktail()
                drink = response.drinks?.firstOrNull()
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
@Composable
fun CategoryView(catogory: Category) {
    Box(Modifier
        .clip(CircleShape)
        .background(
            Brush.horizontalGradient(
                Category.colors(catogory)
            )
        )
    ) {
        Text(
            Category.toString(catogory),
            fontSize = 20.sp,
            color = colorResource(R.color.white),
            modifier = Modifier.padding(8.dp)
        )
    }
}