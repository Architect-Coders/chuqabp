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

    @Query("SELECT * FROM `case` ORDER BY id DESC")
    fun getAllCases() : List<Case>

    @Query("SELECT * FROM `case` WHERE id = :id")
    fun findCaseById(id: String): Case

    @Query("SELECT COUNT(id) FROM `case`")
    fun casesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCases(persons: List<Case>)

    @Query("DELETE FROM `case`")
    fun deleteCases()

    @Query("DELETE FROM `case` WHERE id = :id")
    fun deleteCase(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCase(case: Case)

    @Query("UPDATE `case` SET person=:person, date=:date, hour=:hour, place=:place, physic=:physic, sexual=:sexual, psychologic=:psychologic, social=:social, economic=:economic WHERE id = :id")
    fun updateCase(id: String, person: String, date: String, hour: String, place: String, physic: Boolean, sexual: Boolean, psychologic: Boolean, social: Boolean, economic: Boolean)

    @Query("SELECT * FROM `case` WHERE person = :person")
    fun getPersonCases(person: String): List<Case>

    @Query("SELECT * FROM `resource` ORDER BY id DESC")
    fun getAllResources() : List<Resource>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResources(resources: List<Resource>)

    @Query("DELETE FROM `resource`")
    fun deleteResources()

    @Query("SELECT * FROM `resource` WHERE id = :id")
    fun findResourceById(id: String): Resource


}