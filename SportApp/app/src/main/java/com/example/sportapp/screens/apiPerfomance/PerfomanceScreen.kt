package com.example.sportapp.screens.apiPerfomance

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.sportapp.animation.CustomComponent
import com.example.sportapp.data.Resource
import com.example.sportapp.modul.PerfomanceDetails
import com.example.sportapp.navigation.Screens
import com.example.sportapp.ui.theme.*

@Composable
fun PerfomanceScreen(
    navController: NavController,
    viewModel: PerfomanceScreenViewModel = hiltViewModel(),
) {
    val apiPerfomance =
        produceState<Resource<PerfomanceDetails>>(initialValue = Resource.Loading()) {
            value = viewModel.getApiPerfomance()
        }.value
    when (apiPerfomance) {
        is Resource.Loading -> {
            Surface(modifier = Modifier.padding(top = 20.dp,
                start = 10.dp, end = 10.dp).fillMaxSize(),
            shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
                color = Color.White.copy(0.9f)
                ) {

            }
        }
        is Resource.Success ->{
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            val yesterday = ((apiPerfomance.data?.data?.accuracy?.yesterday)?.times(100))?.toInt()
            val last7Days = ((apiPerfomance.data?.data?.accuracy?.last_7_days)?.times(100))?.toInt()
            val last14Days = ((apiPerfomance.data?.data?.accuracy?.last_14_days)?.times(100))?.toInt()
            val last30Days = ((apiPerfomance.data?.data?.accuracy?.last_30_days)?.times(100))?.toInt()
            Log.d("ZAZAZA", "$last7Days")
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = DetailScreenBackgroundColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TopAppBar(screenHeight) {
                        navController.popBackStack()
                    }
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Точность прогнозов",
                                fontSize = 24.sp,
                                color = PrognozColor,
                                fontFamily = bold
                            )
                            Spacer(modifier = Modifier.weight(0.3f))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ApiPerfomanceDetails(date = "Вчера",
                                    perfomance = yesterday,
                                    total = apiPerfomance.data!!.data.details.yesterday.total,
                                    won = apiPerfomance.data.data.details.yesterday.won,
                                    lost = apiPerfomance.data.data.details.yesterday.lost
                                    )
                                ApiPerfomanceDetails(
                                    date="За 7 Дней",
                                    perfomance = last7Days,
                                    total = apiPerfomance.data.data.details.last_7_days.total,
                                    won = apiPerfomance.data.data.details.last_7_days.won,
                                    lost = apiPerfomance.data.data.details.last_7_days.lost
                                )
                            }
                            Spacer(modifier = Modifier.weight(0.2f))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                ApiPerfomanceDetails(
                                    date = "За 14 Дней",
                                    perfomance =last14Days,
                                    total = apiPerfomance.data?.data?.details?.last_14_days!!.total,
                                    won = apiPerfomance.data.data.details.last_14_days.won,
                                    lost = apiPerfomance.data.data.details.last_14_days.lost
                                )
                                ApiPerfomanceDetails(
                                    date ="За 30 дней" ,
                                    perfomance = last30Days,
                                    total = apiPerfomance.data.data.details.last_30_days.total,
                                    won = apiPerfomance.data.data.details.last_30_days.won,
                                    lost =apiPerfomance.data.data.details.last_30_days.lost
                                )
                            }
                            Spacer(modifier = Modifier.weight(0.6f))
                        }

                    }
                }
            }
        }
        else -> {

        }
    }
}

@Composable
private fun ApiPerfomanceDetails(
    date:String,
    perfomance: Int?,
    total:Int,
    won:Int,
    lost:Int
) {
    Column {
        Text(
            text = "$date",
            fontFamily = medium,
            fontSize = 20.sp,
            color = PrognozColor
        )
        Spacer(modifier = Modifier.height(5.dp))
        CustomComponent(indicatorValue = perfomance!!)
        Text(
            "Total : $total",
            fontFamily = bold,
            fontSize = 14.sp
        )
        Text(
            "Won : ${lost}",
            fontFamily = regular,
            fontSize = 14.sp
        )
        Text(
            "Lost : ${lost}",
            fontFamily = regular,
            fontSize = 14.sp
        )
    }
}


@Composable
fun TopAppBar(screenHeight: Dp,
              navigate:() ->Unit
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
                        navigate()
                    }
                ,
                tint = Color.White
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = "Матч",
                color = Color.White,
                fontFamily = medium,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(0.6f))
        }

    }
}


@Composable
fun DotsFlashing() {
    val minAlpha = 0.1f
    @Composable
    fun Dot(
        alpha: Float
    ) = Spacer(
        Modifier
            .size(24.dp)
            .alpha(0.6f)
            .background(
                color = MaterialTheme.colors.primary,
                shape = CircleShape
            )
    )
    val infiniteTransition = rememberInfiniteTransition()
    @Composable
    fun animateAlphaWithDelay(delay: Int) = infiniteTransition.animateFloat(
        initialValue = minAlpha,
        targetValue = minAlpha,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 300 * 4
                minAlpha at delay with LinearEasing
                1f at delay + 500 with LinearEasing
                minAlpha at delay + 500 * 2
            }
        )
    )
    val alpha1 by animateAlphaWithDelay(0)
    val alpha2 by animateAlphaWithDelay(300)
    val alpha3 by animateAlphaWithDelay(300 * 2)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val spaceSize = 2.dp
        Dot(alpha1)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha2)
        Spacer(Modifier.width(spaceSize))
        Dot(alpha3)
    }
}


