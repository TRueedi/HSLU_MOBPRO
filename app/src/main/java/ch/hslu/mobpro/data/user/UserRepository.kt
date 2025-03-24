package ch.hslu.mobpro.data.user

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ch.hslu.mobpro.data.database.DemoDatabase
import ch.hslu.mobpro.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.prefs.Preferences

class UserRepository(
    private val context: Context
) {

    private val dao = DemoDatabase.getDatabase(context).userDao()

    val user: Flow<UserEntity> = context.dataStore.data.map { preferences ->
        val userName = preferences[PreferencesKeys.USER_NAME] ?: ""
        val userAge = preferences[PreferencesKeys.USER_AGE] ?: -1
        val userAuthorized = preferences[PreferencesKeys.USER_AUTHORIZED] ?: false
        UserEntity(
            name = userName,
            age = userAge,
            authorized = userAuthorized
        )
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_NAME] = name
        }
    }

    suspend fun setUserAge(age: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_AGE] = age
        }
    }

    suspend fun setUserAuthorized(authorized: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_AUTHORIZED] = authorized
        }
    }

    suspend fun addUser(user: UserEntity) {
        dao.insert(
            UserEntity(
                name = user.name,
                age = user.age,
                authorized = user.authorized
            )
        )
    }

    fun getAllUsers(): Flow<List<UserEntity>> {
        return dao.getAll().map { users ->
            users.map { user ->
                UserEntity(
                    name = user.name,
                    age = user.age,
                    authorized = user.authorized
                )
            }
        }
    }

    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"
        private val Context.dataStore by preferencesDataStore(
            name = USER_PREFERENCES_NAME
        )
    }

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_AGE = intPreferencesKey("user_age")
        val USER_AUTHORIZED = booleanPreferencesKey("user_authorized")
    }
}

