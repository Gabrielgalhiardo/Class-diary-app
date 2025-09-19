package com.example.classdiary.data

import com.example.classdiary.R

class DataSouce {
    fun loadAStudents(): List<Student>{
        return  listOf<Student>(
            Student("Gabriel", R.drawable.gabriel,"Desenvolvimento de sistemas"),
            Student("Igor", R.drawable.igor, "Desenvolvimento de sistemas"),
            Student("Gustavo", R.drawable.gustavo, "Desenvolvimento de sistemas"),
            Student("Eric", R.drawable.eric, "Desenvolvimento de sistemas")
        )
    }
}