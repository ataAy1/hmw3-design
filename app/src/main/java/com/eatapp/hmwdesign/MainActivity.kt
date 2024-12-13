package com.eatapp.hmwdesign

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eatapp.hmwdesign.ui.theme.HmwdesignTheme
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HmwdesignTheme {
                val darkTheme = isSystemInDarkTheme()
                Scaffold(
                    topBar = { AppBar(darkTheme = darkTheme) },
                    modifier = Modifier.fillMaxSize(),
                    containerColor = if (darkTheme) colorResource(id = R.color.TextBackgroundLight) else colorResource(id = R.color.AnaRenk)
                ) { innerPadding ->
                    HomeContent(modifier = Modifier.padding(innerPadding), darkTheme = darkTheme)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(darkTheme: Boolean) {
    val configuration = LocalConfiguration.current
    val ekranGenisligi = configuration.screenWidthDp
    val titlePadding = if (ekranGenisligi > 600) 32.dp else 16.dp

    val titleColor = if (darkTheme) colorResource(id = R.color.white) else colorResource(id = R.color.FavRengi)
    val appBarColor = if (darkTheme) colorResource(id = R.color.purple_700) else colorResource(id = R.color.teal_700)

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = titleColor,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = titlePadding),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.pacifico))
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = appBarColor
        )
    )
}

@Composable
fun HomeContent(modifier: Modifier = Modifier, darkTheme: Boolean) {
    val backgroundColor = if (darkTheme) {
        colorResource(id = R.color.white)
    } else {
        colorResource(id = R.color.AnaRenk)
    }

    val configuration = LocalConfiguration.current
    val ekranGenisligi = configuration.screenWidthDp

    val itemsPerRow = if (ekranGenisligi > 600) 3 else 2

    val context = LocalContext.current
    val foodNames = context.resources.getStringArray(R.array.food_names).toList()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(backgroundColor)
    ) {
        Image(
            painter = painterResource(id = R.drawable.yemek_banner),
            contentDescription = "Banner",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        CategoryButtonRow(darkTheme = darkTheme)

        val foodImages = listOf(
            R.drawable.yemek1, R.drawable.yemek2,
            R.drawable.yemek3, R.drawable.yemek4,
            R.drawable.yemek5, R.drawable.yemek6
        )

        FoodImageGrid(images = foodImages, foodNames = foodNames, imagesPerRow = itemsPerRow, darkTheme = darkTheme)
    }
}

@Composable
fun CategoryButtonRow(darkTheme: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CategoryButton(text = stringResource(id = R.string.hot), darkTheme = darkTheme)
        CategoryButton(text = stringResource(id = R.string.popular), darkTheme = darkTheme)
        CategoryButton(text = stringResource(id = R.string.trending), darkTheme = darkTheme)
        CategoryButton(text = stringResource(id = R.string.fresh), darkTheme = darkTheme)
    }
}

@Composable
fun CategoryButton(text: String, darkTheme: Boolean) {
    val buttonBackgroundColor = if (darkTheme) {
        colorResource(id = R.color.ic_launcher_background)
    } else {
        colorResource(id = R.color.FavRengi)
    }

    val buttonTextColor = if (darkTheme) {
        colorResource(id = R.color.DarkModuRenk)
    } else {
        colorResource(id = R.color.Siyah)
    }

    Button(
        onClick = { /* No action needed */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackgroundColor,
            contentColor = buttonTextColor
        ),
        modifier = Modifier
            .height(40.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.W900)
    }
}

@Composable
fun FoodImageGrid(images: List<Int>, foodNames: List<String>, imagesPerRow: Int = 2, darkTheme: Boolean) {
    val rows = images.chunked(imagesPerRow)
    Column(modifier = Modifier.fillMaxWidth()) {
        rows.forEachIndexed { index, rowImages ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                rowImages.forEachIndexed { i, imageRes ->
                    FoodImage(imageRes, foodNames[index * imagesPerRow + i], darkTheme)
                }
            }
        }
    }
}

@Composable
fun FoodImage(imageRes: Int, foodName: String, darkTheme: Boolean) {
    var quantity by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .size(180.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(vertical = 2.dp, horizontal = 4.dp)
        ) {
            Text(
                text = foodName,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
        ) {

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )


            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite",
                tint = colorResource(id = R.color.FavRengi),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(2.dp)
                    .size(26.dp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            IconButton(
                onClick = { if (quantity > 0) quantity-- },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Decrease Quantity"
                )
            }

            Text(
                text = "$quantity",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )

            IconButton(
                onClick = { quantity++ },
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "Increase Quantity"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HmwdesignTheme {
        MainActivity()
    }
}
