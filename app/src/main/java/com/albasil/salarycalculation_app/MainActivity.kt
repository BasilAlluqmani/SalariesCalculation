package com.albasil.salarycalculation_app

import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.albasil.salarycalculation_app.ui.theme.SalaryCalculationAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SalaryCalculationAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumn()


                }


            }
        }
    }
}


@Composable
fun LazyColumn() {

    Column() {


        SpinnerDays()


        WorkHours()



        DaysOfAbsent()




        ExtraOffDays()

        TotalSalary()





     //   CalculaterSalary()


    }


}


var daysOfMonth:String = ""
var numberHours :String = ""


var numberDaysOfAbsent :String =""
var numberExtraDaysOff :String =""


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
        Icons.Filled.ArrowDropDown //it requires androidx.compose.material:material-icons-extended
    else
        Icons.Filled.KeyboardArrowDown

    Column() {
        daysOfMonth = selectedNumberDays

        OutlinedTextField(
            value = selectedNumberDays,
            onValueChange = { selectedNumberDays = it },
            // enabled = false,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Selected Number Days Of Month") },
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
        // enabled = false,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text("WorK Hours") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),


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
        label = {
            Text("Days Of Absent") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    numberDaysOfAbsent = daysOfAbsent

}


@Composable
fun ExtraOffDays(){


    var numberExtraOffDays by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = numberExtraOffDays,
        onValueChange = { numberExtraOffDays = it },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text("Extra Days Off") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
    numberExtraDaysOff =numberExtraOffDays
}





@Composable
fun TotalSalary() {

    var employeeSalary by remember {
        mutableStateOf("")
    }

    Column() {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = employeeSalary,
            onValueChange = { employeeSalary = it },
            label = { Text(text = "Enter Employee Salary") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )



        var DailyPrice by remember {
            mutableStateOf("${employeeSalary}")
        }
        var HourlyPrice by remember {
            mutableStateOf("")
        }
        var DaysOfAbsent by remember {
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
            if (!employeeSalary.isNullOrEmpty() && !daysOfMonth.isNullOrEmpty() && !numberHours.isNullOrEmpty()) {


                //Calucletor------------------


                DailyPrice = "${employeeSalary.toDouble() / daysOfMonth.toInt()}"

                HourlyPrice = "${DailyPrice.toDouble() / numberHours.toInt()} "



                if (!numberExtraDaysOff.isNullOrEmpty()) {
                    DaysOff = "${(numberExtraDaysOff.toInt() * DailyPrice.toDouble()) *1.50 }"

                }else{
                    DaysOff = "0"


                }


                DaysOfAbsent = if (!numberDaysOfAbsent.isNullOrEmpty()){
                    "${(DailyPrice.toDouble() * numberDaysOfAbsent.toInt())}"
                }else{
                    "0"
                }

               TotalSalary= "${(employeeSalary.toDouble() - DaysOfAbsent.toDouble()) + (DaysOff.toDouble()) }"

            } else {
                DailyPrice = "You must enter Employee and Days Of Month !!!"
            }

        }
        ) {
            Text(text = "Cal")
        }

        Text(text = " Salary : $employeeSalary ")
        Text(text = " Daily $DailyPrice  Hours $HourlyPrice  ")

        Text(text = " Number Days Of Absent: -$DaysOfAbsent")

        Text(text = " Total Extra Days Off: $DaysOff")



        Text(text = " TotalSalary  $$TotalSalary")






    }


}


@Composable
fun CalculaterSalary() {


    OutlinedButton(onClick = { Log.e("BUTTON", "")



    }) {


        Text(text = "CLEAR")

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SalaryCalculationAppTheme {

        TotalSalary()
    }
}


