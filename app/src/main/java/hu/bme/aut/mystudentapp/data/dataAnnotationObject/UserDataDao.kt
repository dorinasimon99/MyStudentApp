package hu.bme.aut.mystudentapp.data.dataAnnotationObject

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.mystudentapp.data.model.LocalUserData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDataDao {
    @Query("SELECT * FROM localUserData")
    suspend fun getLocalUser() : LocalUserData?

    @Insert
    suspend fun addLocalUserData(username: LocalUserData)

    @Query("UPDATE localUserData SET id=:id, userName=:username")
    suspend fun changeLocalUser(id: String, username: String)
}