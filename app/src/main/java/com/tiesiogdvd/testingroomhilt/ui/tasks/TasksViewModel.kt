package com.tiesiogdvd.testingroomhilt.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tiesiogdvd.testingroomhilt.data.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(  //@ViewModelScoped constructor basically enabled injection
    private val taskDao: TaskDao
) : ViewModel() {
    val tasks = taskDao.getTasks().asLiveData() //required livedata dependency
    // best to use asLiveData in the viewmodel as unlike flow, LiveData can observe lifecycle changes and stop, preventing memory leaks and crashes
}