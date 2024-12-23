package com.nooro.weather.presentation.view.weather

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.nooro.domain.model.Weather
import com.nooro.weather.R
import com.nooro.weather.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeatherScreen(weatherViewModel: WeatherViewModel) {
    val uiState by weatherViewModel.uiState.collectAsState()
    val searchCity = remember { mutableStateOf(TextFieldValue()) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        SearchBar(searchCity) {
            weatherViewModel.callWeatherAPI(searchCity.value.text, true)
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                uiState.loading -> {
                    ProgressIndicator()
                }

                uiState.error != null -> {
                    Text(text = uiState.error!!, color = Color.Red)
                }

                uiState.searchWeatherData != null -> {
                    WeatherCard(uiState.searchWeatherData!!) { data ->
                        searchCity.value = TextFieldValue("")
                        weatherViewModel.saveCityWeatherInfo(data)
                    }
                }

                uiState.mainWeatherData != null -> {
                    WeatherDataWithDetails(uiState.mainWeatherData!!)
                }

                else -> {
                    NoCitySelected()
                }
            }
        }
    }
}

@Composable
fun NoCitySelected() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.lab_no_city_selected),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.lab_please_search_for_a_city),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun WeatherCard(weatherData: Weather, onClickData: (Weather) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F2F2)
            ),
            onClick = {
                onClickData(weatherData)
            },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = weatherData.cityName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.temp_celsius, weatherData.tempC),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Image(
                    painter = rememberAsyncImagePainter(
                        stringResource(R.string.icon_url, weatherData.icon)),
                    contentDescription = stringResource(R.string.lab_weather_icon),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun WeatherDataWithDetails(weatherData: Weather) {
    LocalFocusManager.current.clearFocus()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = rememberAsyncImagePainter(stringResource(R.string.icon_url, weatherData.icon)),
            contentDescription = stringResource(R.string.lab_weather_icon),
            modifier = Modifier
                .padding(start = 16.dp)
                .height(100.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = weatherData.cityName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_wind),
                contentDescription = stringResource(R.string.lab_weather_icon),
                modifier = Modifier.size(25.dp),
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.temp_celsius, weatherData.tempC.toString()),
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5F5F5))
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            WeatherDetail(stringResource(R.string.lab_humidity), stringResource(R.string.humidity_value, weatherData.humidity.toString()))
            WeatherDetail(stringResource(R.string.lab_uv), weatherData.uv.toString())
            WeatherDetail(stringResource(R.string.lab_feels_like), stringResource(R.string.temp_celsius, weatherData.feelsLikeC.toString()))
        }
    }
}

@Preview
@Composable
fun SearchBar(searchCity: MutableState<TextFieldValue>, onSearch: (String) -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .background(
                color = Color(0xFFF2F2F2),
                shape = RoundedCornerShape(26.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = searchCity.value,
            onValueChange = {
                searchCity.value = it
            },
            placeholder = {
                Text(text = stringResource(R.string.lab_search_location))
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (searchCity.value.text.isNotEmpty()) {
                            onSearch(searchCity.value.text)
                        } else {
                            Toast.makeText(context,
                                context.getString(R.string.lab_please_enter_location), Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.lab_search),
                        tint = Color(0xFFC4C4C4)
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {  onSearch(searchCity.value.text) }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(26.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5),
                unfocusedTextColor = Color.Gray,
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}

@Composable
fun WeatherDetail(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    }
}

@Preview
@Composable
fun PreviewWeatherScreen() {
    WeatherScreen(koinViewModel())
}
