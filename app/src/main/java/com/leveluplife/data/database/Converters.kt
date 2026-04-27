package com.leveluplife.data.database

import androidx.room.TypeConverter
import com.leveluplife.data.model.Difficulty
import com.leveluplife.data.model.Frequency
import com.leveluplife.data.model.MissionCategory
import com.leveluplife.data.model.UserRank

class Converters {
    @TypeConverter
    fun fromCategory(value: MissionCategory): String = value.name

    @TypeConverter
    fun toCategory(value: String): MissionCategory = MissionCategory.valueOf(value)

    @TypeConverter
    fun fromDifficulty(value: Difficulty): String = value.name

    @TypeConverter
    fun toDifficulty(value: String): Difficulty = Difficulty.valueOf(value)

    @TypeConverter
    fun fromFrequency(value: Frequency): String = value.name

    @TypeConverter
    fun toFrequency(value: String): Frequency = Frequency.valueOf(value)

    @TypeConverter
    fun fromRank(value: UserRank): String = value.name

    @TypeConverter
    fun toRank(value: String): UserRank = UserRank.valueOf(value)
}
