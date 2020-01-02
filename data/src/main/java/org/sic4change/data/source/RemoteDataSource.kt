package org.sic4change.data.source

import org.sic4change.domain.Case

interface RemoteDataSource {
    suspend fun getCases() : List<Case>

}