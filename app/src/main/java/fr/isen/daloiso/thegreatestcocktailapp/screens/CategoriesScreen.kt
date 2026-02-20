package fr.isen.daloiso.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.daloiso.thegreatestcocktailapp.DrinksActivity
import fr.isen.daloiso.thegreatestcocktailapp.dataClasses.DrinkCategory
import fr.isen.daloiso.thegreatestcocktailapp.models.Category
import fr.isen.daloiso.thegreatestcocktailapp.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(modifier: Modifier) {
    var categories by remember { mutableStateOf<List<DrinkCategory>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitClient.apiService.getCategories()
                categories = response.drinks ?: emptyList()
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
            modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                Card(Modifier.clickable {
                    val intent = Intent(context, DrinksActivity::class.java)
                    intent.putExtra("category", category.strCategory)
                    context.startActivity(intent)
                }) {
                    Text(
                        category.strCategory ?: "Sans nom",
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}