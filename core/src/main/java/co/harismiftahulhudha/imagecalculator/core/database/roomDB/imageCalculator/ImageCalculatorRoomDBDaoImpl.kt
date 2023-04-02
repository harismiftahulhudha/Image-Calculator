package co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator

import co.harismiftahulhudha.imagecalculator.core.database.dao.ImageCalculatorDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageCalculatorRoomDBDaoImpl @Inject constructor(
    private val dao: ImageCalculatorRoomDBDao
): ImageCalculatorDao {
    override suspend fun insertOrReplace(data: ImageCalculatorEntity): Long {
        return dao.insertOrReplace(data)
    }

    override suspend fun list(): MutableList<ImageCalculatorEntity> {
        return dao.list()
    }
}