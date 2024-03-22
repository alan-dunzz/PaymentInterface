package com.example.ejercicio1cm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
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
        modifier=modifier,
        shape= RoundedCornerShape(12.dp)
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCard(){
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var card by remember {
        mutableStateOf("")
    }
    Box(
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it },
        ) {
            TextField(
                value = card,
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
                        isExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "MasterCard") },
                    onClick = {
                        card = "MasterCard"
                        isExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text(text = "American Express") },
                    onClick = {
                        card = "American Express"
                        isExpanded = false
                    }
                )
            }
        }
    }
}

fun pay(){
    
}

@Composable
fun Content(modifier: Modifier){
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
    val total by remember{
        mutableFloatStateOf(Random.nextInt(100,5000)+Random.nextInt(0,100).toFloat()/100)
    }
    Column(
        modifier = modifier.padding(20.dp)
    ){
        Text(
            text = stringResource(id = R.string.total),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "$$total",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color=Color.DarkGray,
            modifier= Modifier.padding(vertical=10.dp)
        )
        Text(
            text=stringResource(id = R.string.select),
            fontSize=18.sp,
            modifier= Modifier.padding(vertical=10.dp)
        )
        SelectCard()
        TextBox(label = R.string.card_num,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            value = cardNum,
            onValueChanged = {cardNum=it},
            modifier=modifier.fillMaxWidth())
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
                onValueChanged = {cardDate=it},
                modifier = Modifier.width(150.dp))
            TextBox(label = R.string.CVV,
                keyboardsOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = cardCVV,
                onValueChanged = {cardCVV=it},
                modifier = Modifier.width(150.dp))
        }

        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = stringResource(id = R.string.nameinfo),
            fontSize=18.sp
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
        )
        TextBox(label = R.string.mail,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            value = email,
            onValueChanged = {email=it},
            modifier = Modifier
                .fillMaxWidth()
                .size(50.dp)
        )
        //Spacer(modifier = Modifier.height(90.dp))
        Row(
            modifier=Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
                Button(
                    onClick = {pay()},
                    modifier=Modifier.padding(vertical=35.dp)
                        .size(210.dp,50.dp)
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