package org.sic4change.chuqabp.course.data

import org.sic4change.domain.Person
import org.sic4change.domain.User
import org.sic4change.domain.Case
import org.sic4change.domain.Resource
import org.sic4change.chuqabp.course.data.database.Resource as DatabaseResource
import org.sic4change.chuqabp.course.data.database.Case as DatabaseCase
import org.sic4change.chuqabp.course.data.server.Case as ServerCase
import org.sic4change.chuqabp.course.data.database.Person as DatabasePerson
import org.sic4change.chuqabp.course.data.server.Person as ServerPerson
import org.sic4change.chuqabp.course.data.database.User as DatabaseUser
import org.sic4change.chuqabp.course.data.server.User as ServerUser
import org.sic4change.chuqabp.course.data.server.Resource as ServerResource


fun User.toDatabaseUser() : DatabaseUser = DatabaseUser(
    id, email, name, surnames, photo
)

fun DatabaseUser.toDomainUser() : User? = User(
    id, email, name, surnames, photo
)

fun ServerUser.toDomainUser() : User = User(
    id, email, name, surnames, photo
)

fun Person.toDatabasePerson() : DatabasePerson =  DatabasePerson(
    id, name, surnames, birthdate, phone, email, photo, location
)

fun DatabasePerson.toDomainPerson() : Person = Person(
    id, name, surnames, birthday, phone, email, photo, location
)

fun ServerPerson.toDomainPerson() : Person = Person(
    id, name, surnames, birthday, phone, email, photo, location
)

fun Case.toDatabaseCase() : DatabaseCase = DatabaseCase(
        id, person, name, surnames, date, hour, place, physic, sexual, psychologic, social, economic, description, resources
)

fun DatabaseCase.toDomainCase() : Case = Case(
    id, person, name, surnames, date, hour, place, physic, sexual, psychologic, social, economic, description, resources
)

fun ServerCase.toDomainCase() : Case = Case(
    id, person, name, surnames, date, hour, place, physic, sexual, psychologic, social, economic, description, resources
)

fun Resource.toDatabaseResource() : DatabaseResource =  DatabaseResource(
    id, name, place, physic, sexual, psychologic, social, economic, description
)

fun DatabaseResource.toDomainResource() : Resource = Resource(
    id, name, place, physic, sexual, psychologic, social, economic, description, false
)

fun ServerResource.toDomainResource() : Resource = Resource(
    id, name, place, physic, sexual, psychologic, social, economic, description, false
)