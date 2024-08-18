package com.ronin.horizontalwheelpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ronin.horizontal_wheel_picker.HorizontalWheelPicker
import com.ronin.horizontalwheelpicker.ui.theme.HorizontalWheelPickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HorizontalWheelPickerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var selectedItem by remember { mutableIntStateOf(15) }
                        Text(
                            text = selectedItem.toString(),
                            fontSize = 46.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        HorizontalWheelPicker(
                            totalItems = 100,
                            initialSelectedItem = selectedItem,
                            onItemSelected = { item ->
                                selectedItem = item
                            }
                        )
                    }
                }
            }
        }
    }
}