package com.example.photouploadapp.component

import android.app.Application
import com.example.photouploadapp.module.RoomDatabaseModule

class MyApplication: Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this

        appComponent = DaggerApplicationComponent.builder()
            .roomDatabaseModule(RoomDatabaseModule(this))
            .build()
    }
}
