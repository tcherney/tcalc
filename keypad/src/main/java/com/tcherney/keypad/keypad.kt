package com.tcherney.keypad

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

//TODO figure out styling, functionality looks in place
//TODO figure out publishing so we can import in other project
@Composable
fun KeyPad() {
    val input = remember {mutableStateOf("")}
    val curInput = remember {mutableStateOf("")}
    val curVal = remember { mutableIntStateOf(0) }
    val previousInput = remember {mutableStateOf("")}
    val curMode = remember {mutableStateOf("+")}
    val curMulMode = remember {mutableStateOf("+")}
    val plusBuild = remember { mutableIntStateOf(0) }

    fun computeInput(newMode: String) {
        if (curInput.value.isEmpty()) {
            if (input.value.isNotEmpty() && newMode != "build") {
                input.value = input.value.dropLast(1) + newMode
            }
            if (newMode == "build" && plusBuild.intValue != 0) {
                if (curMode.value == "+" || curMode.value == "build") {
                    curVal.intValue += plusBuild.intValue
                }
                else if (curMode.value == "-") {
                    curVal.intValue -= plusBuild.intValue
                }
                previousInput.value = plusBuild.intValue.toString()
                input.value += plusBuild.intValue.toString() + "+"
                curMode.value = "+"
            }
            else {
                curMode.value = newMode
            }

        }
        else {
            if (curMode.value == "+") {
                if (newMode != "*") {
                    curVal.intValue += curInput.value.toInt()
                    if (newMode != "build" && curInput.value.toInt() != 0) {
                        plusBuild.intValue = curInput.value.toInt()
                    }
                }
                previousInput.value = curInput.value
                curInput.value = ""
            } else if (curMode.value == "-") {
                if (newMode != "*") {
                    curVal.intValue -= curInput.value.toInt()
                }
                previousInput.value = curInput.value
                curInput.value = ""
            } else if (curMode.value == "*") {
                if (curMulMode.value == "+") {
                    curVal.intValue += curInput.value.toInt() * previousInput.value.toInt()
                }
                else {
                    curVal.intValue -= curInput.value.toInt() * previousInput.value.toInt()
                }
                previousInput.value = curInput.value
                curInput.value = ""
            }
            else if (curMode.value == "build") {
                if (newMode != "*") {
                    curVal.intValue += curInput.value.toInt()
                    if (newMode != "build" && curInput.value.toInt() != 0) {
                        plusBuild.intValue = curInput.value.toInt()
                    }
                }
                previousInput.value = curInput.value
                curInput.value = ""
            }
            if (newMode == "build") {
                input.value += "+"
            }
            else {
                input.value += newMode
            }
            curMode.value = newMode
        }
        if (newMode == "*") {
            if (curMode.value != "*") {
                curMulMode.value = curMode.value
            }
        }



    }
    Column {
        Row {
            Text(curVal.intValue.toString())
        }
        Row {
            Text(input.value)
        }
        Row {
            Button( onClick = {
                computeInput("+")
            }) {
                Text("+")
            }
            Button( onClick = {computeInput("*")}) {
                Text("*")
            }
            Button( onClick = {computeInput("-")}) {
                Text("-")
            }
            Button( onClick = {computeInput("+")}) {
                Text("+")
            }
        }
        Row {
            Button( onClick = {
                input.value += "7"
                curInput.value += "7"
            }) {
                Text("7")
            }
            Button( onClick = {
                input.value += "8"
                curInput.value += "8"
            }) {
                Text("8")
            }
            Button( onClick = {
                input.value += "9"
                curInput.value += "9"
            }) {
                Text("9")
            }
        }
        Row {
            Button( onClick = {
                input.value += "4"
                curInput.value += "4"
            }) {
                Text("4")
            }
            Button( onClick = {
                input.value += "5"
                curInput.value += "5"
            }) {
                Text("5")
            }
            Button( onClick = {
                input.value += "6"
                curInput.value += "6"
            }) {
                Text("6")
            }
        }
        Row {
            Button( onClick = {
                input.value += "1"
                curInput.value += "1"
            }) {
                Text("1")
            }
            Button( onClick = {
                input.value += "2"
                curInput.value += "2"
            }) {
                Text("2")
            }
            Button( onClick = {
                input.value += "3"
                curInput.value += "3"
            }) {
                Text("3")
            }
        }
        Row {
            Button( onClick = {computeInput("build")}) {
                Text(".")
            }
            Button( onClick = {
                input.value += "0"
                curInput.value += "0"
            }) {
                Text("0")
            }
            Button( onClick = {computeInput("build")}) {
                Text("+")
            }
        }

    }
}