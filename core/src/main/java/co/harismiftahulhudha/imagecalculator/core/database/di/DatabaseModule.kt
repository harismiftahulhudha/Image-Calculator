package co.harismiftahulhudha.imagecalculator.core.database.di

import android.content.Context
import co.harismiftahulhudha.imagecalculator.core.database.dao.ImageCalculatorDao
import co.harismiftahulhudha.imagecalculator.core.database.roomDB.AppRoomDB
import co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator.ImageCalculatorRoomDBDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppRoomDB(@ApplicationContext context: Context) = AppRoomDB.getInstance(context)

    @Provides
    @Singleton
    internal fun provideImageCalculatorRoomDBDao(db: AppRoomDB) = db.imageCalculatorDao()

    @Provides
    @Singleton
    @Named("ImageCalculatorRoomDBDaoImpl")
    internal fun provideImageCalculatorDao(impl: ImageCalculatorRoomDBDaoImpl): ImageCalculatorDao = impl
}