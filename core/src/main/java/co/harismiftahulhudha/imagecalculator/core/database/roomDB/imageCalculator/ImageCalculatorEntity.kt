package co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_calculator")
data class ImageCalculatorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val image: String,
    val input: String,
    val result: String
)