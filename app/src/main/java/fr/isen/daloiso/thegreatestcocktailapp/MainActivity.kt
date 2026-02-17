package fr.isen.daloiso.thegreatestcocktailapp

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.daloiso.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.daloiso.thegreatestcocktailapp.screens.BottomAppBar
import fr.isen.daloiso.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.daloiso.thegreatestcocktailapp.screens.FavoritesScreen
import fr.isen.daloiso.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            val randomItem = TabBarItem(stringResource(id = R.string.tab_item_random), selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
            val categoryItem = TabBarItem(stringResource(id = R.string.tab_item_category), selectedIcon = Icons.Filled.Menu, unselectedIcon = Icons.Outlined.Menu)
            val favoriteItem = TabBarItem(stringResource(id = R.string.tab_item_favorite), selectedIcon = Icons.Filled.Favorite, unselectedIcon = Icons.Outlined.Favorite)

            val tabItems = listOf(randomItem, categoryItem, favoriteItem)

            TheGreatestCocktailAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar({
                            Text("Detail")
                        }, actions = {
                            IconButton({
                                Toast
                                    .makeText(context, "Add to favorite", Toast.LENGTH_LONG)
                                    .show()
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = "Localized description"
                                )
                            }
                        })
                    },
                    bottomBar = { BottomAppBar(tabItems, navController) }
                ) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    NavHost(navController, startDestination = randomItem.title){
                        composable( route = randomItem.title) {
                            DetailCocktailScreen(
                                Modifier.padding(innerPadding))
                        }
                        composable( route = categoryItem.title) {
                            CategoriesScreen(
                                Modifier.padding(innerPadding))
                        }
                        composable( route = favoriteItem.title) {
                            FavoritesScreen(
                                Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier) { // Horizontal Alignment
        Text("Hello $name!")
        Text("Hello Isen")
    }

//    Column(modifier = modifier) { /* Vertical Alignment */
//        Text("Hello $name!")
//        Text("Hello Isen")
//    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheGreatestCocktailAppTheme {
        Greeting("Android")
    }
}