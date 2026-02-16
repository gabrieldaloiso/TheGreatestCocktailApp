package fr.isen.daloiso.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import fr.isen.daloiso.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource


class MainActivity : ComponentActivity() {

    private val TAG = "MainActivityLifecycle"

    override fun onCreate(savedInstanceState: Bundle?) {super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate appelé")
        enableEdgeToEdge()
        setContent {
            TheGreatestCocktailAppTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    DetailCocktailScreen(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart appelé")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume appelé")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause appelé")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop appelé")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy appelé")
    }
}