package ch.hslu.mobpro

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.test.core.app.ApplicationProvider
import ch.hslu.mobpro.data.database.DemoDatabase
import ch.hslu.mobpro.data.database.daos.UserDao
import ch.hslu.mobpro.data.database.entities.UserEntity
import ch.hslu.mobpro.data.user.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class UserRepositoryTest {

    private lateinit var context: Context
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        userRepository = UserRepository(context)
    }

    @Test
    fun testSetUserName() = runTest {
        val testUser = UserEntity(
            name = "Hans Muster",
            age = 35,
            authorized = false
        )
        userRepository.setUserName(testUser.name)
        val user = userRepository.user.first()
        assertEquals(testUser.name, user.name)
    }

    @Test
    fun testSetUserAge() = runTest {
        val testUser = UserEntity(
            name = "Hans Muster",
            age = 35,
            authorized = false
        )
        userRepository.setUserAge(testUser.age)
        val user = userRepository.user.first()
        assertEquals(testUser.age, user.age)
    }

    @Test
    fun testSetUserAuthorized() = runTest {
        val testUser = UserEntity(
            name = "Hans Muster",
            age = 35,
            authorized = false
        )
        userRepository.setUserAuthorized(testUser.authorized)
        val user = userRepository.user.first()
        assertEquals(testUser.authorized, user.authorized)
    }
}