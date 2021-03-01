package hu.bme.aut.mystudentapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.bme.aut.mystudentapp.backend.Backend
import hu.mystudentapp.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class SignInFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        view.btnLogin.setOnClickListener {
            Backend.signIn(requireActivity())
        }
        return view
    }
}