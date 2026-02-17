package fr.isen.daloiso.thegreatestcocktailapp.screens

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun CategoriesScreen(paddingValues: PaddingValues, onCategoryClick: (Category) -> Unit) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(Category.allObjects()) { category ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        Log.d("tag", "click on $category")
                        onCategoryClick(category)
                    }
            ) {
                Text(
                    text = Category.toString(category),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}