package com.zitos.theweather

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.zitos.theweather.data.utils.DataUtils
import com.zitos.theweather.ui.permission.AndroidLocationService
import com.zitos.theweather.ui.view_models.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            //App()
            val viewModel = koinViewModel<WeatherViewModel>()
            WeatherApp(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}



@Composable
fun WeatherApp(modifier: Modifier = Modifier, viewModel: WeatherViewModel) {

    var locationService by remember { mutableStateOf<AndroidLocationService?>(null) }
    val scope = rememberCoroutineScope()

    val permission =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { bool ->
            if (bool) {
                scope.launch(Dispatchers.IO) {
                    val location = locationService?.getLocation()
                    location?.run {
                        viewModel.getWeather(latitude, longitude)
                        viewModel.getWeatherDetails(latitude, longitude)
                    }
                }
            }
        }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        locationService = AndroidLocationService(context, permission)
        locationService?.requestLocationPermission { bool -> }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Blue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (uiState.error.isNotBlank()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.error)
            }
        }
        uiState.data?.let { response ->
            Spacer(modifier = Modifier.height(64.dp))

            AsyncImage(
                model = DataUtils.getImageUrl(response.icon),
                modifier = Modifier
                    .width(200.dp)
                    .height(100.dp),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = response.country,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 40.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = DataUtils.floatUpToTwoDecimalPlaces(response.temperature) + " C",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontSize = 60.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        uiState.weatherDetails?.let { response ->
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn {

                item {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 12.dp),
                        textAlign = TextAlign.Center,
                        text = "Last 5 days forecast",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                items(response) { item ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .fillMaxWidth()
                            .background(Color.White, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = DataUtils.convertDate(item.time),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = DataUtils.floatUpToTwoDecimalPlaces(item.feelsLike) + " C")
                        Spacer(modifier = Modifier.weight(1f))
                        AsyncImage(
                            model = DataUtils.getImageUrl(
                                item.icon
                            ),
                            modifier = Modifier.size(40.dp),
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}
