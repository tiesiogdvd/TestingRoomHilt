package com.tiesiogdvd.testingroomhilt.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tiesiogdvd.testingroomhilt.di.SingletonComponentScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider


//Class is abstract because Room will do the code will generate implementations
@Database(entities = [Task::class], version = 1)  //Inside [] probably possible to have multiple tables
abstract class TaskDatabase : RoomDatabase() {



    //again abstract because we do not want to declare the body, just the function itself
    //will be used to get a handle of TaskDao to do database operations
    //To get it into various parts dependency injection will be used
    abstract fun taskDao():TaskDao



    //@Inject tells dagger how it can create an instance of this class
    //and also tells dagger to pass necessary dependencies if something is defined in the constructor
    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @SingletonComponentScope private val applicationScope: CoroutineScope //Provided by DI in AppModule to launch db operations on other threads
        //Annotation added to make sure that the right scope is picked if we add more scopes to the DI module
    ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            //It will get executed only the first time when the database is created
            super.onCreate(db)
             val dao = database.get().taskDao() //This way dagger will not instantiate database when callback is created,
            //Instead it will create the database when its on create method is executed which happens after
            //.build method is finished in provideDatabase method
            //This is way we can get database into the callback

            //DB operations are suspended functions so we need coroutine to execute them and can't just use dao.insert()
            applicationScope.launch {
                println("LAUNCHED")
                dao.insert(Task(name = "Wash the dishes1"))
                println("LAUNCHED")
                dao.insert(Task(name = "Wash the dishes2"))
                println("LAUNCHED")
                dao.insert(Task(name = "Wash the dishes3", important = true))
                println("LAUNCHED")
                dao.insert(Task(name = "Wash the dishes4", completed = true))
                println("LAUNCHED")
                dao.insert(Task(name = "Wash the dishes5"))
                dao.insert(Task(name = "Wash the dishes6", completed = true))
                dao.insert(Task(name = "Wash the dishes7"))
                dao.insert(Task(name = "Wash the dishes8"))

            }

        }
    }



}