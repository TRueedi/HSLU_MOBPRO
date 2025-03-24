package ch.hslu.mobpro.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ch.hslu.mobpro.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM User")
    fun getAll(): Flow<List<UserEntity>>
}