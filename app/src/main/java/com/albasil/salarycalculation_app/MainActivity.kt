package com.albasil.salarycalculation_app

import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.airbnb.lottie.compose.*
import com.albasil.salarycalculation_app.ui.theme.SalaryCalculationAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalaryCalculationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ContentsColumn()


                }


            }
        }
    }
}


@Composable
fun ContentsColumn() {

    LazyColumn(
        modifier = Modifier.padding(bottom = 30.dp,start = 15.dp,end = 15.dp,top = 3.dp),


        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,



    )
    {

        item {
            Loader()
            SpinnerDays()


            WorkHours()


            TotalSalary()





        }

    }


}


var daysOfMonth: String = ""
var numberHours: String = ""
var numberOfLateHours: String = ""


var numberDaysOfAbsent: String = ""
var numberExtraDaysOff: String = ""


@Composable
fun SpinnerDays() {
    val listOfDays = mutableListOf<String>()

    var expanded by remember { mutableStateOf(false) }

    for (i in 27..31) {
        listOfDays.add("$i")
    }
    var selectedNumberDays by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.KeyboardArrowDown

    Column() {
        daysOfMonth = selectedNumberDays

        OutlinedTextField(
            value = selectedNumberDays,
            onValueChange = { selectedNumberDays = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Selected Number Days Of Month") },
            leadingIcon = { Icon(imageVector = Icons.Default.DateRange, contentDescription = "") },
          //  leadingIcon = { Text(text = "$") },

                    trailingIcon = {

                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            },
            readOnly = true

        )
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            listOfDays.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedNumberDays = label
                }) {
                    Text(text = label)
                }
            }
        }
    }
}




@Composable
fun WorkHours() {


    var workHours by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        value = workHours,
        onValueChange = { workHours = it },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text("WorK Hours")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Notifications,contentDescription = "")
                      },
        //  leadingIcon = { Text(text = "$") },


        )

    numberHours = workHours

}


@Composable
fun DaysOfAbsent() {

    var daysOfAbsent by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = daysOfAbsent,
        onValueChange = { daysOfAbsent = it },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Close, contentDescription = "") },

        label = {
            Text("Days Of Absent")

        },


        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    numberDaysOfAbsent = daysOfAbsent

}


@Composable
fun ExtraOffDays() {


    var numberExtraOffDays by remember {
        mutableStateOf("")
    }

    var IsError by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        value = numberExtraOffDays,
        onValueChange = { numberExtraOffDays = it

                        IsError= it.contains("-") || it.contains(",")
                        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = { Icon(imageVector = Icons.Default.Done, contentDescription = "") },

        isError = IsError,
        label = {
            Text("Extra Days Off")
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    numberExtraDaysOff = numberExtraOffDays
}




@Composable
fun SpinnerLate() {
    val listOfHours = mutableListOf<String>()

    var expanded by remember { mutableStateOf(false) }

    if (!numberHours.isNullOrEmpty()) {
        for (i in 0..numberHours.toInt()) {
            listOfHours.add("$i")
        }
    }
    var selectedNumberOfLateHours by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.KeyboardArrowDown

    Column() {

        numberOfLateHours = selectedNumberOfLateHours
        OutlinedTextField(
            value = selectedNumberOfLateHours,
            onValueChange = { selectedNumberOfLateHours = it },
            // enabled = false,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            leadingIcon = { Icon(imageVector = Icons.Default.Check, contentDescription = "") },

            label = { Text("Selected Late Hours") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })


            },


            readOnly = true

        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            listOfHours.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedNumberOfLateHours = label
                }) {
                    Text(text = label)
                }
            }
        }
    }

}







@Composable
fun TotalSalary() {
    val context = LocalContext.current

    var employeeSalary by remember {
        mutableStateOf("")
    }

    var isError by rememberSaveable { mutableStateOf(false) }



    Column() {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),

            leadingIcon = {
              Text(text = "$")
            },
            value = employeeSalary,
            onValueChange = { employeeSalary = it
                isError= it.contains("-")|| it.contains(".")
                            },
            label = { Text(text = "Enter Employee Salary") },
            singleLine = true,

            isError = isError,

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.outlinedTextFieldColors(
              focusedBorderColor = colorResource(id = R.color.cardview_dark_background),
                focusedLabelColor = colorResource(id = R.color.design_default_color_error)
            )
        )


        if (isError) {
            Text(
                text = "Error message",
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }


        ExtraOffDays()

        DaysOfAbsent()


        SpinnerLate()


        var DailyPrice by remember {
            mutableStateOf("${employeeSalary}")
        }
        var HourlyPrice by remember {
            mutableStateOf("")
        }
        var DaysOfAbsent by remember {
            mutableStateOf("")
        }
        var TotalLateHours by remember {
            mutableStateOf("")
        }

        var DaysOff by remember {
            mutableStateOf("")
        }

        var TotalSalary by remember {
            mutableStateOf("")
        }

        //---------------------------------------------------------------------------
        OutlinedButton(onClick = {


            if ( !daysOfMonth.isNullOrEmpty() && !numberHours.isNullOrEmpty() && !employeeSalary.isNullOrEmpty() ){



            if ( !employeeSalary.contains("-") && !employeeSalary.contains(",")
                &&!numberHours.contains("-") && !numberHours.contains(",")&&!numberHours.contains(".")
                &&!numberExtraDaysOff.contains("-") && !numberExtraDaysOff.contains(",")&&!numberExtraDaysOff.contains(".")
                &&!numberDaysOfAbsent.contains("-") && !numberDaysOfAbsent.contains(",")&&!numberDaysOfAbsent.contains(".")

            ){


                    //Calucletor------------------

                    DailyPrice = "${employeeSalary.toDouble() / daysOfMonth.toInt()}"

                    HourlyPrice = "${DailyPrice.toDouble() / numberHours.toInt()} "

                    "%.2f".format(HourlyPrice.toDouble())


                    // HourlyPrice =hourlyPrice
                    HourlyPrice = "%.2f".format(HourlyPrice.toDouble())
                    DailyPrice = "%.2f".format(DailyPrice.toDouble())


                    if (!numberExtraDaysOff.isNullOrEmpty()) {
                        DaysOff = "${(numberExtraDaysOff.toInt() * DailyPrice.toDouble()) * 1.50}"

                    } else {
                        DaysOff = "0"


                    }




                    DaysOfAbsent = if (!numberDaysOfAbsent.isNullOrEmpty()) {
                        "${(DailyPrice.toDouble() * numberDaysOfAbsent.toInt())}"
                    } else {
                        "0"
                    }

                    if (!numberOfLateHours.isNullOrEmpty()) {
                        TotalLateHours = "${numberOfLateHours.toInt() * HourlyPrice.toDouble()}"

                    } else {
                        TotalLateHours = "0"

                    }



                    TotalSalary =
                        "TotalSalary $${(employeeSalary.toDouble() - DaysOfAbsent.toDouble()) + (DaysOff.toDouble() - TotalLateHours.toDouble())}"


            }else{
                Toast.makeText(context, "Please Enter valid text ", Toast.LENGTH_SHORT).show()

            }

            }else{

                Toast.makeText(context, "Please Enter text ", Toast.LENGTH_SHORT).show()
            }




        }
        ) {
            Text(text = "Calculate",
                Modifier
                    .background(Color.Green)
                    .fillMaxWidth()
                    .size(35.dp)
                    .padding(top = 10.dp)

                ,color = Color.Blue
            )
            Icon(imageVector = Icons.Default.Create, contentDescription = "")

        }



        Text(text = " Salary : $employeeSalary ", color= Color.Cyan)
        Text(text = " Daily $DailyPrice ",color = Color.Blue)
        Text(text = " Hours $HourlyPrice",color = Color.Blue)

        Text(text = " Number Days Of Absent: -$DaysOfAbsent",color = Color.Red)
        Text(text = " Late Hours: -$TotalLateHours",color = Color.Red)

        Text(text = " Total Extra Days Off: $DaysOff",color = Color.Green)



        Text(text = "$TotalSalary",color = Color.LightGray)


    }


}


@Composable
fun Loader() {
    val compositionResult: LottieCompositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.money))

    val progress by animateLottieCompositionAsState(compositionResult.value,

        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
        )

    LottieAnimation(compositionResult.value, progress =progress,

    modifier = Modifier
        .padding(all = 5.dp)
        .size(200.dp),


        )


}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SalaryCalculationAppTheme {
        ContentsColumn()
    }
}


