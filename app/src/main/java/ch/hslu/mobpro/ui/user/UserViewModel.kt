package ch.hslu.mobpro.ui.user

import ch.hslu.mobpro.data.database.entities.UserEntity

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ch.hslu.mobpro.DemoApplicationScreen
import ch.hslu.mobpro.data.user.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    val user: StateFlow<UserEntity> = userRepository
        .user
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            UserEntity()
        )
    val allUsers: StateFlow<List<UserEntity>> = userRepository
        .getAllUsers()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun updateUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setUserName(user.name)
            userRepository.setUserAge(user.age)
            userRepository.setUserAuthorized(user.authorized)
        }
    }

    fun addUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.addUser(user)
        }
    }

}

@Composable
fun getUserViewModel(): UserViewModel {
    val userRepository = UserRepository(LocalContext.current)
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(userRepository)
    )
    return userViewModel
}

class UserViewModelFactory(
    private val userRepository: UserRepository
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}