package com.example.classdiary

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.classdiary.data.Student
import com.example.classdiary.data.DataSouce
import com.example.classdiary.ui.theme.ClassDiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClassDiaryTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    classDiaryApp(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun classDiaryApp(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(16.dp),

        ) {
        StudentsList(modifier = modifier,
            DataSouce().loadAStudents())
    }
}

@Composable
fun StudentsList(
    modifier: Modifier = Modifier,
    studentList: List<Student>
) {
    LazyColumn(modifier = modifier) {
        items(studentList) { student ->
            StudentCard(
                modifier = modifier,
                studentPhoto = student.photo,
                studentName = student.name,
                studentCurse = student.curse
            )
        }
    }

}

@Composable
fun StudentCard(
    modifier: Modifier = Modifier,
    @DrawableRes studentPhoto: Int,
    studentName: String,
    studentCurse: String,
) {

    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = studentPhoto),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
            )

            Column {
                Text(text = studentName)
                Text(text = studentCurse)
            }

        }


    }

}

