package org.sic4change.chuqabp.course.data

import org.sic4change.domain.Person
import org.sic4change.domain.User
import org.sic4change.chuqabp.course.data.database.Person as DatabasePerson
import org.sic4change.chuqabp.course.data.server.Person as ServerPerson
import org.sic4change.chuqabp.course.data.database.User as DatabaseUser
import org.sic4change.chuqabp.course.data.server.User as ServerUser

fun Person.toDatabasePerson() : DatabasePerson =  DatabasePerson(
    id, name, surnames, birthdate, phone, email, photo, location
)

fun DatabasePerson.toDomainPerson() : Person = Person(
    id, name, surnames, birthday, phone, email, photo, location
)

fun ServerPerson.toDomainPerson() : Person = Person(
    id, name, surnames, birthday, phone, email, photo, location
)

fun User.toDatabaseUser() : DatabaseUser = DatabaseUser(
    id, email, name, surnames, photo
)

fun DatabaseUser.toDomainUser() : User? = User(
    id, email, name, surnames, photo
)

fun ServerUser.toDomainUser() : User = User(
    id, email, name, surnames, photo
)