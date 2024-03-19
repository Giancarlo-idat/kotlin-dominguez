package com.store.importacionesdominguez.di

import com.store.importacionesdominguez.data.repository.ClienteRepository
import com.store.importacionesdominguez.data.repository.LoginRepository
import com.store.importacionesdominguez.data.repository.RolRepository
import com.store.importacionesdominguez.data.repository.TipoDocumentoIdentidadRepository
import com.store.importacionesdominguez.data.service.AuthService
import com.store.importacionesdominguez.data.service.ClienteService
import com.store.importacionesdominguez.data.service.RolService
import com.store.importacionesdominguez.data.service.TipoDocumentoIdentidadService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(authService: AuthService): LoginRepository {
        return LoginRepository(authService)
    }

    @Provides
    @Singleton
    fun provideClienteRepository(clienteService: ClienteService): ClienteRepository {
        return ClienteRepository(clienteService)
    }

    @Provides
    @Singleton
    fun provideTipoDocumentoIdentidadRepository(tipoDocumentoIdentidadService: TipoDocumentoIdentidadService): TipoDocumentoIdentidadRepository {
        return TipoDocumentoIdentidadRepository(tipoDocumentoIdentidadService)
    }

    @Provides
    @Singleton
    fun provideRolRepository(rolService: RolService): RolRepository {
        return RolRepository(rolService)
    }
}