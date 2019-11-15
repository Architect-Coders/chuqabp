package org.sic4change.chuqabp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabaseUser::class], version = 1)
abstract class ChuqabpDatabase: RoomDatabase() {
    abstract val chuqabpDatabaseDao: ChuqabpDao
}

private lateinit var INSTANCE: ChuqabpDatabase

fun getDatabase(context: Context): ChuqabpDatabase {
    synchronized(ChuqabpDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                ChuqabpDatabase::class.java,
                "chuqabp"
            ).build()
        }
    }
    return INSTANCE
}