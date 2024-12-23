package com.nooro.weather.presentation.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nooro.weather.navigation.RegisterBaseNavigation
import com.nooro.weather.presentation.ui.theme.WhetherAppSampleTheme
import com.nooro.weather.presentation.ui.theme.White

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            WhetherAppSampleTheme {
                Surface(color = White, modifier = Modifier.fillMaxSize()) {
                    rememberNavController().RegisterBaseNavigation()
                }
            }
        }
    }
}
