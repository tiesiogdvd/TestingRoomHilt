package com.tiesiogdvd.testingroomhilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp //Used to activate Hilt, requires to be named in AndroidManifest
class ToDoApplication : Application() {
}