package co.harismiftahulhudha.imagecalculator.home.interactors.usecases

import co.harismiftahulhudha.imagecalculator.core.result.UseCaseResult
import co.harismiftahulhudha.imagecalculator.home.datasource.domain.repository.HomeRepository
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import co.harismiftahulhudha.imagecalculator.home.domain.payloads.CreateImageCalculatorPayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateImageCalculatorUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
) : CreateImageCalculatorUseCase {

    override suspend fun invoke(payload: CreateImageCalculatorPayload): Flow<UseCaseResult<MutableList<ImageCalculatorModel>>> = flow {
        emitAll(repository.createImageCalculator(payload).map {
            it.fold(
                success = { data ->
                    UseCaseResult.Success(data)
                }
            )
        })
    }
}