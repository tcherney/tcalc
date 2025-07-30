package com.tcherney.tcalc

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em


//TODO figure out styling, functionality looks in place
//TODO figure out publishing so we can import in other project
@Composable
fun KeyPad(input: MutableState<String> = remember { mutableStateOf("") }, curVal: MutableIntState = remember{mutableIntStateOf(0)}, plusBuildEnabled: Boolean = true) {
    //TODO refactor modes to use a simple enum instead of doing string comparisons
    val curInput = remember {mutableStateOf("")}
    val previousInput = remember {mutableStateOf("")}
    val curMode = remember {mutableStateOf("+")}
    val curMulMode = remember {mutableStateOf("+")}
    val plusBuild = remember { mutableIntStateOf(0) }
    var cleared = false
    val ctx = LocalContext.current
    fun computeInput(newMode: String) {
        //TODO finish end functionality
        if (newMode == "end") {
            input.value = ""
            curInput.value = ""
            previousInput.value = ""
            curMode.value = "+"
            curMulMode.value = "+"
            plusBuild.intValue = 0
            if (ctx is IMEService) {
                ctx.currentInputConnection.commitText(
                    curVal.intValue.toString(),
                    curVal.intValue.toString().length
                )
                //reset if keyboard input, might want to generally do that
                curVal.intValue = 0
                input.value = ""
                curInput.value = ""
                previousInput.value = ""
                curMode.value = "+"
                curMulMode.value = "+"
                plusBuild.intValue = 0
            }
            return
        }
        if (curInput.value.isEmpty()) {
            if (input.value.isNotEmpty() && newMode != "build") {
                input.value = input.value.dropLast(1) + newMode
            }
            if (newMode == "build" && plusBuild.intValue != 0) {
                if (curMode.value == "+" || curMode.value == "build") {
                    curVal.intValue += plusBuild.intValue
                } else if (curMode.value == "-") {
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
                    //this allows entering of a number directly
                    if(newMode == "build" && plusBuild.intValue == 0) {
                        if (ctx is IMEService) {
                            ctx.currentInputConnection.commitText(
                                curInput.value,
                                curInput.value.length
                            )
                            curVal.intValue = 0
                            input.value = ""
                            curInput.value = ""
                            previousInput.value = ""
                            curMode.value = "+"
                            curMulMode.value = "+"
                            plusBuild.intValue = 0
                            cleared = true
                        }
                    }
                    else {
                        curVal.intValue += curInput.value.toInt()
                        if (newMode != "build" && curInput.value.toInt() != 0) {
                            plusBuild.intValue = curInput.value.toInt()
                        }
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
            if (!cleared) {
                if (newMode == "build") {
                    input.value += "+"
                } else {
                    input.value += newMode
                }
                curMode.value = newMode
            }
            cleared = false
        }
        if (newMode == "*") {
            if (curMode.value != "*") {
                curMulMode.value = curMode.value
            }
        }



    }
    val buttonPadding = Modifier.padding(2.dp)
    Column {
        Row {
            //Text(curVal.intValue.toString(), fontSize = 20.em)
            Text(
                text = curVal.intValue.toString(),
                style = TextStyle(
                    fontSize = 20.em,
                    shadow = Shadow(
                        color = Color.White, blurRadius = 3f
                    )
                )
            )
        }
        //TODO adjust settings for textbox so that it doesn't shift content down when it gets big
        Row {
            //Text(input.value, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(
                text = input.value,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.White, blurRadius = 3f
                    )
                )
            )
        }
        Row {
            Button( onClick = {
                computeInput("end")
            }, modifier = buttonPadding) {
                Text("End")
            }
        }
        Row {
            Button( onClick = {
                computeInput("+")
            }, modifier = buttonPadding

            ) {
                Text("+")
            }
            Button( onClick = {computeInput("*")}, modifier = buttonPadding

            ) {
                Text("*")
            }
            Button( onClick = {computeInput("-")}, modifier = buttonPadding

            ) {
                Text("-")
            }
            Button( onClick = {computeInput("+")}, modifier = buttonPadding

            ) {
                Text("+")
            }
        }
        Row {
            Button( onClick = {
                input.value += "7"
                curInput.value += "7"
            }, modifier = buttonPadding) {
                Text("7")
            }
            Button( onClick = {
                input.value += "8"
                curInput.value += "8"
            }, modifier = buttonPadding) {
                Text("8")
            }
            Button( onClick = {
                input.value += "9"
                curInput.value += "9"
            }, modifier = buttonPadding) {
                Text("9")
            }
        }
        Row {
            Button( onClick = {
                input.value += "4"
                curInput.value += "4"
            }, modifier = buttonPadding) {
                Text("4")
            }
            Button( onClick = {
                input.value += "5"
                curInput.value += "5"
            }, modifier = buttonPadding) {
                Text("5")
            }
            Button( onClick = {
                input.value += "6"
                curInput.value += "6"
            }, modifier = buttonPadding) {
                Text("6")
            }
        }
        Row {
            Button( onClick = {
                input.value += "1"
                curInput.value += "1"
            }, modifier = buttonPadding) {
                Text("1")
            }
            Button( onClick = {
                input.value += "2"
                curInput.value += "2"
            }, modifier = buttonPadding) {
                Text("2")
            }
            Button( onClick = {
                input.value += "3"
                curInput.value += "3"
            }, modifier = buttonPadding) {
                Text("3")
            }
        }
        Row {
            Button( onClick = {
                if (plusBuildEnabled)computeInput("build")
                else computeInput("+")
            }, modifier = buttonPadding) {
                Text(".")
            }
            Button( onClick = {
                input.value += "0"
                curInput.value += "0"
            }, modifier = buttonPadding) {
                Text("0")
            }
            Button( onClick = {
                if (plusBuildEnabled)computeInput("build")
                else computeInput("+")
            }, modifier = buttonPadding) {
                Text("Enter")
            }
        }

    }
}