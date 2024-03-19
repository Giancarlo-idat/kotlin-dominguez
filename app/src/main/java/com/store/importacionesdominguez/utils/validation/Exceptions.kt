package com.store.importacionesdominguez.utils.validation

open class Exceptions(message: String) : RuntimeException(message) {

    open class ValidacionException(message: String) : Exceptions(message)

    class TipoDocumentoInvalidoException(message: String) : ValidacionException(message)

    class EmailInvalidoException(message: String) : ValidacionException(message)

    class ValidarSexoException(message: String) : ValidacionException(message)

    class ValidarRolException(message: String) : ValidacionException(message)

    class ValidarTelefonoException(message: String) : ValidacionException(message)

    class ValidarPasswordException(message: String) : ValidacionException(message)

    class ValidarNombreApellidosException(message: String) : ValidacionException(message)
}