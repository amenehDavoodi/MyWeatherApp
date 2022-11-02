package com.example.myweatherapp

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myweatherapp.presentation.WeatherViewModel
import com.example.myweatherapp.presentation.ui.WeatherCard
import com.example.myweatherapp.presentation.ui.theme.DarkBlue
import com.example.myweatherapp.presentation.ui.theme.DeepBlue
import com.example.myweatherapp.presentation.ui.theme.MyWeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel:WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher=registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions())
        {
            viewModel.getWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION

        ))
        setContent {
            MyWeatherAppTheme {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBlue)) {
                    WeatherCard(
                        state = viewModel.state,
                        bgColor = DeepBlue
                    )

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyWeatherAppTheme {
        Greeting("Android")
    }
}