package com.store.importacionesdominguez.utils.validation


import com.store.importacionesdominguez.data.model.ClienteModel
import com.store.importacionesdominguez.data.repository.TipoDocumentoIdentidadRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@Module
@InstallIn(SingletonComponent::class)
class EmpleadoValidator @Inject constructor(
    private val tipoDocumentoIdentidadRepository: TipoDocumentoIdentidadRepository,
) {

    suspend fun validarEmpleado(cliente: ClienteModel) {
        validarCamposObligatorios(cliente)
        validarCamposValidos(cliente)
        validarDocumento(cliente)
        validarSexo(cliente.sexo.name)
    }

    private fun validarCamposObligatorios(cliente: ClienteModel) {
        if (Validations.isBlank(cliente.nombres))
            throw IllegalArgumentException("El nombre no puede ser vacío")
        if (Validations.isBlank(cliente.direccion))
            throw IllegalArgumentException("La dirección no puede ser vacía")
        if (Validations.isBlank(cliente.tipoDocumento?.nombre))
            throw IllegalArgumentException("El tipo de documento no puede ser vacío")
        if (Validations.isBlank(cliente.sexo.name))
            throw IllegalArgumentException("El sexo no puede ser vacío")
        if (Validations.isBlank(cliente.email))
            throw IllegalArgumentException("El email no puede ser vacío")
        if (Validations.isBlank(cliente.tipoDocumento?.id))
            throw Exceptions.TipoDocumentoInvalidoException("El tipo de documento no puede estar vacio")
    }

    private fun validarCamposValidos(cliente: ClienteModel) {
        if (Validations.isValidNames(cliente.nombres))
            throw Exceptions.ValidarNombreApellidosException("El nombre no puede contener números")
        if (Validations.isValidNames(cliente.apellidos))
            throw Exceptions.ValidarNombreApellidosException("El apellido no puede contener números")
        if (Validations.isValidEmail(cliente.email))
            throw Exceptions.EmailInvalidoException("El email debe tener un formato válido")
        if (Validations.isValidTelephone(cliente.telefono))
            throw Exceptions.ValidarTelefonoException("El teléfono debe tener 9 dígitos")
        if (Validations.isValidPassword(cliente.password))
            throw Exceptions.ValidarPasswordException("La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un número")
    }

    private fun validarSexo(sexo: String) {
        if (Validations.isBlank(sexo))
            throw Exceptions.ValidarSexoException("El sexo no puede ser vacío")
    }

    private suspend fun validarDocumento(cliente: ClienteModel) {
        val documento =
            cliente.tipoDocumento?.let {
                tipoDocumentoIdentidadRepository.getTipoDocumentoIdentidad(
                    it.id
                )
            }

        // Si documento no existe lanza una excepcioón
        if (documento != null) {
            if (documento.id.isBlank())
                throw Exceptions.TipoDocumentoInvalidoException("El tipo de documento no puede estar vacio")

            if (documento.id.isBlank())
                throw Exceptions.TipoDocumentoInvalidoException("El tipo de documento no puede estar vacio")

            if (!documento.estado)
                throw Exceptions.TipoDocumentoInvalidoException("El tipo de documento no esta habilitado")

            if (!cliente.tipoDocumento.nombre.trim()
                    .equals(documento.nombre.trim(), ignoreCase = true)
            )
                throw Exceptions.TipoDocumentoInvalidoException("El nombre del tipo de documento no coincide con el tipo de documento seleccionado")

        }
    }

    companion object {
        fun validarId(id: String) {
            if (Validations.isBlank(id))
                throw IllegalArgumentException("El id no puede ser vacío")
        }
    }
}
