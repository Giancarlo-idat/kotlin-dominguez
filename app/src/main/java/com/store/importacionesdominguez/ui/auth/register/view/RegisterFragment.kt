package com.store.importacionesdominguez.ui.auth.register.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun registerClient() {
        val nombres = binding.tiedTxtName.text.toString()
        val apellidos = binding.tiedTxtLastName.text.toString()
        val email = binding.tiedTxtEmail.text.toString()
        val password = binding.tiedTxtPassword.text.toString()
        val telefono = binding.tiedTxtPhone.text.toString()
        val direccion = binding.tiedTxtAddress.text.toString()

        // Validación para el formulario
        /*if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            binding.tilName.error = "Campo requerido"
            binding.tilLastName.error = "Campo requerido"
            binding.tilEmail.error = "Campo requerido"
            binding.tilPassword.error = "Campo requerido"
            binding.tilPhone.error = "Campo requerido"
            binding.tilAddress.error = "Campo requerido"

        } else {
            // Llamada a la API para registrar el cliente
            // registerClient(nombres, apellidos, email, password, phone, address)
            // val cliente = ClienteModel(nombres, apellidos, email, password, telefono, direccion)
        }*/



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        onInit()
    }

    private fun onInit() {
        onBackPressed()
    }

    private fun onBackPressed() {
        val backAction = binding.imgBack
        backAction.setOnClickListener {
            navController?.navigate(R.id.profileFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}