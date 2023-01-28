package com.tiesiogdvd.testingroomhilt.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

//With DAO it is possible to make operations on the table from Task entity
@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table")
    //Use kotlinX.coroutines.flow
    //Similar to LiveData, but is not mutable, requires initial values, it works Async
    //No need for suspend because it happens inside the Flow methods
    fun getTasks():Flow<List<Task>>



    //suspend is a coroutines feature to let something run on a different thread
    //room does not let any functions to be executed on the main thread by default
    @Insert(onConflict = OnConflictStrategy.REPLACE) //If the ID is the same, item will be replaced
    suspend fun insert(task:Task)

    @Update
    suspend fun update(task:Task)

    @Delete
    suspend fun delete(task:Task)
}