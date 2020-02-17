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

    @Query("UPDATE `person` SET name=:name, surnames=:surnames, birthday=:birthdate, phone=:phone, email=:email, photo=:photo, location=:location WHERE id = :id")
    fun updatePerson(id: String, name: String, surnames: String, birthdate: String, phone: String, email: String, photo: String, location: String)

    @Query("SELECT * FROM `person` ORDER BY name ASC")
    fun getAllPersons() : List<Person>

    @Query("SELECT * FROM `person` WHERE id = :id")
    fun findPersonById(id: String): Person

    @Query("SELECT COUNT(id) FROM `person`")
    fun personsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersons(persons: List<Person>)

    @Query("DELETE FROM `person`")
    fun deletePersons()

    @Query("DELETE FROM `person` WHERE id = :id")
    fun deletePerson(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)


}