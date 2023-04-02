package co.harismiftahulhudha.imagecalculator.home.datasource.domain.repository

import co.harismiftahulhudha.imagecalculator.core.result.SourceResult
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import co.harismiftahulhudha.imagecalculator.home.domain.payloads.CreateImageCalculatorPayload
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun createImageCalculator(payload: CreateImageCalculatorPayload): Flow<SourceResult<MutableList<ImageCalculatorModel>>>
    suspend fun getListImageCalculator(): Flow<SourceResult<MutableList<ImageCalculatorModel>>>
}