package com.hnalovski.readerapp.screens.detail

import android.text.Html
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hnalovski.readerapp.components.ReaderAppBar
import com.hnalovski.readerapp.components.RoundedButton
import com.hnalovski.readerapp.data.Resource
import com.hnalovski.readerapp.model.MBook
import com.hnalovski.readerapp.navigation.ReaderScreens

@Composable
fun ReaderDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: ReaderDetailViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Details",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val bookInfo =
                    produceState<Resource<com.hnalovski.readerapp.model.Item>>(initialValue = Resource.Loading()) {
                        value = viewModel.getBookInfo(bookId)

                    }.value

                if (bookInfo.data == null) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LinearProgressIndicator()
                        Text(text = "Loading..")
                    }

                } else {

                    ShowBookDetails(bookInfo = bookInfo, navController = navController)
                }


            }
        }
    }

}

@Composable
fun ShowBookDetails(
    bookInfo: Resource<com.hnalovski.readerapp.model.Item>,
    navController: NavController
) {

    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id


    Card(
        modifier = Modifier.padding(34.dp),
        shape = CircleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = bookData?.imageLinks?.thumbnail),
            contentDescription = "Book image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(9.dp)
        )
    }
    Text(
        text = bookData?.title.toString(),
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.titleLarge,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )
    Text(
        text = "Authors: ${bookData?.authors}",
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.titleMedium
    )
    Text(
        text = "Page count ${bookData?.pageCount}",
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.titleMedium
    )
    Text(
        text = "Categories: ${bookData?.categories}",
        modifier = Modifier.padding(5.dp),
        style = MaterialTheme.typography.titleMedium,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )
    Text(
        text = "Published: ${bookData?.publishedDate}",
        modifier = Modifier.padding(10.dp),
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(5.dp))

    val cleanDescription = HtmlCompat.fromHtml(
        bookData?.description.toString(),
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()


    val localDims = LocalContext.current.resources.displayMetrics

    Surface(
        modifier = Modifier
            .padding(5.dp)
            .height(localDims.heightPixels.dp.times(0.09f)),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        LazyColumn(modifier = Modifier.padding(3.dp)) {
            items(count = 1) {
                Text(text = cleanDescription)
            }
        }
    }


    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundedButton(onPress = {
            val book = MBook(
                title = bookData?.title,
                authors = bookData?.authors.toString(),
                description = bookData?.description,
                categories = bookData?.categories.toString(),
                notes = "",
                photoUrl = bookData?.imageLinks?.thumbnail,
                publishedDate = bookData?.publishedDate,
                pageCount = bookData?.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            saveToFirebase(book, navController)
        }, label = "Save")

        RoundedButton(onPress = { navController.popBackStack() }, label = "Cancel")
    }
}

fun saveToFirebase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()) {
        dbCollection.add(book).addOnSuccessListener { documentRef ->
            val docId = documentRef.id
            dbCollection.document(docId).update(hashMapOf("id" to docId) as Map<String, Any>)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }

                }.addOnFailureListener{
                    Log.d("Error", "saveToFirebase: $it")
                }
        }
    } else {

    }
}
