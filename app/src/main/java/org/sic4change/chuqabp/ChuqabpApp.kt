package org.sic4change.chuqabp

import android.app.Application
import androidx.room.Room
import org.sic4change.chuqabp.course.model.database.ChuqabpDatabase

class ChuqabpApp : Application() {

    lateinit var  db: ChuqabpDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            ChuqabpDatabase::class.java,
            "chuqabp-db"
        ).build()
    }
}