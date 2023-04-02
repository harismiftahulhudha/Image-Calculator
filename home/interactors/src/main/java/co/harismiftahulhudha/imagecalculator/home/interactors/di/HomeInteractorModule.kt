package co.harismiftahulhudha.imagecalculator.home.interactors.di

import co.harismiftahulhudha.imagecalculator.home.interactors.usecases.CreateImageCalculatorUseCase
import co.harismiftahulhudha.imagecalculator.home.interactors.usecases.CreateImageCalculatorUseCaseImpl
import co.harismiftahulhudha.imagecalculator.home.interactors.usecases.GetListImageCalculatorUseCase
import co.harismiftahulhudha.imagecalculator.home.interactors.usecases.GetListImageCalculatorUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeInteractorModule {
    @Provides
    @Singleton
    internal fun provideCreateImageCalculatorUseCase(createImageCalculatorUseCaseImpl: CreateImageCalculatorUseCaseImpl): CreateImageCalculatorUseCase {
        return createImageCalculatorUseCaseImpl
    }
    @Provides
    @Singleton
    internal fun provideGetListImageCalculatorUseCase(getListImageCalculatorUseCaseImpl: GetListImageCalculatorUseCaseImpl): GetListImageCalculatorUseCase {
        return getListImageCalculatorUseCaseImpl
    }
}