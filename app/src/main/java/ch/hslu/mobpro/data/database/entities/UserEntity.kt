package ch.hslu.mobpro.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var name: String = "",
    var age: Int = -1,
    var authorized: Boolean = false
)