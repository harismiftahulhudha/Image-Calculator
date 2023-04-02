package co.harismiftahulhudha.imagecalculator.home.interactors.usecases

import co.harismiftahulhudha.imagecalculator.core.result.UseCaseResult
import co.harismiftahulhudha.imagecalculator.home.datasource.domain.repository.HomeRepository
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetListImageCalculatorUseCaseImpl @Inject constructor(
    private val repository: HomeRepository
) : GetListImageCalculatorUseCase {

    override suspend fun invoke(): Flow<UseCaseResult<MutableList<ImageCalculatorModel>>> = flow {
        emitAll(repository.getListImageCalculator().map {
            it.fold(
                success = { data ->
                    UseCaseResult.Success(data)
                }
            )
        })
    }
}