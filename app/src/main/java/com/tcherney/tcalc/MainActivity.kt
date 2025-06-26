package com.tcherney.tcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.tcherney.tcalc.ui.theme.TcalcTheme
import com.tcherney.keypad.KeyPad

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TcalcTheme {
                KeyPad()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun KeyPadPreview() {
    TcalcTheme {
        Column {
            KeyPad()
        }
    }
}