package co.harismiftahulhudha.imagecalculator.core.database.dao

import co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator.ImageCalculatorEntity

interface ImageCalculatorDao {
    suspend fun insertOrReplace(data: ImageCalculatorEntity): Long
    suspend fun list(): MutableList<ImageCalculatorEntity>
}