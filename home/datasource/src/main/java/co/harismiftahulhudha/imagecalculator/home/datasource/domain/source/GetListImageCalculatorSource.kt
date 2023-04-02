package co.harismiftahulhudha.imagecalculator.home.datasource.domain.source

import co.harismiftahulhudha.imagecalculator.core.database.dao.ImageCalculatorDao
import co.harismiftahulhudha.imagecalculator.core.result.SourceResult
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class GetListImageCalculatorSource @Inject constructor(
    @Named("ImageCalculatorRoomDBDaoImpl")
    private val calculatorRoomDao: ImageCalculatorDao
) {
    suspend operator fun invoke(): Flow<SourceResult<MutableList<ImageCalculatorModel>>> = flow {
        val list = calculatorRoomDao.list().map { data ->
            ImageCalculatorModel(
                id = data.id, image = data.image, input = data.input, result = data.result
            )
        }.toMutableList()
        emit(SourceResult.Success(list))
    }
}