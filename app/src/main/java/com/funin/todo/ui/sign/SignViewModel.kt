package com.funin.todo.ui.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.funin.base.extensions.combine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*

@HiltViewModel
class SignViewModel : ViewModel() {

    private val _emailField: MutableStateFlow<String?> = MutableStateFlow("")
    val emailField: StateFlow<String?> = _emailField

    private val _nicknameField: MutableStateFlow<String?> = MutableStateFlow("")
    val nicknameField: StateFlow<String?> = _nicknameField

    private val _passwordField: MutableStateFlow<String?> = MutableStateFlow("")
    val passwordField: StateFlow<String?> = _passwordField

    private val _rewritePasswordField: MutableStateFlow<String?> = MutableStateFlow("")
    val rewritePasswordField: StateFlow<String?> = _rewritePasswordField

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