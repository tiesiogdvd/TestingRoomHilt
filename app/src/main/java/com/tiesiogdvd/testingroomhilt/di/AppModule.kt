package com.tiesiogdvd.testingroomhilt.di

import android.app.Application
import androidx.room.Room

import com.tiesiogdvd.testingroomhilt.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


//Module is the place where we give dagger instructions on how to create needed dependencies
@Module
@InstallIn(SingletonComponent::class) //SingletonComponent is a class automatically created by dagger, it is the object that contains dependencies
object AppModule { //Does not matter what it is called
    //There is only need for a single database object to be created, for that reason we declare it as singleton
    //Room requires context, and Hilt knows already how to get context
    @Provides
    @Singleton
    fun provideDatabase(
        app:Application,
        callback: TaskDatabase.Callback //Dagger knows how to get the callback because of the @Inject method in TaskDatabase.kt
    ) = Room.databaseBuilder(app, TaskDatabase::class.java, "task_database") //It uses java under the hood so we give java class (context, database, name)
        .fallbackToDestructiveMigration() //If db updates schema, it just replaces it with a new one
        .addCallback(callback)
        .build() //Creates instance of TaskDatabase class
        //provideDatabse()=... basically returns what is after the = symbol


    //Will create TaskDao object which is needed to make database operations
    //No need to declare this as singleton as database is already a singleton so there will automatically be only a single Dao
    //Database has a taskDao method, in here to get the TaskDatabase it checks if anything is declared before to get a database
    //And there is a method above provideDatabase which will automatically be used
    @Provides
    fun provideTaskDao(db:TaskDatabase) = db.taskDao()

    //We create a coroutine scope to be used in TaskDatabase to use coroutine operations
    //It lives as long as application does because it is declared singleton
    //Same scope will later be also used for long running operations for the whole app
    @SingletonComponentScope //takes the annotation from below, used if there is more scopes used for dagger to recognize which one should be used
    @Provides
    @Singleton
    fun provideSingletonComponentScope() = CoroutineScope(SupervisorJob())
    //SupervisorJob tells coroutine that if any of its childs' fails to keep the others running
    //Otherwise it would stop tasks
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class  SingletonComponentScope
