package com.funin.todo.ui.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funin.todo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _signUpUser: MutableStateFlow<SignUpUser> = MutableStateFlow(SignUpUser())

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: SharedFlow<Boolean> =
        _isLoading.shareIn(viewModelScope, SharingStarted.Eagerly, 0)

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow("")
    val errorMessage: SharedFlow<String?> = _errorMessage.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        replay = 0
    )

    val isSignUpButtonEnabled: StateFlow<Boolean> = _signUpUser
        .map { it.isValidated() }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setEmailField(email: String?) {
        _signUpUser.value = _signUpUser.value.copy(email = email)
    }

    fun setNicknameField(nickname: String?) {
        _signUpUser.value = _signUpUser.value.copy(nickname = nickname)
    }

    fun setPasswordField(password: String?) {
        _signUpUser.value = _signUpUser.value.copy(password = password)
    }

    fun setRewritePasswordField(rewritePassword: String?) {
        _signUpUser.value = _signUpUser.value.copy(rewritePassword = rewritePassword)
    }

    fun signup() {
        _isLoading.value = true
        val signUpUser = _signUpUser.value
        if (!signUpUser.isValidated()) {
            _errorMessage.value = "회원가입 할 수 없습니다."
            _isLoading.value = false
        }
        if (signUpUser.email == null || signUpUser.nickname == null || signUpUser.password == null) return
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signup(signUpUser.email, signUpUser.nickname, signUpUser.password)
            _isLoading.value = false
        }
    }

    data class SignUpUser(
        val email: String? = null,
        val nickname: String? = null,
        val password: String? = null,
        val rewritePassword: String? = null
    ) {
        fun isValidated(): Boolean {
            return !email.isNullOrBlank() && !nickname.isNullOrBlank()
                    && !password.isNullOrBlank() && !rewritePassword.isNullOrBlank()
                    && password == rewritePassword
        }
    }
}