package co.harismiftahulhudha.imagecalculator.core.database.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.harismiftahulhudha.imagecalculator.core.BuildConfig
import co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator.ImageCalculatorEntity
import co.harismiftahulhudha.imagecalculator.core.database.roomDB.imageCalculator.ImageCalculatorRoomDBDao

@Database(
    entities = [ImageCalculatorEntity::class],
    version = 1
)
abstract class AppRoomDB: RoomDatabase() {
    companion object Factory {
        fun getInstance(context: Context): AppRoomDB {
            return if (BuildConfig.DEBUG) {
                Room.databaseBuilder(context, AppRoomDB::class.java, "imagecalculators.db")
                    .setJournalMode(JournalMode.TRUNCATE)
                    .fallbackToDestructiveMigration()
                    .build()
            } else {
                Room.databaseBuilder(context, AppRoomDB::class.java, "imagecalculators.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }

    abstract fun imageCalculatorDao(): ImageCalculatorRoomDBDao
}