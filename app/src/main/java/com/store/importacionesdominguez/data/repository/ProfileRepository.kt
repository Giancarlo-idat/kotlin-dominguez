package com.store.importacionesdominguez.data.repository

import com.store.importacionesdominguez.data.service.ProfileService
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val profileService: ProfileService
){
    suspend fun getProfileCliente() = profileService.getProfileClient()

}