package presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import domain.model.FlightInfo
import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.ic_ellipse
import findtravelnow_kmm.shared.generated.resources.img_travel_person
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.FlightInfoItem
import presentation.theme.Black_10
import presentation.theme.Orange_55
import presentation.theme.Orange_alpha_50
import presentation.theme.Red_48
import presentation.theme.strings.Strings
import util.asState

@Composable
fun HomeScreen(
    uiStateHolder: HomeUiStateHolder,
    onNavigateTop5Flights: () -> Unit,
    onNavigateCategory: (CategoryData) -> Unit,
    onNavigateFlightInfo: (FlightInfo) -> Unit,
) {
    val uiState by uiStateHolder.uiState.asState()
    HomeScreen(
        uiState = uiState,
        onNavigateTop5Flights = onNavigateTop5Flights,
        onNavigateCategory = onNavigateCategory,
        onNavigateFlightInfo = onNavigateFlightInfo,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeScreenUiState,
    onNavigateTop5Flights: () -> Unit,
    onNavigateCategory: (CategoryData) -> Unit,
    onNavigateFlightInfo: (FlightInfo) -> Unit,
) {

    val systemBarPaddingValues = WindowInsets.systemBars.asPaddingValues()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 30.dp,
                end = 30.dp,
                top = systemBarPaddingValues.calculateTopPadding()
            ),

        ) {
        item { Spacer(modifier = Modifier.height(36.dp)) }
        item { TopTitleSection(modifier = Modifier.fillMaxWidth()) }
        item { Spacer(modifier = Modifier.height(20.dp)) }
        item {
            CardViewBanner(
                onClickBookNow = {
                onNavigateCategory(uiState.categories[0])
            })
        }

        item { CategoriesTitle() }
        item {
            CategoriesSection(
                modifier = Modifier.padding(top = 18.dp),
                categories = uiState.categories,
                onClickCategory = { categoryData ->
                    onNavigateCategory(categoryData)
                }
            )
        }

        top5FlightsSection(
            modifier = Modifier.padding(top = 18.dp),
            topFlightInfoList = uiState.topFlightInfoList,
            onClickViewAll = onNavigateTop5Flights,
            onNavigateFlightInfo = onNavigateFlightInfo
        )
        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

private fun LazyListScope.top5FlightsSection(
    modifier: Modifier = Modifier,
    topFlightInfoList: List<FlightInfo>,
    onClickViewAll: () -> Unit,
    onNavigateFlightInfo: (FlightInfo) -> Unit,
) {
    item {
        Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = Strings.top_5_flights,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = {
                    onClickViewAll()
                }
            ) {
                Text(
                    text = Strings.view_all,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Start,
                )
            }
        }
    }

    items(topFlightInfoList) {
        FlightInfoItem(
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            flightInfo = it,
            onClickItem = {
                onNavigateFlightInfo(it)
            }
        )
    }


}

@Composable
fun CategoriesTitle() {
    Text(
        modifier = Modifier.padding(top = 18.dp),
        text = Strings.our_categories,
        style = MaterialTheme.typography.titleSmall,
        color = Color.Black,
        textAlign = TextAlign.Start,
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoriesSection(
    modifier: Modifier = Modifier,
    categories: List<CategoryData>,
    onClickCategory: (CategoryData) -> Unit,
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        categories.forEach {
            CategoryItem(
                categoryData = it,
                modifier = Modifier.sizeIn(
                    minWidth = 100.dp,
                    minHeight = 100.dp,
                    maxWidth = 100.dp,
                    maxHeight = 100.dp
                ),
                onClickCategoryData = {
                    onClickCategory(it)
                }
            )
        }
    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CategoryItem(
    modifier: Modifier = Modifier,
    categoryData: CategoryData,
    onClickCategoryData: () -> Unit,
) {

    val shape = RoundedCornerShape(11.dp)
    val backgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val borderColor = Orange_alpha_50

    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = shape
            )
            .background(backgroundColor, shape)
            .clip(shape)
            .clickable { onClickCategoryData() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(14.dp))
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(categoryData.iconRes),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = categoryData.title,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CardViewBanner(modifier: Modifier = Modifier, onClickBookNow: () -> Unit) {
    val shape = RoundedCornerShape(9.dp)
    val gradient = Brush.verticalGradient(
        0f to Red_48, 1f to Orange_55
    )
    Box(
        modifier = modifier.fillMaxWidth().clip(shape).background(
            brush = gradient, shape = shape
        ).padding(start = 16.dp)
    ) {
        Box(Modifier.align(Alignment.TopEnd).fillMaxWidth(0.5f)) {

            Image(
                painter = painterResource(Res.drawable.ic_ellipse),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 4.dp, bottom = 11.dp),
                contentScale = ContentScale.Fit,

                )
            Image(
                painter = painterResource(Res.drawable.img_travel_person),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 19.dp),
                contentScale = ContentScale.Crop,
            )


        }

        Column(modifier = Modifier.fillMaxWidth(0.58f).padding(top = 30.dp)) {
            Text(
                text = Strings.home_screen_banner_text,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            val buttonShape = RoundedCornerShape(8.dp)
            Button(modifier = Modifier.padding(top = 16.dp).clip(buttonShape)
                .background(Black_10, buttonShape),
                shape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Black_10
                ),
                onClick = { onClickBookNow() }) {
                Text(
                    text = Strings.btn_book_now,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

        }

    }
}

@Composable
private fun TopTitleSection(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(0.7f),
            text = Strings.home_screen_title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
