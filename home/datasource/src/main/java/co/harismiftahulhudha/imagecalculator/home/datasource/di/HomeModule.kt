package co.harismiftahulhudha.imagecalculator.home.datasource.di

import co.harismiftahulhudha.imagecalculator.home.datasource.domain.repository.HomeRepository
import co.harismiftahulhudha.imagecalculator.home.datasource.domain.repository.HomeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeModule {

    @Provides
    @Singleton
    fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository {
        return homeRepositoryImpl
    }
}