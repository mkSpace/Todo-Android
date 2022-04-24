package com.funin.todo.ui.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funin.base.extensions.combine
import com.funin.todo.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    private val _emailField: MutableStateFlow<String?> = MutableStateFlow("")

    private val _nicknameField: MutableStateFlow<String?> = MutableStateFlow("")

    private val _passwordField: MutableStateFlow<String?> = MutableStateFlow("")

    private val _rewritePasswordField: MutableStateFlow<String?> = MutableStateFlow("")

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val isSignUpButtonEnabled: StateFlow<Boolean> = _passwordField.combine(_rewritePasswordField)
        .map { (password, rewirePassword) ->
            !password.isNullOrBlank() && !rewirePassword.isNullOrBlank() && password == rewirePassword
        }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun setEmailField(email: String?) {
        _emailField.value = email
    }

    fun setNicknameField(nickname: String?) {
        _nicknameField.value = nickname
    }

    fun setPasswordField(password: String?) {
        _passwordField.value = password
    }

    fun setRewritePasswordField(rewirePassword: String?) {
        _rewritePasswordField.value = rewirePassword
    }
}