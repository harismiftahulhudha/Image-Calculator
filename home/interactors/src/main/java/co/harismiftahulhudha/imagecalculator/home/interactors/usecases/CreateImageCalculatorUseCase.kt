package co.harismiftahulhudha.imagecalculator.home.interactors.usecases

import co.harismiftahulhudha.imagecalculator.core.result.UseCaseResult
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import co.harismiftahulhudha.imagecalculator.home.domain.payloads.CreateImageCalculatorPayload
import kotlinx.coroutines.flow.Flow

interface CreateImageCalculatorUseCase {
    suspend operator fun invoke(payload: CreateImageCalculatorPayload): Flow<UseCaseResult<MutableList<ImageCalculatorModel>>>
}