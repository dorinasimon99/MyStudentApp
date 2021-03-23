package hu.bme.aut.mystudentapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.mystudentapp.backend.NetworkBackend
import hu.bme.aut.mystudentapp.backend.UserDataBackend
import hu.bme.aut.mystudentapp.data.model.Role
import hu.mystudentapp.R

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer : DrawerLayout
    private lateinit var toggle : ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.bringToFront()

    }

    override fun onStart() {
        super.onStart()

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.navigate(R.id.loadingFragment)


        UserDataBackend.isSignedIn.observe(this, Observer<Boolean> { isSignedUp ->
            Log.i(TAG, "isSignedIn changed : $isSignedUp")

            if (isSignedUp) {
                val data = UserDataBackend.getUserData()
                if (data == null) {
                    navController.navigate(R.id.selectRoleFragment)
                }
                UserDataBackend.hasUserData.observe(this, { userData ->
                    when(userData.role){
                        Role.STUDENT.toString() ->{
                            navController.navigate(R.id.studentMainFragment)
                        }
                        Role.TEACHER.toString() -> {
                            navController.navigate(R.id.teacherMainFragment)
                        }
                        else -> throw Exception("Something wrong with role!")
                    }
                })
            } else if(!isSignedUp){
                navController.navigate(R.id.signInFragment)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        NetworkBackend.handleWebUISignInResponse(requestCode, resultCode, data)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_sign_out ->
            {
                Log.i(TAG, "Sign out clicked")

                NetworkBackend.signOut()
                Toast.makeText(this, "Signed out!", Toast.LENGTH_SHORT).show()
            }
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}