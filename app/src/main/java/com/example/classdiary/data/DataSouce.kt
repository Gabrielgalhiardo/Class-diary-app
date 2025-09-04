package com.example.classdiary.data

import com.example.classdiary.R

class DataSouce {
    fun loadAStudents(): List<Student>{
        return  listOf<Student>(
            Student("Gabriel", R.drawable.ic_launcher_background, "Desenvolvimento de sistemas"),
            Student("Gabriel", R.drawable.ic_launcher_background, "Desenvolvimento de sistemas"),
            Student("Gabriel", R.drawable.ic_launcher_background, "Desenvolvimento de sistemas"),
            Student("Gabriel", R.drawable.ic_launcher_background, "Desenvolvimento de sistemas"),
            Student("Gabriel", R.drawable.ic_launcher_background, "Desenvolvimento de sistemas"),
            Student("Gabriel", R.drawable.ic_launcher_background, "Desenvolvimento de sistemas")
        )
    }
}