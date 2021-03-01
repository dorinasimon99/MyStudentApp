package hu.bme.aut.mystudentapp.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.navigation.NavigationView
import hu.bme.aut.mystudentapp.backend.Backend
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_student_welcome.*
import kotlinx.android.synthetic.main.fragment_student_welcome.view.*

class StudentWelcomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_welcome, container, false)
        /*view.btnClasses.setOnClickListener {
            Backend.loadCourse()
            //TODO: ClassesFragment
        }*/
        /*view.nav_view.menu.findItem(R.id.nav_sign_out).setOnMenuItemClickListener {
            Backend.signOut()
            Backend.updateUserData(false)
            this.findNavController()
                .navigate(R.id.signInFragment)
            true
        }*/
        return view
    }
}