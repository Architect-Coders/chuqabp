package org.sic4change.chuqabp.course.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import org.sic4change.chuqabp.course.data.AndroidPermissionChecker
import org.sic4change.chuqabp.course.data.PlayServicesLocationDataSource
import org.sic4change.chuqabp.course.data.database.ChuqabpDatabase
import org.sic4change.chuqabp.course.data.database.RoomDataSource
import org.sic4change.chuqabp.course.data.server.FirebaseDataSource
import org.sic4change.data.repository.PermissionChecker
import org.sic4change.data.source.LocalDataSource
import org.sic4change.data.source.LocationDataSource
import org.sic4change.data.source.RemoteDataSource
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        ChuqabpDatabase::class.java,
        "chuqabp-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: ChuqabpDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSource() : RemoteDataSource = FirebaseDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource = PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker = AndroidPermissionChecker(app)

}