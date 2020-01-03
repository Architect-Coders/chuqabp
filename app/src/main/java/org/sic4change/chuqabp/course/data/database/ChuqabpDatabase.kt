package org.sic4change.chuqabp.course.data.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [User::class, Case::class], version = 1)
abstract class ChuqabpDatabase: RoomDatabase() {
    abstract fun chuqabpDao(): ChuqabpDao
}
