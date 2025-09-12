package com.example.classdiary

import android.os.Bundle
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.sp
import com.example.classdiary.data.Student
import com.example.classdiary.data.DataSouce
import com.example.classdiary.ui.theme.AbrilFatface
import com.example.classdiary.ui.theme.ClassDiaryTheme
import com.example.classdiary.ui.theme.Libertinus

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClassDiaryTheme {
                Scaffold(
                    modifier =
                        Modifier.fillMaxSize(),
                    topBar = {
                        ClassDiaryTopAppBar()
                    }
                )
                { innerPadding ->
                    classDiaryApp(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDiaryTopAppBar(modifier: Modifier = Modifier){
    CenterAlignedTopAppBar(
        title = {
                Text(
                    text = "Lista de alunos")
        },
        modifier = Modifier
    )
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
        StudentsList(
            modifier = modifier,
            DataSouce().loadAStudents()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ClassDiaryPreviewDark() {
    ClassDiaryTheme(darkTheme = true) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            classDiaryApp(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ClassDiaryPreview() {
    ClassDiaryTheme(darkTheme = false) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            classDiaryApp(
                modifier = Modifier
                    .padding(innerPadding)
            )
        }
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
    @DrawableRes studentPhoto: Int,
    studentName: String,
    studentCurse: String,
    modifier: Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Padding local do card
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(
            bottomStart = 26.dp,
            topEnd = 26.dp,
            bottomEnd = 1.dp,
            topStart = 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp) // Padding interno
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = studentPhoto),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = studentName,
                    fontSize = 20.sp,
                    fontFamily = AbrilFatface
                )
                Text(
                    text = studentCurse,
                    fontSize = 16.sp,
                    fontFamily = Libertinus
                )
            }
        }
    }
}


