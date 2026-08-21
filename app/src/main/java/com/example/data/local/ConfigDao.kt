package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.PortfolioConfigEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigDao {
    @Query("SELECT * FROM portfolio_config WHERE id = 1 LIMIT 1")
    fun getConfigFlow(): Flow<PortfolioConfigEntity?>

    @Query("SELECT * FROM portfolio_config WHERE id = 1 LIMIT 1")
    suspend fun getConfig(): PortfolioConfigEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(config: PortfolioConfigEntity)

    @Update
    suspend fun updateConfig(config: PortfolioConfigEntity)
}
