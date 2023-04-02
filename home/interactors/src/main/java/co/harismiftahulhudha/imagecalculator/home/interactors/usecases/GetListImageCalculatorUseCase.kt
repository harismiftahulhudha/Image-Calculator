package co.harismiftahulhudha.imagecalculator.home.interactors.usecases

import co.harismiftahulhudha.imagecalculator.core.result.UseCaseResult
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import kotlinx.coroutines.flow.Flow

interface GetListImageCalculatorUseCase {
    suspend operator fun invoke(): Flow<UseCaseResult<MutableList<ImageCalculatorModel>>>
}