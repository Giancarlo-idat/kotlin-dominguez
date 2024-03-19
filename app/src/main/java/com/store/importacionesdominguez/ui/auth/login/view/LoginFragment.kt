package com.store.importacionesdominguez.ui.auth.login.view

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.store.importacionesdominguez.MainActivity
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.AuthenticationState
import com.store.importacionesdominguez.data.model.SignInModel
import com.store.importacionesdominguez.databinding.FragmentLoginBinding
import com.store.importacionesdominguez.ui.auth.login.viewmodel.LoginViewModel
import com.store.importacionesdominguez.utils.preferences.SharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects

interface OnAuthenticationSuccessListener {
    fun onAuthenticationSuccess()
}

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()
    private var authenticationSuccessListener: OnAuthenticationSuccessListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        initLogin()
        onRegister()
    }

    private fun initLogin() {

        binding.btnLogin.isEnabled = false

        binding.tiedTxtEmail.loseFocusAfterAction(EditorInfo.IME_ACTION_NEXT)

        binding.tiedTxtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValidCredentials: Boolean =
                    isEmailValid(s.toString()) && isPasswordValid(
                        Objects.requireNonNull(
                            binding.tiedTxtPassword.text.toString()
                        )
                    )
                binding.btnLogin.isEnabled = isValidCredentials
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.tiedTxtPassword.loseFocusAfterAction(EditorInfo.IME_ACTION_DONE)

        binding.tiedTxtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isValidCredentials: Boolean = isEmailValid(
                    Objects.requireNonNull(binding.tiedTxtEmail.text.toString())
                ) && isPasswordValid(s.toString())
                binding.btnLogin.isEnabled = isValidCredentials
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.btnLogin.setOnClickListener {
            val email: String = binding.tiedTxtEmail.text.toString()
            val password: String = binding.tiedTxtPassword.text.toString()
            viewModel.iniciarSesion(SignInModel(email, password), requireContext())
        }

        // Observador de autenticación
        viewModel.authenticationState.observe(viewLifecycleOwner) { isAuthenticated ->
            println("Estado de autenticación: $isAuthenticated")
            when (isAuthenticated) {
                is AuthenticationState.Authenticated -> {
                    // Guardar el estado de autenticación
                    SharedPreferences.saveAuthenticationState(requireContext(), true)

                    // Navegar a Home
                    onHome()
                    (requireActivity() as MainActivity).updateMenuVisibility(true)
                }

                is AuthenticationState.Unauthenticated -> {
                    // Mostrar mensaje de error
                    showUnauthenticated()
                    (requireActivity() as MainActivity).updateMenuVisibility(false)

                }

                is AuthenticationState.Loading -> {
                    // Notificaciones de carga
                }

                is AuthenticationState.Error -> {
                    // Mostrar mensaje de error
                    (requireActivity() as MainActivity).updateMenuVisibility(false)
                    showAlert()
                }

                else -> {}
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAuthenticationSuccessListener) {
            authenticationSuccessListener = context
        } else {
            throw ClassCastException("$context must implement OnAuthenticationSuccessListener")
        }
    }

    // Cuando el usuario se autentica correctamente
    private fun onAuthenticationSuccess() {
        authenticationSuccessListener?.onAuthenticationSuccess()
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    private fun onRegister() {
        binding.txtRegister.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.registerFragment)
        }
    }

    private fun EditText.loseFocusAfterAction(action: Int) {
        this.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == action) {
                this.dismissKeyboard()
                v.clearFocus()
            }
            return@setOnEditorActionListener false
        }
    }

    private fun View.dismissKeyboard(completed: () -> Unit = {}) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val wasOpened = inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        if (!wasOpened) completed()
    }

    private fun onHome() {
        val navController = findNavController()
        navController.navigate(R.id.homeFragment)
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error en la autenticación del usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showUnauthenticated() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Error")
        builder.setMessage("Correo o contraseña incorrectos")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}