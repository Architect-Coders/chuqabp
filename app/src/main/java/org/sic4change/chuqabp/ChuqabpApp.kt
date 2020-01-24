package org.sic4change.chuqabp

import android.app.Application
import org.sic4change.chuqabp.course.di.CasesComponent
import org.sic4change.chuqabp.course.di.DaggerCasesComponent

class ChuqabpApp : Application() {

    lateinit var component: CasesComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerCasesComponent
            .factory()
            .create(this)
    }
}