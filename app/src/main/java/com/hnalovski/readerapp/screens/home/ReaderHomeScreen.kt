package com.hnalovski.readerapp.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.hnalovski.readerapp.components.FABContent
import com.hnalovski.readerapp.components.ListCard
import com.hnalovski.readerapp.components.ReaderAppBar
import com.hnalovski.readerapp.components.TitleSection
import com.hnalovski.readerapp.model.MBook
import com.hnalovski.readerapp.navigation.ReaderScreens

@Composable
fun ReaderHomeScreen(navController: NavHostController) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Reader", navController = navController)
    }, floatingActionButton = {
        FABContent {}
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeContent(navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController) {
    val listOfBooks =
        listOf(
            MBook(id = "fsdaf", title = "ASDAS", authors = "Everyone", notes = null),
            MBook(id = "bxcv", title = "agewg", authors = "gdsagae", notes = null)
        )
    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty()) {
        email.split("@")[0]
    } else {
        "N/A"
    }
    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "You're reading \n" + "activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.StatsScreen.name)
                        }
                        .size(45.dp), tint = Color.Green
                )
                Text(
                    text = currentUserName,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                HorizontalDivider()
            }
        }
        ReadingRightNowArea(books = listOf(), navController = navController)
        TitleSection(label = "Reading List")

        BookListArea(listOfBooks = listOfBooks, navController = navController)
    }
}

@Composable
fun BookListArea(listOfBooks: List<MBook>, navController: NavController) {
    HorizontalScrollableComponent(listOfBooks) {

    }
}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>, onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .horizontalScroll(scrollState)
    ) {
        for (book in listOfBooks) {
            ListCard(book) {
                onCardPressed(it)
            }
        }
    }
}


@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
    ListCard()
}

