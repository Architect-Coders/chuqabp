package org.sic4change.chuqabp.course.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_user.*
import org.sic4change.chuqabp.R


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = this.findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.label == getString(R.string.new_case) ||
                destination.label == getString(R.string.cases)  ||
                destination.label == getString(R.string.persons) ||
                destination.label == getString(R.string.training) ||
                destination.label == getString(R.string.configuration)) {
                title = getString(R.string.app_name) + ": " + destination.label
            } else {
                title = getString(R.string.app_name)
            }
        }

    }

    override fun onBackPressed() {
        if (title.contains(":")) {
            finishAffinity()
        }
    }

}