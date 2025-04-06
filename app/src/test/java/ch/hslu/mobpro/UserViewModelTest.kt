package ch.hslu.mobpro

import app.cash.turbine.test
import ch.hslu.mobpro.data.database.entities.UserEntity
import ch.hslu.mobpro.data.user.UserRepository
import ch.hslu.mobpro.ui.user.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    @Test
    fun testUpdateUser() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        val userRepositoryMock = mock<UserRepository>()
        val user = UserEntity(
            name = "Hans Muster",
            age = 35,
            authorized = false
        )
        val viewModel = UserViewModel(userRepositoryMock)
        viewModel.updateUser(user)
        verify(userRepositoryMock).setUserName(user.name)
        verify(userRepositoryMock).setUserAge(user.age)
        verify(userRepositoryMock).setUserAuthorized(user.authorized)
        Dispatchers.resetMain()
    }

    @Test
    fun testAddUser() = runTest {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val userRepositoryMock = mock<UserRepository>()
        val user = UserEntity(
            name = "Test User",
            age = 33,
            authorized = false
        )

        val viewModel = UserViewModel(userRepositoryMock)
        viewModel.addUser(user)
        verify(userRepositoryMock).addUser(user)

        Dispatchers.resetMain()
    }

    @Test
    fun testAllUsers() = runTest {
        val user = UserEntity(
            name = "Hans Muster",
            age = 35,
            authorized = false
        )
        val userRepositoryMock = mock<UserRepository> {
            on { getAllUsers() } doReturn flowOf(
                listOf(user)
            )
        }
        val viewModel = UserViewModel(userRepositoryMock)
        viewModel.allUsers.test {
            var users = awaitItem()
            Assert.assertTrue(users.isEmpty()) // Initial state is an empty list
            users = awaitItem()
            Assert.assertTrue(users.size == 1)
            Assert.assertEquals(user, users.first())
        }
    }

    @Test
    fun testUser() = runTest {
        val mockedUser = UserEntity(
            name = "Hans Muster",
            age = 35,
            authorized = false
        )
        val userRepositoryMock = mock<UserRepository> {
            on { user } doReturn flowOf(mockedUser)
        }
        val viewModel = UserViewModel(userRepositoryMock)
        viewModel.user.test {
            awaitItem() // Initial state is an empty user
            val emittedUser = awaitItem()
            Assert.assertEquals(mockedUser, emittedUser)
        }
    }

}