package com.store.importacionesdominguez.ui.client.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.store.importacionesdominguez.MainActivity
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.databinding.FragmentMenuClientBinding
import com.store.importacionesdominguez.utils.preferences.SharedPreferences

class MenuClientFragment : Fragment() {

    private var _binding: FragmentMenuClientBinding? = null
    private val binding get() = _binding!!
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenuClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        displayUser()
        init()

    }

    private fun displayUser() {
        val user = SharedPreferences.getUserEmail(requireContext())
        println("Usuario $user")
        binding.tvNombreCliente.text = "Hola, \n$user\nBienvenido"
    }

    private fun init() {
        onLogout()
    }

    private fun onLogout() {
        binding.llLogout.setOnClickListener {
            SharedPreferences.clearAuthenticationData(requireContext())
            (requireActivity() as MainActivity).updateMenuVisibility(false)
            onHome()
        }
    }

    private fun onHome() {
        navController.navigate(R.id.homeFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}