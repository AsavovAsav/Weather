package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.ui.theme.WeatherTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofit=Retrofit
            .Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        setContent {
            WeatherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherApp(retrofit = retrofit, city = "Omsk")
                }
            }
        }
    }
}

@Composable
fun WeatherApp(retrofit:Retrofit,city: String) {
    val state= remember {
        mutableStateOf("Unknown")
    }
    val state2= remember {
        mutableStateOf("Unknown")
    }
    var model:CurrentWeather?=null
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
            contentAlignment = Alignment.Center,
        ){
            Text(text = "Temp in ${state.value}")
        }
        Text(text = "Temp is ${state2.value}",)
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter,

        ){
            Button(onClick = {
                             getResult(retrofit,state,state2, city)
            }, modifier= Modifier
                .padding(8.dp)
                .fillMaxWidth()
                ) {
                Text(text = "Refresh")
            }
        }
    }
}

fun getResult(retrofit:Retrofit,state1:MutableState<String>,state2: MutableState<String>,city:String){
    var model_:CurrentWeather





     
    CoroutineScope(Dispatchers.Main).launch {
        val api=retrofit.create(WeatherAPI::class.java)
        model_=api.getCurrentWeather(city,"metric","6a5ed850c5355e76544c503a4f411221")
        state1.value=model_?.name.toString()
        state2.value=model_.main?.temp.toString()
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherTheme {
    }
}