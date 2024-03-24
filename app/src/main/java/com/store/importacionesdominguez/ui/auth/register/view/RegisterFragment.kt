package com.store.importacionesdominguez.ui.auth.register.view

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.store.importacionesdominguez.R
import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.model.TipoDocumentoIdentidadModel
import com.store.importacionesdominguez.data.model.TipoSexo
import com.store.importacionesdominguez.databinding.FragmentRegisterBinding
import com.store.importacionesdominguez.ui.auth.register.viewmodel.RegisterClientViewModel
import com.store.importacionesdominguez.ui.tipoDocumento.viewmodel.TipoDocumentoViewModel
import com.store.importacionesdominguez.utils.validation.EmpleadoValidator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var navController: NavController? = null

    private val tipoDocumentoViewModel: TipoDocumentoViewModel by viewModels()
    private val registerClienteModel: RegisterClientViewModel by viewModels()

    @Inject
    lateinit var empleadoValidator: EmpleadoValidator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        onInit()
        setupSpinner()
        observerTipoDocumentos()
        validarRegistro()
    }

    private fun setupSpinner() {
        val sexoValues = TipoSexo.values()
        val sexoName = sexoValues.map { tipoSexo -> tipoSexo.name }
        val defaultText = "Seleccionar sexo"
        val sexoNameWithDefault = mutableListOf(defaultText)
        sexoNameWithDefault.addAll(sexoName)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexoName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSexo.adapter = adapter
        // Seleccionar posicion del elelemento por defecto
        val defaultPosition = 0
        binding.spinnerSexo.setSelection(defaultPosition)
    }

    private fun setupSpinnerDocumentos(tipoDocumento: List<TipoDocumentoIdentidadModel>) {
        val tipoDocumentoName = tipoDocumento.map { documento -> documento.nombre }
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tipoDocumentoName)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipoDocumento.adapter = adapter
    }

    private fun observerTipoDocumentos() {
        lifecycleScope.launch {
            // Llamada a la API para obtener los tipos de documentos
            tipoDocumentoViewModel.tiposDocumentos.collect { tipoDocumento ->
                setupSpinnerDocumentos(tipoDocumento)
            }
        }
        tipoDocumentoViewModel.getTipoDocumentos()
    }


    private fun validarRegistro() {

        binding.btnRegister.setOnClickListener {
            val nombres = binding.tiedTxtName.text?.toString() ?: ""
            val apellidos = binding.tiedTxtLastName.text?.toString() ?: ""
            val email = binding.tiedTxtEmail.text?.toString() ?: ""
            val password = binding.tiedTxtPassword.text?.toString() ?: ""
            val phone = binding.tiedTxtPhone.text?.toString() ?: ""
            val address = binding.tiedTxtAddress.text?.toString() ?: ""
            val selectedSexo = binding.spinnerSexo.selectedItem?.toString() ?: ""
            val sexo = selectedSexo.let { TipoSexo.valueOf(it) }
            val numeroDocumento = binding.tiedTxtNumeroDocumento.text?.toString() ?: ""
            val selectedTipoDocumentoName = binding.spinnerTipoDocumento.selectedItem?.toString() ?: ""

            val tipoDocumentoSeleccionado = tipoDocumentoViewModel.tiposDocumentos.value.find { documento ->
                documento.nombre == selectedTipoDocumentoName
            }

            binding.tiedTxtName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (text.isNullOrEmpty()) {
                        binding.txtErrorName.text = getString(R.string.txt_errorname_label)
                        binding.txtErrorName.visibility = View.VISIBLE
                    } else {
                        binding.txtErrorName.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {
                }
            })

            binding.tiedTxtLastName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (text.isNullOrEmpty()) {
                        binding.txtErrorLastName.text = getString(R.string.txt_errorlastname_label)
                        binding.txtErrorLastName.visibility = View.VISIBLE
                    } else {
                        binding.txtErrorLastName.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {
                }
            })

            binding.tiedTxtEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (text.isNullOrEmpty()) {
                        binding.txtErrorEmail.text = getString(R.string.txt_erroremail_label)
                        binding.txtErrorEmail.visibility = View.VISIBLE
                    } else {
                        binding.txtErrorEmail.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {
                }
            })

            binding.tiedTxtPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (text.isNullOrEmpty()) {
                        binding.txtErrorPassword.text = getString(R.string.txt_errorpassword_label)
                        binding.txtErrorPassword.visibility = View.VISIBLE
                    } else {
                        binding.txtErrorPassword.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {
                }
            })

            binding.tiedTxtPhone.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (text.isNullOrEmpty()) {
                        binding.txtErrorTelefono.text = getString(R.string.txt_errortelefono_label)
                        binding.txtErrorTelefono.visibility = View.VISIBLE
                    } else {
                        binding.txtErrorTelefono.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {
                }
            })

            binding.tiedTxtAddress.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (text.isNullOrEmpty()) {
                        binding.txtErrorAddress.text = getString(R.string.txt_erroraddress_label)
                        binding.txtErrorAddress.visibility = View.VISIBLE
                    } else {
                        binding.txtErrorAddress.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {
                }
            })

            binding.tiedTxtNumeroDocumento.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    text: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    if (text.isNullOrEmpty()) {
                        binding.txtErrorNumeroDocumento.text =
                            getString(R.string.txt_errornumero_documento_label)
                        binding.txtErrorNumeroDocumento.visibility = View.VISIBLE
                    } else {
                        binding.txtErrorNumeroDocumento.visibility = View.GONE
                    }
                }

                override fun afterTextChanged(s: android.text.Editable?) {
                }
            })

            // Llamada a la API para registrar el cliente
            val createCliente = ClienteModel(
                id = null,
                nombres,
                apellidos,
                address,
                email,
                password,
                tipoDocumentoSeleccionado,
                numeroDocumento,
                sexo,
                phone
            )

            registerClienteModel.createCliente(createCliente)

        }

        registerClienteModel.registroExitoso.observe(viewLifecycleOwner) { registroSuccess ->
            when(registroSuccess) {
                true -> {
                    Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                    navController?.navigate(R.id.profileFragment)
                }
                false -> {
                    Toast.makeText(requireContext(), "Error al registrar", Toast.LENGTH_SHORT).show()
                }
            }
        }
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