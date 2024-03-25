package com.example.ejercicio1cm

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejercicio1cm.ui.theme.Ejercicio1CMTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ejercicio1CMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Payment()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(@StringRes title:Int) {
    TopAppBar(
        title = { Text(
            text = stringResource(id = title),
            fontSize=22.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        ) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue),
    )
}

@Composable
private fun TextBox(
    @StringRes label: Int,
    keyboardsOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        label = { Text(
            text = stringResource(id = label),
            color=Color.Gray
        )  },
        value = value,
        singleLine = true,
        keyboardOptions = keyboardsOptions,
        onValueChange = onValueChanged,
        modifier=modifier.fillMaxWidth(),
        shape= RoundedCornerShape(12.dp),

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
                    DropdownMenuItem(
                        text = { Text(text = "Visa") },
                        onClick = {
                            card = "Visa"
                            showCard="visa"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "MasterCard") },
                        onClick = {
                            card = "MasterCard"
                            showCard="mastercard"
                            isExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "American Express") },
                        onClick = {
                            card = "Amex"
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
fun pay(context:Context,email:String,num:String,cvv:String,name:String,date:String,selectedCard:String){
    var message =""
    if(validateEmail(email)&& checkLength(num,16)&&
        (checkLength(cvv,3)||checkLength(cvv,4))&&
        checkName(name)&&
        checkDate(date)&&
        selectedCard!="blank"){
        message = if(Random.nextInt(1,5)==1){
            context.getString(R.string.error)
        }else {
            context.getString(R.string.success)
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
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

fun validateEmail(email:String):Boolean{
    return email.isNotEmpty()&&Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun checkLength(text:String,len:Int):Boolean{
    return text.length==len
}

fun checkName(text:String):Boolean{
    return text.contains(Regex("[A-Za-z]"))
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

@SuppressLint("DiscouragedApi")
@Composable
fun Content(modifier: Modifier){
    val context= LocalContext.current

    var cardNum by remember {
        mutableStateOf("")
    }
    var cardDate by remember {
        mutableStateOf("")
    }
    var cardCVV by remember {
        mutableStateOf("")
    }
    var cardName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var selectedCard by remember {
        mutableStateOf("")
    }
    val total by remember{
        mutableFloatStateOf(Random.nextInt(100,5000)+Random.nextInt(0,100).toFloat()/100)
    }
    Column(
        modifier = modifier.padding(horizontal=20.dp)
    ){
        Text(
            text = stringResource(id = R.string.total),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier=Modifier.padding(vertical=5.dp)
        )
        Text(
            text = "$$total",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color=Color.DarkGray,
            modifier= Modifier.padding(vertical=5.dp)
        )
        Text(
            text=stringResource(id = R.string.select),
            fontSize=18.sp,
            modifier= Modifier.padding(vertical=10.dp)
        )
        selectedCard=selectCard()
        Box(
            modifier= Modifier
                .fillMaxWidth()
                .wrapContentSize()
        ) {
            TextBox(
                label = R.string.card_num,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                value = cardNum,
                onValueChanged = {
                    val number=it
                    val trimmed = number.replace(Regex("\\D"), "")
                    if (it.length <= 16) cardNum = trimmed
                },
            )
            if(selectedCard!="blank") {
                val resourceId = context.resources.getIdentifier(selectedCard, "drawable", context.packageName)
                Image(
                    painter = painterResource(id = resourceId), contentDescription = null,
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            TextBox(label = R.string.date,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = cardDate,
                onValueChanged = {
                    newDate ->
                   val formattedDate = if (newDate.length <= 5) {
                       val trimmed = newDate.replace(Regex("\\D"), "")
                       if (trimmed.length >= 2 && !trimmed.contains("/")) {
                           val month = trimmed.substring(0, 2)
                           val year = if (trimmed.length >= 4) trimmed.substring(2, 4) else trimmed.substring(2)
                           "$month/$year"
                       } else {
                           trimmed
                       }
                   } else {
                       cardDate
                   }
                   cardDate = formattedDate
                },
                modifier = Modifier.width(150.dp))
            TextBox(label = R.string.CVV,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = cardCVV,
                onValueChanged = {
                    val cvv=it
                    val trimmed = cvv.replace(Regex("\\D"), "")
                    if(it.length<=4) cardCVV=trimmed
                },
                modifier = Modifier.width(150.dp))
        }

        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = stringResource(id = R.string.nameinfo),
            fontSize=18.sp,
            lineHeight = 10.sp
        )
        TextBox(label = R.string.name,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = cardName,
            onValueChanged = {cardName=it},
            modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = stringResource(id = R.string.contact),
            fontSize=18.sp,
            lineHeight = 10.sp
        )
        TextBox(
            label = R.string.mail,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
            ),
            value = email,
            onValueChanged = { email = it },
        )
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
                Button(
                    onClick = {pay(context,email,cardNum,cardCVV,cardName,cardDate,selectedCard)},
                    modifier= Modifier
                        .padding(vertical = 70.dp)
                        .size(210.dp, 50.dp)
                ) {
                    Text(
                        stringResource(id = R.string.proceed),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
        }
    }
}

@Composable
fun Payment() {
    Scaffold (
        topBar = {TopBar(R.string.title)}
    ){
        paddingValues ->
        Content(modifier=Modifier.padding(paddingValues))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ejercicio1CMTheme {
        Payment()
    }
}