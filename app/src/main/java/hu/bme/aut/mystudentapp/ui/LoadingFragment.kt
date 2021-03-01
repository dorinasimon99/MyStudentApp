package hu.bme.aut.mystudentapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.mystudentapp.backend.Data
import hu.bme.aut.mystudentapp.backend.Role
import hu.mystudentapp.R
import java.lang.Exception

class LoadingFragment : Fragment() {

    companion object{
        const val TAG = "LoadingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_loading, container, false)


        return view
    }

}