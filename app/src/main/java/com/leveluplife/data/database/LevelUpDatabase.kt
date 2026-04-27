package com.leveluplife.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leveluplife.data.model.Achievement
import com.leveluplife.data.model.CategoryProgress
import com.leveluplife.data.model.ExpenseLog
import com.leveluplife.data.model.Mission
import com.leveluplife.data.model.UserProfile

@Database(
    entities = [UserProfile::class, Mission::class, CategoryProgress::class, Achievement::class, ExpenseLog::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LevelUpDatabase : RoomDatabase() {
    abstract fun dao(): LevelUpDao

    companion object {
        @Volatile
        private var INSTANCE: LevelUpDatabase? = null

        fun getInstance(context: Context): LevelUpDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LevelUpDatabase::class.java,
                    "level_up_life.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
