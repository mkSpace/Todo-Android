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

    private val _errorMessage: MutableSharedFlow<String?> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 1)
    val errorMessage: Flow<String?> = _errorMessage

    private val _toastMessage: MutableSharedFlow<String?> =
        MutableSharedFlow(replay = 1, extraBufferCapacity = 1)
    val toastMessage: Flow<String?> = _toastMessage

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
            _errorMessage.tryEmit("???????????? ?????? ????????? ???????????????. ?????? ??????????????????.")
            _isLoading.value = false
        }
        if (signInUser.email.isNullOrBlank() || signInUser.password.isNullOrBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.login(signInUser.email, signInUser.password)
            if (!result) {
                _errorMessage.emit("???????????? ?????? ????????? ???????????????. ?????? ??????????????????.")
            } else {
                _toastMessage.emit("???????????? ?????????????????????.")
            }
            _isAuthorized.value = result
        }
        _isLoading.value = false
    }

    fun signup() {
        _isLoading.value = true
        val signUpUser = _signUser.value
        if (!signUpUser.isValidatedForSignUp()) {
            _errorMessage.tryEmit("???????????? ??? ??? ????????????.")
            _isLoading.value = false
        }
        if (signUpUser.email == null || signUpUser.nickname == null || signUpUser.password == null) return
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signup(
                email = signUpUser.email,
                nickname = signUpUser.nickname,
                password = signUpUser.password
            )
            if (!result) {
                _errorMessage.emit("???????????? ?????? ????????? ???????????????. ?????? ??????????????????.")
            } else {
                _toastMessage.emit("???????????? ?????????????????????.")
            }
            _isAuthorized.value = result
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