package org.sic4change.chuqabp

import android.app.Application
import org.sic4change.chuqabp.course.initDI

class ChuqabpApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}