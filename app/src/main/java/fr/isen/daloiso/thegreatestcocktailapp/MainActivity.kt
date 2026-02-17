package fr.isen.daloiso.thegreatestcocktailapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import fr.isen.daloiso.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.daloiso.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.daloiso.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                val context = LocalContext.current

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(
                                    "The Greatest Cocktail App",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            actions = {
                                IconButton(onClick = {
                                    Toast.makeText(
                                        context,
                                        "Ajouté aux favoris !",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.FavoriteBorder,
                                        contentDescription = "Localized description"
                                    )
                                }
                            }
                        )
                    },
                ) { innerPadding ->
                    //DetailCocktailScreen(Modifier.padding(innerPadding))
                    CategoriesScreen(
                        innerPadding,
                        onCategoryClick = { category ->
                            Log.d("Category", "click on $category")
                            val intent = Intent(context, DrinksActivity::class.java)
                            context.startActivity(intent)
                        })
                }
            }
        }
    }
}
