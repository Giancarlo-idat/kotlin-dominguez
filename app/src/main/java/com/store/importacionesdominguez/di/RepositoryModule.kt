package com.store.importacionesdominguez.di

import com.store.importacionesdominguez.data.repository.CategoriesRepository
import com.store.importacionesdominguez.data.repository.CheckoutRepository
import com.store.importacionesdominguez.data.repository.ClienteRepository
import com.store.importacionesdominguez.data.repository.DocVentaRepository
import com.store.importacionesdominguez.data.repository.LoginRepository
import com.store.importacionesdominguez.data.repository.PaymentRepository
import com.store.importacionesdominguez.data.repository.ProductsRepository
import com.store.importacionesdominguez.data.repository.ProfileRepository
import com.store.importacionesdominguez.data.repository.ReportesRepository
import com.store.importacionesdominguez.data.repository.RolRepository
import com.store.importacionesdominguez.data.repository.TipoDocumentoIdentidadRepository
import com.store.importacionesdominguez.data.repository.TipoTransaccionRepository
import com.store.importacionesdominguez.data.service.AuthService
import com.store.importacionesdominguez.data.service.CategoriesService
import com.store.importacionesdominguez.data.service.CheckoutService
import com.store.importacionesdominguez.data.service.ClienteService
import com.store.importacionesdominguez.data.service.DocVentaService
import com.store.importacionesdominguez.data.service.PaymentService
import com.store.importacionesdominguez.data.service.ProductsService
import com.store.importacionesdominguez.data.service.ProfileService
import com.store.importacionesdominguez.data.service.ReportesService
import com.store.importacionesdominguez.data.service.RolService
import com.store.importacionesdominguez.data.service.TipoDocumentoIdentidadService
import com.store.importacionesdominguez.data.service.TipoTransaccionService
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

    @Provides
    @Singleton
    fun provideProductoRepository(productoService: ProductsService): ProductsRepository {
        return ProductsRepository(productoService)
    }


    @Provides
    @Singleton
    fun provideCategoriesRepository(categoriesService: CategoriesService): CategoriesRepository {
        return CategoriesRepository(categoriesService)
    }

    @Provides
    @Singleton
    fun provideDocVentaRepository(docVentaService: DocVentaService): DocVentaRepository {
        return DocVentaRepository(docVentaService)
    }

    @Provides
    @Singleton
    fun provideTipoTransaccionRepository(tipoTransaccionService: TipoTransaccionService): TipoTransaccionRepository {
        return TipoTransaccionRepository(tipoTransaccionService)
    }

    @Provides
    @Singleton
    fun provideCheckoutRepository(checkoutService: CheckoutService): CheckoutRepository {
        return CheckoutRepository(checkoutService)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(profileService: ProfileService): ProfileRepository {
        return ProfileRepository(profileService)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(paymentService: PaymentService): PaymentRepository {
        return PaymentRepository(paymentService)
    }

    @Provides
    @Singleton
    fun provideReportesRepository(reportesServices: ReportesService): ReportesRepository {
        return ReportesRepository(reportesServices)
    }

}


