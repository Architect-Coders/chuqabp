package org.sic4change.chuqabp.course.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChuqabpDao {

    @Query("select * from databaseuser order by id asc limit 1")
    fun getUser(): DatabaseUser

    @Query("select * from databaseuser where id=:id")
    fun getUser(id: String): LiveData<List<DatabaseUser>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: DatabaseUser)

    @Query("delete from databaseuser")
    fun deleteUser()

    @Query("SELECT * FROM `case`")
    fun getAllCases() : List<Case>

    @Query("SELECT * FROM `case` WHERE id = :id")
    fun findCaseById(id: String): Case

    @Query("SELECT COUNT(id) FROM `case`")
    fun caseCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCases(cases: List<Case>)

    @Query("delete from `case`")
    fun deleteCases()


}