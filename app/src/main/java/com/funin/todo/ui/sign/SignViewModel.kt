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

    private val _signUser: MutableStateFlow<SignUpUser> = MutableStateFlow(SignUpUser())

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: SharedFlow<Boolean> =
        _isLoading.shareIn(viewModelScope, SharingStarted.Eagerly, 0)

    private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow("")
    val errorMessage: SharedFlow<String?> = _errorMessage.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        replay = 0
    )

    private val _isAuthorized: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isAuthorized: SharedFlow<Boolean> = _isAuthorized.shareIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        replay = 0
    )

    val isSignUpButtonEnabled: StateFlow<Boolean> = _signUser
        .map { it.isValidatedForSignUp() }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setEmailField(email: String?) {
        _signUser.value = _signUser.value.copy(email = email)
    }

    fun setNicknameField(nickname: String?) {
        _signUser.value = _signUser.value.copy(nickname = nickname)
    }

    fun setPasswordField(password: String?) {
        _signUser.value = _signUser.value.copy(password = password)
    }

    fun setRewritePasswordField(rewritePassword: String?) {
        _signUser.value = _signUser.value.copy(rewritePassword = rewritePassword)
    }

    fun signIn() {
        _isLoading.value = true
        val signInUser = _signUser.value
        if (!signInUser.isValidatedForSignIn()) {
            _errorMessage.value = "유효하지 않는 사용자 정보입니다. 다시 시도해주세요."
            _isLoading.value = false
        }
        if (signInUser.email.isNullOrBlank() || signInUser.password.isNullOrBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            _isAuthorized.value = authRepository.login(signInUser.email, signInUser.password)
        }
        _isLoading.value = false
    }

    fun signup() {
        _isLoading.value = true
        val signUpUser = _signUser.value
        if (!signUpUser.isValidatedForSignUp()) {
            _errorMessage.value = "회원가입 할 수 없습니다."
            _isLoading.value = false
        }
        if (signUpUser.email == null || signUpUser.nickname == null || signUpUser.password == null) return
        viewModelScope.launch(Dispatchers.IO) {
            _isAuthorized.value = authRepository.signup(
                email = signUpUser.email,
                nickname = signUpUser.nickname,
                password = signUpUser.password
            )
        }
        _isLoading.value = false
    }

    data class SignUpUser(
        val email: String? = null,
        val nickname: String? = null,
        val password: String? = null,
        val rewritePassword: String? = null
    ) {
        fun isValidatedForSignUp(): Boolean {
            return !email.isNullOrBlank() && !nickname.isNullOrBlank()
                    && !password.isNullOrBlank() && !rewritePassword.isNullOrBlank()
                    && password == rewritePassword
        }

        fun isValidatedForSignIn(): Boolean {
            return !email.isNullOrBlank() && !password.isNullOrBlank()
        }
    }
}