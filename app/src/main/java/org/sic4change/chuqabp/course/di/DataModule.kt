package org.sic4change.chuqabp.course.di

import dagger.Module
import dagger.Provides
import org.sic4change.data.repository.CasesRepository
import org.sic4change.data.repository.PermissionChecker
import org.sic4change.data.repository.RegionRepository
import org.sic4change.data.repository.UserRepository
import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.LocationDataSource
import org.sic4change.data.source.RemoteDataSource

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun casesRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ) = CasesRepository(localDataSource, remoteDataSource)

    @Provides
    fun userRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ) = UserRepository(localDataSource, remoteDataSource)
}