package org.sic4change.chuqabp.course.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChuqabpDao {

    @Query("select * from user order by id asc limit 1")
    fun getUser(): User

    @Query("select * from user where id=:id")
    fun getUser(id: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("delete from user")
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

    @Query("delete from `case` WHERE id = :id")
    fun deleteCase(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCase(case: Case)


}