package com.example.ejercicio1cm.utilities

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ejercicio1cm.R
import kotlin.random.Random
import com.example.ejercicio1cm.navigation.Screen

@Composable
fun TextBox(
    @StringRes label: Int,
    keyboardsOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None
){
    OutlinedTextField(
        label = { Text(
            text = stringResource(id = label),
            color= Color.Gray
        )  },
        value = value,
        singleLine = true,
        keyboardOptions = keyboardsOptions,
        onValueChange = onValueChanged,
        modifier=modifier.fillMaxWidth(),
        shape= RoundedCornerShape(12.dp),
        visualTransformation=visualTransformation
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun selectCard():String{
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var card by remember {
        mutableStateOf("")
    }
    var showCard by remember {
        mutableStateOf("blank")
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier=Modifier.padding(vertical = 10.dp)
    ) {
        RadioButton(
            selected = true,
            onClick = {},
        )
        Text(
            text = stringResource(R.string.credit_card_rd),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color= Color(0,0,110)
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                modifier=Modifier.size(160.dp,50.dp)
            ) {
                TextField(
                    value =  card,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor()
                )
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    val visa=stringResource(R.string.visa)
                    val mastercard=stringResource(R.string.mastercard)
                    val american_express=stringResource(R.string.american_express)
                    val amex= stringResource(id = R.string.amex)
                    DropdownMenuItem(
                        text = { Text(text = visa) },
                        onClick = {
                            card = visa
                            showCard="visa"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = mastercard) },
                        onClick = {
                            card = mastercard
                            showCard="mastercard"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = american_express) },
                        onClick = {
                            card = amex
                            showCard="american_express"
                            isExpanded = false
                        }
                    )
                }
            }
        }
    }
    return showCard
}


fun pay(context: Context, email:String, num:String, cvv:String, name:String, date:String, selectedCard:String,total:String,navController:NavController){
    var message =""
    var success = false
    if(validateEmail(email)&& checkLength(num,16)&&
        (checkLength(cvv,3)||checkLength(cvv,4))&&
        checkName(name)&&
        checkDate(date)&&
        selectedCard!="blank"){
        if(Random.nextInt(1,5)==1){
            message = context.getString(R.string.error)
        }else {
            message = context.getString(R.string.success)
            success=true
        }
    }else if(!validateEmail(email)){
        message=context.getString(R.string.email_fail)
    }else if(!checkLength(num,16)) {
        message = context.getString(R.string.number_fail)
    }else if(!(checkLength(cvv,3)||checkLength(cvv,4))) {
        message = context.getString(R.string.cvv_fail)
    }else if(!checkName(name)){
        message= context.getString(R.string.name_fail)
    } else if(!checkDate(date)){
        message= context.getString(R.string.date_fail)
    }else if(selectedCard=="blank"){
        message= context.getString(R.string.selection_fail)
    }
    Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    if(success==true){
        navController.navigate(Screen.SuccessScreen.withArgs(total,name))
    }
}

fun validateEmail(email:String):Boolean{
    return email.isNotEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun checkLength(text:String,len:Int):Boolean{
    return text.length==len
}

fun checkName(text:String):Boolean{
    return text.contains(Regex("[A-Za-z]"))&&!text.contains(Regex("[0-9_.-]"))
}

fun checkDate(text: String):Boolean{
    return if(text.isNotEmpty()&&text.length==5) {
        val month = (text.substring(0, 2)).toInt()
        val year = (text.substring(3, 5)).toInt()
        (month in 1..12 && year in 24..50 && !(month < 4 && year == 24))
    }else{
        false
    }
}

