package com.example.ejercicio1cm.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejercicio1cm.R
import kotlin.random.Random

@Composable
fun Success(total: String?, name: String?){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp, vertical = 40.dp)
    ){
        Image(
            painter =
            painterResource(id = R.drawable.baseline_check_circle_24), contentDescription = null,
            modifier = Modifier
                .size(150.dp, 150.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            text= stringResource(R.string.payment_success),
            color= Color.Green,
            lineHeight = 40.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(30.dp))
        val id = Random.nextInt(1000000,100000000)
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            text= stringResource(R.string.transaction_id, id),
            color= Color.LightGray,
            lineHeight = 40.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            text=stringResource(R.string.title),
            textAlign = TextAlign.Left,
            modifier= Modifier.padding(vertical=10.dp)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier= Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ){
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                text= stringResource(R.string.total_success),
            )
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                text="\$$total",
            )
        }
        Row(
            modifier= Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                text= stringResource(R.string.success_name),
            )
            Text(
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                text="$name",
            )
        }
    }
}