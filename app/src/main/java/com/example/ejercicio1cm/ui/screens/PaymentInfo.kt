package com.example.ejercicio1cm.ui.screens

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ejercicio1cm.R
import com.example.ejercicio1cm.utilities.TextBox
import com.example.ejercicio1cm.utilities.pay
import com.example.ejercicio1cm.utilities.selectCard
import kotlin.random.Random

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

@SuppressLint("DiscouragedApi")
@Composable
fun Content(modifier: Modifier,navController:NavController){
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
        mutableFloatStateOf(Random.nextInt(100,5000)+ Random.nextInt(0,100).toFloat()/100)
    }
    Column(
        modifier = modifier.padding(horizontal=20.dp)
    ){
        Text(
            text = stringResource(id = R.string.total),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier= Modifier.padding(vertical=5.dp)
        )
        Text(
            text = "$$total",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color= Color.DarkGray,
            modifier= Modifier.padding(vertical=5.dp)
        )
        Text(
            text= stringResource(id = R.string.select),
            fontSize=18.sp,
            modifier= Modifier.padding(vertical=10.dp)
        )
        selectedCard= selectCard()
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
                visualTransformation = PasswordVisualTransformation(),
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
            modifier = Modifier.height(60.dp)
        )
        Row(
            modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            Button(
                onClick = { pay(context,email,cardNum,cardCVV,cardName,cardDate,selectedCard,total.toString(),navController) },
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
fun Payment(navController: NavController) {
    Scaffold (
        topBar = {TopBar(R.string.title)}
    ){
            paddingValues ->
        val scrollState= rememberLazyListState()
        LazyColumn(
            state = scrollState
        ){
            item{
                Content(modifier= Modifier.padding(paddingValues),navController)
            }
        }
    }
}


