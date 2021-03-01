package hu.bme.aut.mystudentapp.ui.teacher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.mystudentapp.backend.Backend
import hu.bme.aut.mystudentapp.ui.MainActivity
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_teacher_welcome.*

class TeacherWelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpNavigationView()
        val view = inflater.inflate(R.layout.fragment_teacher_welcome, container, false)
        //TODO: btnClickListeners
        return view
    }

    private fun setUpNavigationView() {
        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { menuItem ->
            if (menuItem.itemId == R.id.nav_sign_out){
                Backend.signOut()
                //TODO: back to login
                return@OnNavigationItemSelectedListener true
            }
            else return@OnNavigationItemSelectedListener false
        })
    }
}