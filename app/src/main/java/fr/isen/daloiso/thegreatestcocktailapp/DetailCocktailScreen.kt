package fr.isen.daloiso.thegreatestcocktailapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.daloiso.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

@Composable
fun DetailCocktailScreen(name: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Image(
            painter = painterResource(id = R.drawable.cocktail),
            contentDescription =  "Image du cocktail"
        )
        Text(
            text = "Hello $name!"
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Click me")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailCocktailScreenPreview() {
    TheGreatestCocktailAppTheme {
        DetailCocktailScreen("Android")
    }
}
