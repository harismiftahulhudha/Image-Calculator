package co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageCalculatorRoomDBDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(data: ImageCalculatorEntity): Long

    @Query("SELECT * FROM image_calculator ORDER BY id DESC")
    suspend fun list(): MutableList<ImageCalculatorEntity>
}