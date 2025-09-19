package com.example.classdiary

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.classdiary.data.Student
import com.example.classdiary.data.DataSouce
import com.example.classdiary.ui.theme.AbrilFatface
import com.example.classdiary.ui.theme.ClassDiaryTheme
import com.example.classdiary.ui.theme.Libertinus
import org.w3c.dom.Text

class MainActivity : ComponentActivity() {
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        enableEdgeToEdge()
        setContent {
            ClassDiaryTheme {
                Scaffold(
                     topBar = {
                        ClassDiaryTopAppBar()
                    }) { innerPadding ->
                    classDiaryApp(
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassDiaryTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Lista de alunos"
            )
        }, modifier = Modifier
    )
}

@Composable
@Preview
fun classDiaryApp(modifier: Modifier = Modifier) {
    val layoutDirection = LocalLayoutDirection.current
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            ),

        ) {
        StudentsList(
            modifier = modifier, DataSouce().loadAStudents()
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ClassDiaryPreviewDark() {
    ClassDiaryTheme(darkTheme = true) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            classDiaryApp(
                modifier = Modifier.padding(innerPadding)
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
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun StudentsList(
    modifier: Modifier = Modifier, studentList: List<Student>
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
fun StudentDetailsButton(
    expanded: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit
) {
    IconButton(
        onClick = onClick, modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun StudentsDetails() {
    Column (
        modifier = Modifier
        .height(80.dp)
        .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,

    ){
        Text(
            text = "Nota: 100",
            fontSize = 16.sp,
            fontFamily = AbrilFatface
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "Faltas: 3",
            fontSize = 16.sp,
            fontFamily = AbrilFatface
        )
    }
}

@Composable
fun StudentCard(
    @DrawableRes studentPhoto: Int, studentName: String, studentCurse: String, modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow
                )
            ),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(
            bottomStart = 26.dp, topEnd = 26.dp, bottomEnd = 1.dp, topStart = 1.dp

        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = studentPhoto),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
                    .clip(CircleShape)
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(2f)
            ) {
                Text(
                    text = studentName, fontSize = 20.sp, fontFamily = Libertinus
                )
                Text(
                    text = studentCurse, fontSize = 16.sp, fontFamily = AbrilFatface
                )
            }
            StudentDetailsButton(
                modifier = modifier
                    .weight(0.5f)
                    .wrapContentSize(align = Alignment.BottomCenter),
                expanded = expanded,
                onClick = { expanded = !expanded})

        }
        if (expanded) {
            StudentsDetails()

        }
    }

}



