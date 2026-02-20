package fr.isen.daloiso.thegreatestcocktailapp.screens

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import fr.isen.daloiso.thegreatestcocktailapp.R
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.CocktailResponse
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.Drink
import fr.isen.daloiso.thegreatestcocktailapp.managers.FavoritesManager
import fr.isen.daloiso.thegreatestcocktailapp.models.AppBarState
import fr.isen.daloiso.thegreatestcocktailapp.models.Category
import fr.isen.daloiso.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@Composable
fun RandomCocktailScreen(modifier: Modifier, onComposing: (AppBarState) -> Unit) {
    var drink = remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(Unit) {
        onComposing (
            AppBarState("Random Cocktail",
                actions = { DetailCocktailTopButton(drink.value) })
        )
//        drink.value = ApiClient.retrofit.getRandom().drinks?.first()
        val call = ApiClient.retrofit.getRandomCocktail()
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse?>?,
                response: Response<CocktailResponse?>?
            ) {
                drink.value = response?.body()?.drinks?.first()
            }
            override fun onFailure(
                call: Call<CocktailResponse?>?,
                t: Throwable?
            ) {
                Log.e("request", "getrandom failed ${t?.message}")
            }
        })
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(modifier, drink)
    } ?: run {
        Text("Loading")
    }
}

@Composable
fun DetailCocktailScreen(drinkId: String,
                         onComposing: (AppBarState) -> Unit,
                         modifier: Modifier) {
    var drink = remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(Unit) {

        onComposing (
            AppBarState("Random Cocktail",
                actions = { DetailCocktailTopButton(drink.value) })
        )
//        drink.value = ApiClient.retrofit.getRandom().drinks?.first()
        val call = ApiClient.retrofit.getDetailCocktail(drinkId)
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse?>?,
                response: Response<CocktailResponse?>?
            ) {
                drink.value = response?.body()?.drinks?.first()
            }
            override fun onFailure(
                call: Call<CocktailResponse?>?,
                t: Throwable?
            ) {
                Log.e("request", "getrandom failed ${t?.message}")
            }
        })
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(modifier, drink)
    } ?: run {
        Text("Loading")
    }
}
@Composable
fun DetailCocktailScreen(modifier: Modifier, drink: Drink) {
    Box(
        Modifier.background(
            brush = Brush.verticalGradient(
                listOf(
                    colorResource(R.color.orange_700),
                    colorResource(R.color.orange_200)
                )
            ))
            .fillMaxSize()) {
        Column(
            modifier = modifier.fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)) {
            AsyncImage(
                model = drink.strDrinkThumb,
                "",
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
                    .clip(CircleShape)
                    .border(
                        5.dp,
                        colorResource(R.color.orange_700),
                        CircleShape
                    )
            )

//            Image(
//                painterResource(R.drawable.cocktail),
//                "",
//                contentScale = ContentScale.FillBounds,
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(200.dp)
//                    .clip(CircleShape)
//                    .border(
//                        1.dp,
//                        colorResource(R.color.teal_200),
//                        CircleShape
//                    )
//            )
            Text(drink.strDrink ?: "",
                fontSize = 40.sp,
                color = colorResource(R.color.white))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
//                Text("Other / Unknown")
//                Text("Non alcoholic")
                Text(drink.strCategory ?: "Unknown")
                Text(drink.strAlcoholic ?: "Unknown")
            }
            Text(drink.strGlass ?: "Unknown glass",
                color = colorResource(R.color.white)
            )// Kind of glass
            Card() {
                Column(
                    Modifier.padding(16.dp)
                        .fillMaxWidth()) {
                    Text(stringResource(R.string.igrendient),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    drink.ingredientList().forEach { (ingredient, measure) ->
                        Text("• ${measure.ifBlank { "" }} $ingredient")
                    }
                }
            }
            Card() {
                Column(
                    Modifier.padding(16.dp)
                        .fillMaxWidth()) {
                    Text(stringResource(R.string.preparation),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                    if (!drink.strInstructions.isNullOrBlank()) {
                        Text(drink.strInstructions ?: "")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailCocktailTopButton(drink: Drink?) {
    val context = LocalContext.current
    val favoritesManager = FavoritesManager()
    drink?.let { drink ->
        var isFavorites = remember { mutableStateOf<Boolean>( value = favoritesManager.isFavorite(drink, context))}
        IconButton({
            favoritesManager.toggleFavorite(drink, context)
            isFavorites.value = favoritesManager.isFavorite(drink, context)
        }) {
            Icon(
                imageVector = if (isFavorites.value) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
                contentDescription = "Localized description"
            )
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