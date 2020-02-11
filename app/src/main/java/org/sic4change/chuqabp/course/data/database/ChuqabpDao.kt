package org.sic4change.chuqabp.course.data.database

import androidx.room.*

@Dao
interface ChuqabpDao {

    @Query("SELECT * FROM user ORDER BY id ASC LIMIT 1")
    fun getUser(): User

    @Query("SELECT * FROM user WHERE id=:id")
    fun getUser(id: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Query("DELETE FROM user")
    fun deleteUser()

    @Query("UPDATE `case` SET name=:name, surnames=:surnames, birthdate=:birthdate, phone=:phone, email=:email, photo=:photo, location=:location WHERE id = :id")
    fun updateCase(id: String, name: String, surnames: String, birthdate: String, phone: String, email: String, photo: String, location: String)

    @Query("SELECT * FROM `case` ORDER BY name ASC")
    fun getAllCases() : List<Case>

    @Query("SELECT * FROM `case` WHERE id = :id")
    fun findCaseById(id: String): Case

    @Query("SELECT COUNT(id) FROM `case`")
    fun caseCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCases(cases: List<Case>)

    @Query("DELETE FROM `case`")
    fun deleteCases()

    @Query("DELETE FROM `case` WHERE id = :id")
    fun deleteCase(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCase(case: Case)


}