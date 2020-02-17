package org.sic4change.chuqabp.course.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class, Person::class], version = 1)
abstract class ChuqabpDatabase: RoomDatabase() {
    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            ChuqabpDatabase::class.java,
            "chuqabp-db"
        ).build()
    }

    abstract fun chuqabpDao(): ChuqabpDao

}
