package org.sic4change.chuqabp.course.data

import org.sic4change.domain.Case
import org.sic4change.domain.User
import org.sic4change.chuqabp.course.data.database.Case as DatabaseCase
import org.sic4change.chuqabp.course.data.server.Case as ServerCase
import org.sic4change.chuqabp.course.data.database.User as DatabaseUser
import org.sic4change.chuqabp.course.data.server.User as ServerUser

fun Case.toDatabaseCase() : DatabaseCase =  DatabaseCase(
    id, name, surnames, birthdate, phone, email, photo, location
)

fun DatabaseCase.toDomainCase() : Case = Case(
    id, name, surnames, birthdate, phone, email, photo, location
)

fun ServerCase.toDomainCase() : Case = Case(
    id, name, surnames, birthdate, phone, email, photo, location
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