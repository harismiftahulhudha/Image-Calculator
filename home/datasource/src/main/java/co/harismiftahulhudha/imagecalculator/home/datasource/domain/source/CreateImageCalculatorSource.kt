package co.harismiftahulhudha.imagecalculator.home.datasource.domain.source

import co.harismiftahulhudha.imagecalculator.core.database.dao.ImageCalculatorDao
import co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator.ImageCalculatorEntity
import co.harismiftahulhudha.imagecalculator.core.result.SourceResult
import co.harismiftahulhudha.imagecalculator.home.domain.models.ImageCalculatorModel
import co.harismiftahulhudha.imagecalculator.home.domain.payloads.CreateImageCalculatorPayload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CreateImageCalculatorSource @Inject constructor(
    @Named("ImageCalculatorRoomDBDaoImpl")
    private val calculatorRoomDao: ImageCalculatorDao
) {
    suspend operator fun invoke(payload: CreateImageCalculatorPayload): Flow<SourceResult<MutableList<ImageCalculatorModel>>> = flow {
        calculatorRoomDao.insertOrReplace(
            data = ImageCalculatorEntity(
                id = 0,
                image = payload.image,
                input = payload.input,
                result = payload.result
            )
        )
        val list = calculatorRoomDao.list().map { data ->
            ImageCalculatorModel(
                id = data.id, image = data.image, input = data.input, result = data.result
            )
        }.toMutableList()
        emit(SourceResult.Success(list))
    }
}