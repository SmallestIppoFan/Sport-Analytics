package com.example.sportapp.screens.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sportapp.data.Resource
import com.example.sportapp.modul.*
import com.example.sportapp.navigation.Screens
import com.example.sportapp.ui.theme.*
import java.util.*
import androidx.compose.ui.unit.sp as sp1

@SuppressLint("ProduceStateDoesNotAssignValue", "NewApi", "SuspiciousIndentation")
@Composable
fun DetailScreen(id:String?,
                 navController: NavController,
                 detailScreenViewModel: DetailScreenViewModel= hiltViewModel(),
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    val result= produceState<Resource<PredictionDetails>>(initialValue = Resource.Loading()){
        if (id != null) {
            value=detailScreenViewModel.getPredictionDetails(id.toInt())
        }
    }

    val headToHead= produceState<Resource<Stats>>(initialValue = Resource.Loading()){
        if (id != null) {
            value=detailScreenViewModel.getHeadToHead(id.toInt())
        }
    }.value
    val selectedChip = remember {
        mutableStateOf(0)
    }

    val isExpandedFirst = remember {
        mutableStateOf(false)
    }
    val isExpandedSecond = remember {
        mutableStateOf(false)
    }
    val isExpandedThird = remember {
        mutableStateOf(false)
    }
    val selectedCard = remember {
        mutableStateOf(0)
    }
    when(headToHead) {
        is Resource.Loading -> {
            Surface(modifier = Modifier.fillMaxSize(), color = DetailScreenBackgroundColor)
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Surface(
                        modifier = Modifier.height(screenHeight / 15),
                        color = DetailScreenBackgroundColor
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack, contentDescription = "Go back",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        navController.navigate(Screens.MainScreen.name)
                                    },
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.weight(0.5f))
                            Text(
                                text = "Матч",
                                color = Color.White,
                                fontFamily = medium,
                                fontSize = 16.sp1
                            )
                            Spacer(modifier = Modifier.weight(0.5f))
                            Icon(
                                imageVector = Icons.Default.Info, contentDescription = "Info screen",
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        navController.navigate(Screens.PerfomanceScreen.name)
                                    },
                                tint = Color.White
                            )

                        }
                    }
                    LoadingTeamInfoBlock()
                    Spacer(modifier = Modifier.height(20.dp))
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                        color = Color.White.copy(alpha = 0.8f)
                    ){

                    }
                }
            }
        }
        else -> {
        Log.d("headToHead", "${headToHead.message.toString()}")

        val headList = headToHead.data?.data?.encounters?.toList()


        data class Events(
            val event: String,
            val kef
            : String,
            val isMiddle
            : Boolean
        )

        val awayTeamStats = headToHead.data?.data?.stats?.away_team

        val homeTeamStatsList = mutableListOf<Stat>()
        val awayTeamStatsList = mutableListOf<Stat>()
        homeTeamStatsList.add(0, Stat("Победы", "${headToHead.data?.data?.stats?.home_team?.won}"))
        homeTeamStatsList.add(
            1,
            Stat("Ничьи", "${headToHead.data?.data?.stats?.home_team?.draw}")
        )
        homeTeamStatsList.add(
            2,
            Stat("Проигрыши", "${headToHead.data?.data?.stats?.home_team?.lost}")
        )

        homeTeamStatsList.add(
            3,
            Stat("Победы", "${headToHead.data?.data?.stats?.home_team?.first_half_win}")
        )
        homeTeamStatsList.add(
            4,
            Stat("Ничьи", "${headToHead.data?.data?.stats?.home_team?.first_half_draw}")
        )
        homeTeamStatsList.add(
            5,
            Stat("Проигрыши", "${headToHead.data?.data?.stats?.home_team?.lost}")
        )

        homeTeamStatsList.add(
            6,
            Stat("Забила", "${headToHead.data?.data?.stats?.home_team?.goals_scored}")
        )
        homeTeamStatsList.add(
            7,
            Stat("Пропустила", "${headToHead.data?.data?.stats?.home_team?.goals_conceived}")
        )
        homeTeamStatsList.add(
            8,
            Stat("Ср Матч", "${headToHead.data?.data?.stats?.home_team?.avg_goals_scored}")
        )
        awayTeamStatsList.add(0, Stat("Победы", "${awayTeamStats?.won}"))
        awayTeamStatsList.add(
            1,
            Stat("Ничьи", "${awayTeamStats?.draw}")
        )
        awayTeamStatsList.add(2, Stat("Проигрыши", "${awayTeamStats?.lost}"))

        awayTeamStatsList.add(3, Stat("Победы", "${awayTeamStats?.first_half_win}"))
        awayTeamStatsList.add(
            4,
            Stat("Ничьи", "${awayTeamStats?.first_half_draw}")
        )
        awayTeamStatsList.add(5, Stat("Проигрыши", "${awayTeamStats?.lost}"))
        awayTeamStatsList.add(6, Stat("Забила", "${awayTeamStats?.goals_scored}"))
        awayTeamStatsList.add(
            7,
            Stat("Пропустила", "${awayTeamStats?.goals_conceived}")
        )
        awayTeamStatsList.add(
            8,
            Stat("Ср Матч", "${awayTeamStats?.avg_goals_scored}")
        )

        Log.d("awayTema", "$awayTeamStatsList")
        Log.d(
            "homeTema",
            "$homeTeamStatsList"
        )

        val eventList = mutableListOf<Events>()
        eventList.add(
            0,
            Events(
                "П1",
                "${result.value.data?.data?.get(0)?.prediction_per_market?.classic?.odds?.`1`}",
                isMiddle = false
            )
        )
        eventList.add(
            1,
            Events(
                "Н",
                "${result.value.data?.data?.get(0)?.prediction_per_market?.classic?.odds?.X}",
                isMiddle = true
            )
        )
        eventList.add(
            2,
            Events(
                "П2",
                "${result.value.data?.data?.get(0)?.prediction_per_market?.classic?.odds?.`2`}",
                isMiddle = false
            )
        )
        eventList.add(
            3,
            Events(
                "1Х",
                "${result.value.data?.data?.get(0)?.prediction_per_market?.classic?.odds?.`1X`}",
                isMiddle = false
            )
        )
        eventList.add(
            4,
            Events(
                "12",
                "${result.value.data?.data?.get(0)?.prediction_per_market?.classic?.odds?.`12`}",
                isMiddle = true
            )
        )
        eventList.add(
            5,
            Events(
                "2Х",
                "${result.value.data?.data?.get(0)?.prediction_per_market?.classic?.odds?.`X2`}",
                isMiddle = false
            )
        )

        Surface(modifier = Modifier.fillMaxSize(), color = DetailScreenBackgroundColor)
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Surface(
                    modifier = Modifier.height(screenHeight / 15),
                    color = DetailScreenBackgroundColor
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, contentDescription = "Go back",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    navController.navigate(Screens.MainScreen.name)
                                },
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(
                            text = "Матч",
                            color = Color.White,
                            fontFamily = medium,
                            fontSize = 16.sp1
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        Icon(
                            imageVector = Icons.Default.Info, contentDescription = "Info screen",
                            modifier = Modifier
                                .size(25.dp)
                                .clickable {
                                    navController.navigate(Screens.PerfomanceScreen.name)
                                },
                            tint = Color.White
                        )

                    }
                }
                val score =
                    if (result.value.data?.data?.get(0)?.result != "") result.value.data?.data?.get(
                        0
                    )?.result else "-"
                Spacer(modifier = Modifier.height(10.dp))
                TeamInfoBlock(
                    homeTeam = "${result.value.data?.`data`?.get(0)?.home_team}",
                    awayTeam = "${result.value.data?.data?.get(0)?.away_team}",
                    score = "${score}",
                    league = "${result.value.data?.`data`?.get(0)?.federation}"
                )
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                ) {
                    Spacer(modifier = Modifier.height(5.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                    ) {
                        Text(
                            text = "Прогнозы",
                            fontFamily = medium,
                            fontSize = 20.sp1,
                            color = PrognozColor
                        )
                        RoundedChipGroup(selectedChip)
                        Spacer(modifier = Modifier.height(5.dp))
                        if (selectedChip.value == 0) {
                            Text(
                                text = "Коэфиценты",
                                fontFamily = medium,
                                fontSize = 20.sp1,
                                color = PrognozColor
                            )
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(10.dp)
                            ) {
                                eventList.forEach { item ->
                                    items(1) {
                                        KefSurface(
                                            event = item.event,
                                            eventKef = item.kef,
                                            isMidle = item.isMiddle
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = "Предсказание",
                                fontFamily = medium,
                                fontSize = 20.sp1,
                                color = PrognozColor
                            )
                            val predictions =
                                result.value.data?.data?.get(0)?.prediction_per_market?.classic?.probabilities
                            val data = remember {
                                listOf(
                                    (roundToOneDecimalPlace(predictions!!.`1`.toFloat() * 100)),
                                    (roundToOneDecimalPlace(predictions.`X`.toFloat() * 100)),
                                    (roundToOneDecimalPlace(predictions.`2`.toFloat() * 100)),
                                    (roundToOneDecimalPlace(predictions.`1X`.toFloat() * 100)),
                                    (roundToOneDecimalPlace(predictions.`12`.toFloat() * 100)),
                                    (roundToOneDecimalPlace(predictions.`X2`.toFloat() * 100))
                                )
                            }
                            val maxWidth = 210.dp
                            HorizontalBarChart(data = data, maxWidth = maxWidth)
                        } else if (selectedChip.value == 1) {
                            Text(
                                text = "Встречи",
                                color = Color.Black,
                                fontFamily = bold,
                                fontSize = 22.sp1
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                            LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                items(headList?.size ?: 0) {
                                    headToHead.data?.let { it1 ->
                                        HeadToHead(
                                            screenHeight = screenHeight,
                                            it1,
                                            index = it
                                        )
                                    }
                                }
                            }


                        } else if (selectedChip.value == 2) {
                            Row(horizontalArrangement = Arrangement.Center) {
                                TeamName(
                                    teamName = "${result.value.data?.data!!.get(0).home_team}",
                                    screenWidth,
                                    screenHeight,
                                    selected = selectedCard.value == 0
                                ) {
                                    selectedCard.value = 0
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                TeamName(
                                    teamName = "${result.value.data?.data!!.get(0).away_team}",
                                    screenWidth,
                                    screenHeight,
                                    selected = selectedCard.value == 1
                                ) {
                                    selectedCard.value = 1
                                }
                            }
                            if (selectedCard.value == 0) {
                                Spacer(modifier = Modifier.height(10.dp))
                                ExpandCard(
                                    isExpanded = isExpandedFirst,
                                    statName = "Результаты",
                                    screenHeight,
                                    screenWidth,
                                    stats = homeTeamStatsList,
                                    index1 = 0,
                                    index2 = 1,
                                    index3 = 2
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                ExpandCard(
                                    isExpanded = isExpandedSecond,
                                    statName = "Первый тайм",
                                    screenHeight,
                                    screenWidth,
                                    stats = homeTeamStatsList,
                                    index1 = 3,
                                    index2 = 4,
                                    index3 = 5
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                ExpandCard(
                                    isExpanded = isExpandedThird,
                                    statName = "Первый тайм",
                                    screenHeight,
                                    screenWidth,
                                    stats = homeTeamStatsList,
                                    index1 = 6,
                                    index2 = 7,
                                    index3 = 8
                                )
                            } else {
                                Spacer(modifier = Modifier.height(10.dp))
                                ExpandCard(
                                    isExpanded = isExpandedFirst,
                                    statName = "Результаты",
                                    screenHeight,
                                    screenWidth,
                                    stats = awayTeamStatsList,
                                    index1 = 0,
                                    index2 = 1,
                                    index3 = 2
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                ExpandCard(
                                    isExpanded = isExpandedSecond,
                                    statName = "Первый тайм",
                                    screenHeight,
                                    screenWidth,
                                    stats = awayTeamStatsList,
                                    index1 = 3,
                                    index2 = 4,
                                    index3 = 5
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                ExpandCard(
                                    isExpanded = isExpandedThird,
                                    statName = "Первый тайм",
                                    screenHeight,
                                    screenWidth,
                                    stats = awayTeamStatsList,
                                    index1 = 6,
                                    index2 = 7,
                                    index3 = 8
                                )

                            }


                        }
                    }

                }

            }
        }
    }
    }
}

@Composable
fun TeamInfoBlock(homeTeam: String,
                  awayTeam: String,
                  score:String,
                  league:String,
                  ) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight= LocalConfiguration.current.screenHeightDp.dp
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight / 6),
        shape = RoundedCornerShape(8.dp),
        color = Color.White
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = league,
                fontFamily = bold,
                fontSize = 22.sp1,
                color = Color.Black
                )
            Spacer(modifier = Modifier.height(22.dp))
            Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = homeTeam,
                        fontSize = 22.sp1,
                        fontFamily = regular,
                        color = Color.Black,
                        softWrap = true,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(120.dp)
                    )
                Spacer(modifier = Modifier.width(20.dp))
                Text(text = score,
                    fontSize = 27.sp1,
                    fontFamily = medium,
                    color= Color.Black
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                        text = awayTeam,
                        fontSize = 22.sp1,
                        fontFamily = regular,
                        color = Color.Black,
                        softWrap = true,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.width(120.dp)
                    )
            }
        }
    }
}

@Composable
fun RoundedChipGroup(selectedChip:MutableState<Int>) {
    Column(
    ) {
        Row(
            modifier = Modifier
                .padding(start = 4.dp, end = 4.dp)
                .horizontalScroll(rememberScrollState(), true)

        ) {
            RoundedChip(
                text = "Шансы",
                isSelected = selectedChip.value == 0,
                onClick = { selectedChip.value = 0 }
            )
            Spacer(modifier = Modifier.width(8.dp))
            RoundedChip(
                text = "Встречи",
                isSelected = selectedChip.value == 1,
                onClick = { selectedChip.value = 1 }
            )
            Spacer(modifier = Modifier.width(8.dp))
            RoundedChip(
                text = "Статистика",
                isSelected = selectedChip.value == 2,
                onClick = { selectedChip.value = 2 }
            )
        }
    }
}

@Composable
fun RoundedChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clickable(onClick = onClick)
            .height(50.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            if (isSelected) {
                Text(
                    text = text,
                    color = PrognozColor,
                    fontFamily = medium,
                    fontSize = 22.sp1,
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp),
                    textDecoration = TextDecoration.Underline
                )
            }
            else{
                Text(
                    text = text,
                    color = PrognozColor.copy(0.3f),
                    fontFamily = regular,
                    fontSize = 22.sp1,
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun KefSurface(event:String,
                eventKef:String,
                isMidle:Boolean
                ) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    var surfcolor=if (isMidle==true) DetailScreenBackgroundColor else EventColor
    Surface(modifier = Modifier
        .width(110.dp)
        .height(40.dp)
        .padding(2.dp),
        color = surfcolor,
        shape = RoundedCornerShape(10.dp)
            ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (isMidle == false) {
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = event,
                    fontSize = 14.sp1,
                    fontFamily = regular,
                    color = Color.Black.copy(alpha = 0.4f),
                    softWrap = true,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.width(25.dp))
                Text(
                    text = eventKef,
                    fontSize = 14.sp1,
                    fontFamily = regular,
                    color = PrognozColor,
                    textAlign = TextAlign.Start,
                )
            }else{
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = event,
                    fontSize = 14.sp1,
                    fontFamily = regular,
                    color = Color.White,
                    softWrap = true,
                    textAlign = TextAlign.Start,
                )
                Spacer(modifier = Modifier.width(screenWidth / 9))
                Text(
                    text = eventKef,
                    fontSize = 14.sp1,
                    fontFamily = regular,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }
    
}

@Composable
fun PredictionSurface(
    probabilities: Double
) {
    val screenHeight=(probabilities*400).toInt()
    Surface(modifier = Modifier
        .width(screenHeight.dp)
        .padding(4.dp)
        .height(30.dp),
        color = DetailScreenBackgroundColor,
        shape = RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp)
        ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
            ) {
            Text(text = "${probabilities*100}%",
                color = Color.White,
                fontFamily = regular,
                fontSize = 13.sp1
            )
        }
    }
}


@Composable
fun HeadToHead(screenHeight: Dp,
               stats:Stats,
               index:Int
               ) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight / 8),
        shape = RoundedCornerShape(12.dp),
        color = DetailScreenBackgroundColor
        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
            ) {
            Column(modifier = Modifier.padding(end = 4.dp)) {
                Text(
                    stats.data.encounters.get(index).fulltime_result,
                    color = Color.White,
                    fontSize = 19.sp1,
                    fontFamily = regular
                )
            }
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .height(screenHeight / 6),
                color = Color.White
            )
            Column(modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth(0.65f)) {
                Text(
                    stats.data.encounters.get(index).home_team,
                    color = Color.White,
                    fontSize = 17.sp1,
                    fontFamily = regular
                )
                Text(
                    stats.data.encounters.get(index).away_team,
                    color = Color.White,
                    fontSize = 17.sp1,
                    fontFamily = regular
                )
            }
                Text(
                    text = stats.data.encounters.get(index).start_date,
                    color = Color.White,
                    fontSize = 14.sp1,
                    fontFamily = regular
                )

        }
    }
}

@Composable
fun TeamName(teamName:String,screenWidth: Dp,screenHeight: Dp, selected:Boolean,onClick: () -> Unit) {
    var backgroundColor=if (selected == true) DetailScreenBackgroundColor else Color.Gray.copy(alpha = 0.4f)
    Surface(modifier = Modifier
        .width(screenWidth / 3)
        .height(screenHeight / 15)
        .clickable {
            onClick()
        }
        ,
        color = backgroundColor,
        shape = RoundedCornerShape(10.dp)
        ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text(text = teamName,
                color = Color.White,
                fontSize = 18.sp1,
                fontFamily = regular,
                softWrap = true,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.8f)
                )
        }
    }
}

@Composable
fun ExpandCard(isExpanded:MutableState<Boolean>,
               statName:String,screenHeight: Dp,screenWidth: Dp,
    stats: List<Stat>,
               index1:Int,
               index2:Int,
               index3:Int,
               ) {
    val icon=if (isExpanded.value==true) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

    Surface(modifier = Modifier
        .fillMaxWidth()) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = statName,
                    fontFamily = regular,
                    color = Color.Black,
                    fontSize = 16.sp1
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(imageVector = icon, contentDescription = "", tint = Color.Gray,
                    modifier = Modifier.clickable {
                        isExpanded.value = !isExpanded.value
                    }
                )

            }
            if (isExpanded.value == true) {
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatCard(
                            screenWidth = screenWidth,
                            screenHeight = screenHeight,
                            stats = stats[index1]
                        )
                        StatCard(
                            screenWidth = screenWidth,
                            screenHeight = screenHeight,
                            stats = stats[index2]
                        )
                        StatCard(
                            screenWidth = screenWidth,
                            screenHeight = screenHeight,
                            stats = stats[index3]
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(screenWidth: Dp,
             screenHeight: Dp,
             stats:Stat
             ) {
    Surface(modifier = Modifier
        .width(screenWidth / 4)
        .height(screenHeight / 14), color =Color.White) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stats.statName,
                color=Color.Black,
                fontFamily = regular,
                fontSize = 14.sp1
            )
            Surface(shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                color = Color.White.copy(alpha = 0.2f)
                ) {
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    Text(
                        text = stats.statPoint.toString(),
                        fontFamily = regular,
                        fontSize = 24.sp1,
                        color = Color.Black
                    )

                }
            }

        }
    }
}


@Composable
fun HorizontalBarChart(data: List<Float>, maxWidth: Dp) {
    val max = data.maxOrNull() ?: 0f
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
        data.forEachIndexed { index, value ->
            Spacer(modifier = Modifier.height(5.dp))
            Bar(value, maxWidth, max, index)
        }
    }
}

@Composable
fun Bar(value: Float, maxWidth: Dp, max: Float, index: Int) {
    val width = (value / max) * maxWidth

    val eventList= listOf<String>("П1","  Н","П2","1X","12","2X")
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = eventList[index],
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onBackground
        )
        Spacer(modifier = Modifier.width(8.dp))
        Canvas(
            Modifier
                .height(35.dp)
                .width(width)) {
            drawRect(DetailScreenBackgroundColor, topLeft = Offset.Zero, size = Size(width.toPx(), 35.dp.toPx()))
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "$value%",
            fontFamily = bold,
            fontSize = 18.sp1
        )
    }
}

fun roundToOneDecimalPlace(number: Float): Float {
    return String.format(Locale.US, "%.1f", number).toFloat()
}

@Composable
fun LoadingTeamInfoBlock() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight= LocalConfiguration.current.screenHeightDp.dp
    Surface(modifier = Modifier
        .fillMaxWidth()
        .height(screenHeight / 6),
        shape = RoundedCornerShape(8.dp),
        color = Color.White.copy(alpha = 0.7f)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Spacer(modifier = Modifier.height(22.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Spacer(modifier = Modifier.width(20.dp))
            }
        }
    }
}