package com.store.importacionesdominguez.di

import com.store.importacionesdominguez.data.service.AuthService
import com.store.importacionesdominguez.data.service.CategoriesService
import com.store.importacionesdominguez.data.service.CheckoutService
import com.store.importacionesdominguez.data.service.ClienteService
import com.store.importacionesdominguez.data.service.DocVentaService
import com.store.importacionesdominguez.data.service.PaymentService
import com.store.importacionesdominguez.data.service.ProductsService
import com.store.importacionesdominguez.data.service.ProfileService
import com.store.importacionesdominguez.data.service.RolService
import com.store.importacionesdominguez.data.service.TipoDocumentoIdentidadService
import com.store.importacionesdominguez.data.service.TipoTransaccionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideClienteService(retrofit: Retrofit): ClienteService {
        return retrofit.create(ClienteService::class.java)
    }

    @Provides
    @Singleton
    fun provideTipoDocumentoIdentidadService(retrofit: Retrofit): TipoDocumentoIdentidadService {
        return retrofit.create(TipoDocumentoIdentidadService::class.java)
    }

    @Provides
    @Singleton
    fun provideRolService(retrofit: Retrofit): RolService {
        return retrofit.create(RolService::class.java)
    }

    @Provides
    @Singleton
    fun provideCategoriaService(retrofit: Retrofit): CategoriesService {
        return retrofit.create(CategoriesService::class.java)
    }

    @Provides
    @Singleton
    fun provideProductService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }

    @Provides
    @Singleton
    fun provideDocVentaService(retrofit: Retrofit): DocVentaService {
        return retrofit.create(DocVentaService::class.java)
    }

    @Provides
    @Singleton
    fun provideTipoTransaccionService(retrofit: Retrofit): TipoTransaccionService {
        return retrofit.create(TipoTransaccionService::class.java)
    }

    @Provides
    @Singleton
    fun provideCheckOutService(retrofit: Retrofit): CheckoutService {
        return retrofit.create(CheckoutService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun providePaymentService(retrofit: Retrofit): PaymentService {
        return retrofit.create(PaymentService::class.java)
    }
}