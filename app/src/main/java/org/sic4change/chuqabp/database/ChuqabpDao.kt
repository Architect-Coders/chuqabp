package org.sic4change.chuqabp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChuqabpDao {

    @Query("select * from databaseuser order by id asc limit 1")
    fun getUser(): LiveData<DatabaseUser>

    @Query("select * from databaseuser where id=:id")
    fun getUser(id: String): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DatabaseUser)

    @Query("delete from databaseuser")
    fun deleteUser()


}