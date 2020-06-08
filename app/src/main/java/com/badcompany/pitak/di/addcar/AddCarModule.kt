package com.badcompany.pitak.di.addcar

import com.badcompany.data.CarRepositoryImpl
import com.badcompany.data.mapper.CarColorMapper
import com.badcompany.data.mapper.CarMapper
import com.badcompany.data.mapper.CarModelMapper
import com.badcompany.data.repository.CarRemote
import com.badcompany.data.source.CarDataStoreFactory
import com.badcompany.data.source.CarRemoteDataStore
import com.badcompany.domain.repository.CarRepository
import com.badcompany.domain.usecases.GetCarColors
import com.badcompany.domain.usecases.GetCarModels
import com.badcompany.domain.usecases.SaveCar
import com.badcompany.domain.usecases.SetDefaultCar
import com.badcompany.pitak.di.main.MainScope
import com.badcompany.remote.ApiService
import com.badcompany.remote.CarRemoteImpl
import dagger.Module
import dagger.Provides

@Module
object AddCarModule {




}