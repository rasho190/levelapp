package com.leveluplife.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.leveluplife.data.model.Achievement
import com.leveluplife.data.model.CategoryProgress
import com.leveluplife.data.model.ExpenseLog
import com.leveluplife.data.model.Frequency
import com.leveluplife.data.model.Mission
import com.leveluplife.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface LevelUpDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun observeUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserProfile(profile: UserProfile)

    @Query("SELECT * FROM mission WHERE frequency = :frequency")
    fun observeMissionsByFrequency(frequency: Frequency): Flow<List<Mission>>

    @Query("SELECT * FROM mission")
    fun observeAllMissions(): Flow<List<Mission>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissions(missions: List<Mission>)

    @Update
    suspend fun updateMission(mission: Mission)

    @Query("SELECT COUNT(*) FROM mission")
    suspend fun missionCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategoryProgress(items: List<CategoryProgress>)

    @Query("SELECT * FROM category_progress")
    fun observeCategoryProgress(): Flow<List<CategoryProgress>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAchievement(items: List<Achievement>)

    @Query("SELECT * FROM achievement")
    fun observeAchievements(): Flow<List<Achievement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(log: ExpenseLog)

    @Query("SELECT * FROM expense_log ORDER BY id DESC")
    fun observeExpenseLogs(): Flow<List<ExpenseLog>>
}
