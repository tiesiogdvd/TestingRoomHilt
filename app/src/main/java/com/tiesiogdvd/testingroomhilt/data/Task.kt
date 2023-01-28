package com.tiesiogdvd.testingroomhilt.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

//Basically a class for a task object, made with Room database implementation in mind
//Parcelable to make it possible to pass the entities with data streams

@Entity(tableName = "task_table")
@Parcelize
data class Task (val name:String,
                 val important:Boolean = false,
                 val completed:Boolean = false,
                 val created:Long = System.currentTimeMillis(),
                 @PrimaryKey(autoGenerate = true) val id:Int = 0 //value will not stay zero
) : Parcelable{

    val createdDateFormatted: String
        get() = DateFormat.getDateTimeInstance().format(created)

}