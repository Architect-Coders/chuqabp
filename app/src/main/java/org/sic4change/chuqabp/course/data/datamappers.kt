package org.sic4change.chuqabp.course.data

import org.sic4change.domain.Case

fun Case.toDatabaseCase() : org.sic4change.chuqabp.course.data.database.Case =
    org.sic4change.chuqabp.course.data.database.Case(
        id, name, surnames, birthdate, phone, email, photo, latitude, longitude
    )

fun org.sic4change.chuqabp.course.data.database.Case.toDomainCase() : Case = Case(
    id, name, surnames, birthdate, phone, email, photo, latitude, longitude
)

fun org.sic4change.chuqabp.course.data.server.Case.toDomainCase() : Case = Case(
    id, name, surnames, birthdate, phone, email, photo, latitude, longitude
)