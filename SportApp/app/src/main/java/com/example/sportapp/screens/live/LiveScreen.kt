package com.example.sportapp.screens.live

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.burgermenu.model.DataX
import com.example.sportapp.R
import com.example.sportapp.navigation.Screens
import com.example.sportapp.screens.detail.KefSurface
import com.example.sportapp.ui.theme.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LiveScreen(navController: NavController,
               viewModel: LiveScreenViewModel= hiltViewModel()
               ) {
    var list = emptyList<DataX>()
    list = viewModel.data.value.data!!.data.toList()
    if (list.isNullOrEmpty()) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            color = Color.Gray.copy(alpha = 0.2f),
        ){
            Column() {
                Surface(
                    modifier = Modifier.height(50.dp),
                    color = DetailScreenBackgroundColor
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(
                            text = "Главная",
                            color = Color.White,
                            fontFamily = medium,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                    }

                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Матчи сегодня",
                        color = PrognozColor,
                        fontSize = 22.sp,
                        fontFamily = bold
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        items(10) {
                                LoadingCard()
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Предстоящие матчи",
                        color = PrognozColor,
                        fontSize = 16.sp,
                        fontFamily = bold
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(10) {
                            LoadingComingMatchesCard()
                        }
                    }
                }
            }

        }
    } else {
//        Log.d("ListZAZA", "$list")
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            color = Color.Gray.copy(alpha = 0.2f),
        ) {
            Column() {
                Surface(
                    modifier = Modifier.height(50.dp),
                    color = DetailScreenBackgroundColor
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(
                            text = "Главная",
                            color = Color.White,
                            fontFamily = medium,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                    }

                }
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Матчи сегодня",
                        color = PrognozColor,
                        fontSize = 22.sp,
                        fontFamily = bold
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        items(list.size) {
                            TodayMatchesCard(dataX = list[it])

                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Предстоящие матчи",
                        color = PrognozColor,
                        fontSize = 16.sp,
                        fontFamily = bold
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(list.size) {
                            ComingMatchesCard(
                                dataX = list[it]
                            ) {
                                val id = list[it].id
                                navController.navigate(Screens.DetailScreen.name + "/${id}") {
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodayMatchesCard(dataX: DataX) {
    val date= convertTimeFromUtcPlus1ToUtcPlus6(dataX.start_date)
    val convertedTime= getTimeFromDateTime(date)
    Log.d("COSSCS","$convertedTime")
    Surface(elevation = 4.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .height(160.dp)
            .width(160.dp)
        ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp)
            ) {
            Row() {
                Surface(shape = RoundedCornerShape(4.dp),
                        modifier = Modifier
                            .width(50.dp)
                            .height(30.dp),
                    color = DateColor
                    ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                        Text(text = convertedTime,
                            fontSize = 12.sp,
                            fontFamily = regular,
                            color = PrognozColor
                            )
                    }
                }
                Spacer(modifier = Modifier.weight(0.8f))
                Image(painter = painterResource(id = R.drawable.ball), contentDescription = "", modifier = Modifier.size(60.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text =dataX.home_team,
                    fontSize = 14.sp,
                    color = PrognozColor,
                    fontFamily = regular
                    )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text =dataX.away_team,
                    fontSize = 14.sp,
                    color = PrognozColor,
                    fontFamily = regular
                )

            }

        }

    }
}

@Composable
fun ComingMatchesCard(
    dataX: DataX
    ,navigate: () ->Unit
) {
   Surface(elevation = 3.dp,
       shape = RoundedCornerShape(8.dp),
       modifier = Modifier
           .height(70.dp)
           .fillMaxWidth()
           .clickable {
               navigate()
           }
       )
   {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 14.dp),
            verticalArrangement = Arrangement.Center
            ) {
         Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,

             ) {
             Text(
                 dataX.home_team,
                 textAlign = TextAlign.End,
                 softWrap = true,
                 color = PrognozColor,
                 fontSize = 14.sp,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis
                 )
             Text(
                 "  vs  ",
                 textAlign = TextAlign.End,
                 softWrap = true,
                 color = PrognozColor,
                 fontSize = 14.sp
             )
             Text(
                 dataX.away_team,
                 textAlign = TextAlign.Start,
                 softWrap = true,
                 color = PrognozColor,
                 fontSize = 14.sp ,
                 maxLines = 1,
                 overflow = TextOverflow.Ellipsis
             )
         }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                KefSurface(event = "П1", eventKef ="${dataX.odds.`1`}" , isMidle = false)
                KefSurface(event = "X", eventKef = "${dataX.odds.X}", isMidle = true)
                KefSurface(event = "П2", eventKef ="${dataX.odds.`2`}" , isMidle = false)
            }


        }
   } 
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertTimeFromUtcPlus1ToUtcPlus6(dateTimeString: String): String {
    val utcPlus1ZoneId = ZoneId.of("UTC+1")
    val utcPlus6ZoneId = ZoneId.of("UTC+6")

    val formatterInput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val formatterOutput = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    val utcPlus1DateTime = LocalDateTime.parse(dateTimeString, formatterInput)
        .atZone(utcPlus1ZoneId)
        .toOffsetDateTime()

    val utcPlus6DateTime = utcPlus1DateTime.withOffsetSameInstant(ZoneOffset.ofHours(6))

    return utcPlus6DateTime.format(formatterOutput)
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("NewApi")
fun getTimeFromDateTime(dateTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val dateTimeParsed = LocalDateTime.parse(dateTime, formatter)
    return dateTimeParsed.format(DateTimeFormatter.ofPattern("HH:mm"))
}


@Composable
fun LoadingCard() {
    Surface(elevation = 4.dp,
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .height(160.dp)
            .width(160.dp)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp)
        ) {
            Row() {
                Surface(shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .width(50.dp)
                        .height(30.dp),
                    color = DateColor
                ) {
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    }
                }
                Spacer(modifier = Modifier.weight(0.8f))
                Image(painter = painterResource(id = R.drawable.ball), contentDescription = "", modifier = Modifier.size(60.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
            }

        }
    }
}

@Composable
fun LoadingComingMatchesCard() {
    Surface(elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                ){}
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
            }


        }
    }
}