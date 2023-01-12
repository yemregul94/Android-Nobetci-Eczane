package com.moonlight.nobetcieczaneler.di

import com.moonlight.nobetcieczaneler.data.datasource.PharmacyDataSource
import com.moonlight.nobetcieczaneler.data.repo.PharmacyRepository
import com.moonlight.nobetcieczaneler.data.retrofit.ApiUtils
import com.moonlight.nobetcieczaneler.data.retrofit.PharmacyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providePharmacyRepository(pds: PharmacyDataSource): PharmacyRepository {
        return PharmacyRepository(pds)
    }

    @Provides
    @Singleton
    fun providePharmacyDataSource(pdao: PharmacyDao): PharmacyDataSource {
        return PharmacyDataSource(pdao)
    }

    @Provides
    @Singleton
    fun providePharmacyDao(): PharmacyDao {
        return ApiUtils.getPharmacyDao()
    }

}