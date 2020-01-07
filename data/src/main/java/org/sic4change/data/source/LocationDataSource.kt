package org.sic4change.data.source

interface LocationDataSource {
    suspend fun findLastRegion(): String?
}