package co.harismiftahulhudha.imagecalculator.home.datasource.domain.repository

import co.harismiftahulhudha.imagecalculator.core.result.SourceResult
import co.harismiftahulhudha.imagecalculator.home.datasource.domain.source.CreateImageCalculatorSource
import co.harismiftahulhudha.imagecalculator.home.datasource.domain.source.GetListImageCalculatorSource
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import co.harismiftahulhudha.imagecalculator.home.domain.payloads.CreateImageCalculatorPayload
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val createImageCalculatorSource: CreateImageCalculatorSource,
    private val getListImageCalculatorSource: GetListImageCalculatorSource
): HomeRepository {
    override suspend fun createImageCalculator(payload: CreateImageCalculatorPayload): Flow<SourceResult<MutableList<ImageCalculatorModel>>> {
        return createImageCalculatorSource(payload)
    }

    override suspend fun getListImageCalculator(): Flow<SourceResult<MutableList<ImageCalculatorModel>>> {
        return getListImageCalculatorSource()
    }
}