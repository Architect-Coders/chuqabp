package org.sic4change.chuqabp.course.data.database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.sic4change.chuqabp.course.data.*
import org.sic4change.data.source.LocalDataSource
import java.text.SimpleDateFormat
import org.sic4change.domain.User as DomainUser
import org.sic4change.domain.Person as DomainPerson
import org.sic4change.domain.Case as DomainCase
import org.sic4change.domain.Resource as DomainResource
import org.sic4change.domain.ClosedReason as DomainClosedReason

class RoomDataSource(db : ChuqabpDatabase) : LocalDataSource {

    private val chuqabpDao = db.chuqabpDao()

    override suspend fun getUser(): DomainUser? = withContext(Dispatchers.IO) {
        val user = chuqabpDao.getUser()
        if (user != null) {
            user.toDomainUser()
        } else {
            null
        }
    }

    override suspend fun getUser(id: String): DomainUser? = withContext(Dispatchers.IO) {
        chuqabpDao.getUser(id).toDomainUser()
    }

    override suspend fun insertUser(user: DomainUser) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertUser(user.toDatabaseUser())
        }
    }

    override suspend fun deleteUser() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteUser()
        }
    }

    override suspend fun createPerson(person: org.sic4change.domain.Person) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertPerson(person.toDatabasePerson())
        }
    }

    override suspend fun updatePerson(case: org.sic4change.domain.Person) {
        withContext(Dispatchers.IO) {
            chuqabpDao.updatePerson(case.id, case.name, case.surnames, case.birthdate, case.phone, case.email, case.photo, case.location)
        }
    }

    override suspend fun deletePerson(id: String) {
        withContext(Dispatchers.IO) {
            chuqabpDao.deletePerson(id)
        }
    }

    override suspend fun deletePersons() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deletePersons()
        }
    }

    override suspend fun insertPersons(persons: List<DomainPerson>) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertPersons(persons.map { it.toDatabasePerson() })
        }
    }

    override suspend fun getPersons(): List<DomainPerson> = withContext(Dispatchers.IO) {
        chuqabpDao.getAllPersons().map { it.toDomainPerson() }
    }

    override suspend fun findPersonById(id: String): DomainPerson = withContext(Dispatchers.IO) {
        chuqabpDao.findPersonById(id).toDomainPerson()
    }

    override suspend fun filterPersons(nameSurname: String, location: String) : List<org.sic4change.domain.Person> {
        var persons = mutableListOf<org.sic4change.domain.Person>()
        withContext(Dispatchers.IO) {
            if (!nameSurname.isNullOrEmpty()) {
                persons.addAll(chuqabpDao.filterPersonsByNameAndSurnames(nameSurname).map { it.toDomainPerson() })
            }
            if (!location.isNullOrEmpty()) {
                persons.addAll(chuqabpDao.filterPersonsByLocation(location).map { it.toDomainPerson() })
            }
        }
        return persons.distinct()
    }

    override suspend fun createCase(case: org.sic4change.domain.Case) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertCase(case.toDatabaseCase())
        }
    }

    override suspend fun updateCase(case: org.sic4change.domain.Case) {
        withContext(Dispatchers.IO) {
            chuqabpDao.updateCase(case.id, case.person, case.date, case.hour, case.place, case.physic, case.sexual, case.psychologic, case.social, case.economic,
            case.description, case.resources, case.status, case.closeDescription, case.closeReason, case.closeDate)
        }
    }

    override suspend fun deleteCase(id: String) {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteCase(id)
        }
    }

    override suspend fun getPersonCases(person: String): List<DomainCase> = withContext(Dispatchers.IO) {
        chuqabpDao.getPersonCases(person).map { it.toDomainCase() }
    }

    override suspend fun deleteCases() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteCases()
        }
    }

    override suspend fun insertCases(cases: List<org.sic4change.domain.Case>) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertCases(cases.map { it.toDatabaseCase() })
        }
    }

    override suspend fun getCases(): List<DomainCase> = withContext(Dispatchers.IO) {
        chuqabpDao.getAllCases().map { it.toDomainCase() }
    }

    override suspend fun findCaseById(id: String): DomainCase = withContext(Dispatchers.IO) {
        chuqabpDao.findCaseById(id).toDomainCase()
    }

    override suspend fun filterCases(nameSurname: String, place: String, physic: Boolean?,
                                     sexual: Boolean?, psychologic: Boolean?, social: Boolean?,
                                     economic: Boolean?, status: String?, rangeDate: String) : List<org.sic4change.domain.Case> {
        var cases = mutableListOf<org.sic4change.domain.Case>()
        withContext(Dispatchers.IO) {
            if (!nameSurname.isNullOrEmpty()) {
                cases.addAll(chuqabpDao.filterCasesByNameAndSurnames(nameSurname).map { it.toDomainCase() })
            }
            if (!place.isNullOrEmpty()) {
                cases.addAll(chuqabpDao.filterCasesByPlace(place).map { it.toDomainCase() })
            }
            if (!rangeDate.isNullOrEmpty()) {
                val dates = rangeDate.split(" - ")
                val dateformatddMMyyyy = SimpleDateFormat("dd/MM/yyyy")
                val startDate = dateformatddMMyyyy.parse(dates[0]).time
                val endDate = dateformatddMMyyyy.parse(dates[1]).time
                cases.addAll(chuqabpDao.filterCasesByDateRange(startDate, endDate).map { it.toDomainCase() })
            }
            if (physic != null) {
                if (physic) {
                    cases.addAll(chuqabpDao.filterCasesByPhysic(physic).map { it.toDomainCase() })
                }
            }
            if (sexual != null) {
                if (sexual) {
                    cases.addAll(chuqabpDao.filterCasesBySexual(sexual).map { it.toDomainCase() })
                }
            }
            if (psychologic != null) {
                if (psychologic) {
                    cases.addAll(chuqabpDao.filterCasesByPsychologic(psychologic).map { it.toDomainCase() })
                }
            }
            if (social != null) {
                if(social) {
                    cases.addAll(chuqabpDao.filterCasesBySocial(social).map { it.toDomainCase() })
                }
            }
            if (economic != null) {
                if (economic) {
                    cases.addAll(chuqabpDao.filterCasesByEconomic(economic).map { it.toDomainCase() })
                }
            }
            if (!status.isNullOrEmpty()) {
                cases.addAll(chuqabpDao.filterCasesByStatus(status).map { it.toDomainCase() })
            }
        }
        return cases.distinct()
    }

    override suspend fun getResources(): List<DomainResource> = withContext(Dispatchers.IO) {
        chuqabpDao.getAllResources().map { it.toDomainResource() }
    }

    override suspend fun insertResources(resources: List<DomainResource>) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertResources(resources.map { it.toDatabaseResource() })
        }
    }

    override suspend fun deleteResources() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteResources()
        }
    }

    override suspend fun findResourceById(id: String): DomainResource = withContext(Dispatchers.IO) {
        chuqabpDao.findResourceById(id).toDomainResource()
    }

    override suspend fun getClosedReasons(): List<DomainClosedReason> = withContext(Dispatchers.IO) {
        chuqabpDao.getAllClosedReasons().map { it.toDomainClosedReason() }
    }

    override suspend fun insertClosedReasons(resources: List<DomainClosedReason>) {
        withContext(Dispatchers.IO) {
            chuqabpDao.insertClosedReasons(resources.map { it.toDatabaseClosedReason() })
        }
    }

    override suspend fun deleteClosedReasons() {
        withContext(Dispatchers.IO) {
            chuqabpDao.deleteClosedReasons()
        }
    }

    override suspend fun findClosedReasonById(id: String): DomainClosedReason = withContext(Dispatchers.IO) {
        chuqabpDao.findClosedReasonById(id).toDomainClosedReason()
    }

}





